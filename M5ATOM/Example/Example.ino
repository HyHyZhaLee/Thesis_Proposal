#include <M5Atom.h>
#include "sensor_data.h"
#include "wifi_setup.h"
#include "MQTT_helper.h"

// Class Wifi_esp32
Wifi_esp32 wifi("cce_office", "0902449198");
// Class MyMQTT
MyMQTT myMQTT("mqttserver.tk", "bkair", "bkair_RgPQAZoA5N");
// Class data json already created
SENSOR_DATA data;

void setup() {
  // put your setup code here, to run once:
  M5.begin(true, false, true);
  Serial2.begin(9600, SERIAL_8N1, 22, 19);
  wifi.setupWifi(); // Setup wifi
  myMQTT.connectToMQTT(); // Connect to MQTT server
  myMQTT.subscribe("/bkair/airmonitoring/");// Subscribe to the feed

}

float temp = 0, humi = 0, CO = 0, CO2 = 0, SO2 = 0, NO2 = 0, PM25 = 0, PM10 = 0, O3 = 0;

void loop() {
  // put your main code here, to run repeatedly:
  myMQTT.checkConnect();
  String data_to_pub;
  SENSOR_RS485 data485;


  // GET TEMP DATA
  Serial.println("Writing to temp with data...");
  Serial2.write(data485.getTemperature(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    temp = (int)receivedData[3]*256 + (int)receivedData[4];
    temp /= 10;
    Serial.println(temp);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET HUMI DATA
  Serial.println("Writing to humi with data...");
  Serial2.write(data485.getHumidity(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    humi = (int)receivedData[3]*256 + (int)receivedData[4];
    humi /= 10;
    Serial.println(humi);

  }
  delay(2000);
  myMQTT.checkConnect();


  // GET CO DATA
  Serial.println("Writing to CO with data...");
  Serial2.write(data485.getCO(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    CO = (int)receivedData[3]*256 + (int)receivedData[4];
    CO /= 10;
    Serial.println(CO);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET CO2 DATA
  Serial.println("Writing to CO2 with data...");
  Serial2.write(data485.getCO2(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    CO2 = (int)receivedData[3]*256 + (int)receivedData[4];
    CO2 /= 10;
    Serial.println(CO2);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET SO2 DATA
  Serial.println("Writing to SO2 with data...");
  Serial2.write(data485.getSO2(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    SO2 = (int)receivedData[3]*256 + (int)receivedData[4];
    SO2 /= 10;
    Serial.println(SO2);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET NO2 DATA
  Serial.println("Writing to NO2 with data...");
  Serial2.write(data485.getNO2(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    NO2 = (int)receivedData[3]*256 + (int)receivedData[4];
    NO2 /= 10;
    Serial.println(NO2);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET PM2.5 DATA
  Serial.println("Writing to PM2.5 with data...");
  Serial2.write(data485.getPM2_5(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    PM25 = (int)receivedData[3]*256 + (int)receivedData[4];
    PM25 /= 10;
    Serial.println(PM25);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET PM10 DATA
  Serial.println("Writing to PM10 with data...");
  Serial2.write(data485.getPM10(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    PM10 = (int)receivedData[3]*256 + (int)receivedData[4];
    PM10 /= 10;
    Serial.println(PM10);
  }
  delay(2000);
  myMQTT.checkConnect();


  // GET O3 DATA
  Serial.println("Writing to O3 with data...");
  Serial2.write(data485.getO3(), 8);
  delay(1000);
  if (Serial2.available()) {    // If the serial port receives a message. 
    uint8_t receivedData[7];
    Serial2.readBytes(receivedData, sizeof(receivedData));  // Read the message.
    for (int i = 0; i <7 ; i++) {
      Serial.print("0x");
      Serial.print(receivedData[i], HEX);
      Serial.print(", ");
    }
    Serial.println();
    O3 = (int)receivedData[3]*256 + (int)receivedData[4];
    O3 /= 10;
    Serial.println(O3);
  }
  delay(2000);
  myMQTT.checkConnect();

  // String SENSOR_DATA::createAQIStationJSON(float temp, float humi, float co, float co2, float so2, float no2, float pm25, float pm10, float o3)
  data_to_pub = data.createAQIStationJSON(temp,humi,CO, CO2, SO2, NO2, PM25, PM10, O3);
  myMQTT.publish("/bkair/airmonitoring//", data_to_pub); // Publish to feed
  myMQTT.checkConnect();
  delay(300000);
  myMQTT.checkConnect();
  M5.update();
}