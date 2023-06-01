package org.WeatherLight.Actors;

import org.WeatherLight.MQTT.MQTT;
import org.WeatherLight.MQTT.MQTTMessageHandler;
import org.WeatherLight.TinkerForge.LedStrip;
import org.WeatherLight.util.JsonParser;
import com.tinkerforge.TinkerforgeException;
import org.WeatherLight.util.WeatherApiClient;
import org.WeatherLight.util.WeatherColorMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class LightActor implements MQTTMessageHandler {
    LedStrip ledStrip;
    LightState lightState;
    MQTT mqtt;
    int[] colors;
    private LocalDateTime lastWeatherAPICall = null;
    public static void main(String[] args) throws Exception {
        String BROKER = System.getenv("MQTT_BROKER");
        String CLIENT_ID = "BrightMood_Light " + MqttClient.generateClientId();
        String USERNAME = System.getenv("MQTT_USERNAME");
        String PASSWORD = System.getenv("MQTT_PASSWORD");
        LightActor lightActor = new LightActor();
        lightActor.mqtt = new MQTT(BROKER, CLIENT_ID, USERNAME, PASSWORD, 0, new CountDownLatch(1), lightActor);
        lightActor.mqtt.subscribe("WeatherLight/Light/I");
        lightActor.ledStrip = new LedStrip("esp32-221v", 4223,"Fo5", 50);
    }

    public void changeState(LightState lightState) throws InterruptedException, IOException, TinkerforgeException {
        System.out.println(lightState.getIsOpen());
        if(lightState.getIsOpen()){
            if (lastWeatherAPICall == null || !lastWeatherAPICall.toLocalDate().isEqual(LocalDate.now())) {
                WeatherApiClient weatherApiClient = new WeatherApiClient(System.getenv("API_KEY"),System.getenv("LOCATION"),1);
                List<String> weather = weatherApiClient.getWeather();
                this.colors = new WeatherColorMapper().getColor(weather);
                System.out.println(weather);
            }

            System.out.println(Arrays.toString(colors));
            if(this.lightState.getIsOpen()){
                ledStrip.setState(colors);
            }else {
                ledStrip.brightenRGB(colors, 3000);
            }

            lastWeatherAPICall = LocalDateTime.now();
        }else {
            ledStrip.setState(ledStrip.percentToRGB(new int[]{0,0,0}));
        }
        this.lightState = lightState;

    }

    public void callBack() throws IOException {
        this.mqtt.publish("WeatherLight/Light/S", Objects.requireNonNull(JsonParser.toJson(lightState)));
    }


    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws IOException, InterruptedException, TinkerforgeException {
       if(topic.equals("WeatherLight/Light/I")){
           changeState((LightState) Objects.requireNonNull(JsonParser.toObject(new String(mqttMessage.getPayload()), LightState.class)));
       }
    }
}
