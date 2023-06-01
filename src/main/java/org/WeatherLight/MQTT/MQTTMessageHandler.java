package org.WeatherLight.MQTT;

import com.tinkerforge.TinkerforgeException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

public interface MQTTMessageHandler {
    public void messageArrived(String topic, MqttMessage mqttMessage) throws IOException, InterruptedException, TinkerforgeException;
}
