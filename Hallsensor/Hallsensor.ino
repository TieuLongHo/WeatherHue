#include <WiFi.h>
#include <PubSubClient.h>
#include "my_secrets.h"


const int MQTT_PORT = 1883;

#define LIMIT 90
static bool trueBefore = false;
static bool falseBefore = false;

WiFiClient espClient;
PubSubClient client(espClient);


void setup() {
  Serial.begin(115200);
  WiFi.begin(SECRET_SSID, SECRET_PASS);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);  
    Serial.print(".");
  }

  Serial.println("Connected to WiFi");
  client.setServer(MQTT_IP, MQTT_PORT);

  
  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");
    if (client.connect("ESP32Client", MQTT_USER, MQTT_PASS)) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.print("Failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
  }
}

void loop() {
  int sensorValue = hallRead();
  Serial.print("Sensor value: ");
  Serial.println(sensorValue);


  if (sensorValue < LIMIT && trueBefore == false) {
    falseBefore = false;
    String payload = "{\"isOpen\": true}";
    client.publish("WeatherLight/Light/I", payload.c_str());
    trueBefore = true;
    Serial.println("Published : true");
  }else if (sensorValue >= LIMIT && falseBefore == false ){
    trueBefore = false;
    String payload = "{\"isOpen\": false}";
    client.publish("WeatherLight/Light/I", payload.c_str());
    falseBefore = true;
    Serial.println("Published : false");
  }

  delay(1000);
  client.loop();
}


