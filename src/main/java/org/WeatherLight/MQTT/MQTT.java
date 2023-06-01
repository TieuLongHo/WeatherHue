package org.WeatherLight.MQTT;

import com.tinkerforge.TinkerforgeException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

public class MQTT implements MqttCallback {
    private final MQTTMessageHandler mqttMessageHandler;
    private final CountDownLatch latch;
    private final String BROKER;
    private final String CLIENT_ID;
    private final String USERNAME;
    private final String PASSWORD;
    private final int qos;
    private final MqttAsyncClient client;

    public MQTT(final String BROKER, final String CLIENT_ID, final String USERNAME, final String PASSWORD, final int qos, final CountDownLatch latch, MQTTMessageHandler mqttMessageHandler) throws MqttException {
        this.BROKER = BROKER;
        this.CLIENT_ID = CLIENT_ID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.qos = qos;
        this.latch = latch;
        this.mqttMessageHandler = mqttMessageHandler;
        this.client = connect();
    }


    private MqttAsyncClient connect() throws MqttException {
        System.out.println("Connecting to MQTT broker...");
        MqttAsyncClient client = new MqttAsyncClient(BROKER, CLIENT_ID, new MemoryPersistence());
        client.setCallback(this); // register callback
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(60);
        IMqttToken token = client.connect(options); // connect with options
        token.waitForCompletion();
        return client;
    }

    public void publish(final String topic, final String message) {
        Thread publishThread = new Thread(() -> {
            System.out.println("\nPublishing message to MQTT broker...");
            try {
                MqttMessage msg = new MqttMessage(message.getBytes());
                msg.setQos(qos);
                IMqttToken token = client.publish(topic, msg);
                token.waitForCompletion();
                System.out.println("\tMessage published");
                System.out.println("\tTopic:           " + topic);
                System.out.println("\tMessage content: " + message);
            } catch (MqttException e) {
                System.out.println("Error while publishing message");
                System.out.println(e.getMessage());
            }
        });
        publishThread.start();

    }

    public void subscribe(final String topic) {
        Thread subscribeThread = new Thread(() -> {
            System.out.println("Subscribing to topic: " + topic);
            try {
                IMqttToken token = client.subscribe(topic, qos);
                token.waitForCompletion();
                System.out.println("Subscribed to topic: " + topic);
                latch.await();
            } catch (MqttException | InterruptedException e) {
                System.out.println("Error while subscribing to topic: " + topic);
                System.out.println(e.getMessage());
            }
        });
        subscribeThread.start();
    }

    public void disconnect() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage mqttMessage) throws IOException, InterruptedException, TinkerforgeException {
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("\nReceived a Message!" +
                "\n\tTime:    " + time +
                "\n\tTopic:   " + topic +
                "\n\tMessage: " + new String(mqttMessage.getPayload()) +
                "\n\tQoS:     " + mqttMessage.getQos() + "\n");
        latch.countDown(); // unblock thread
        this.mqttMessageHandler.messageArrived(topic, mqttMessage);
    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("Message delivered successfully");
    }

    @Override
    public void connectionLost(final Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }
}

