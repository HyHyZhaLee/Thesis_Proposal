#include <M5Atom.h>
#include "sensor_data.h"
#include "wifi_setup.h"
#include "MQTT_helper.h"
#include <freertos/FreeRTOS.h>
#include <freertos/task.h>

// Class Wifi_esp32
Wifi_esp32 wifi("cce_office", "0902449198");
// Class MyMQTT
MyMQTT myMQTT("mqttserver.tk", "bkair", "bkair_RgPQAZoA5N");
// Class data json already created
SENSOR_DATA data;

void setup() {
  M5.begin(true, false, true);
  Serial2.begin(9600, SERIAL_8N1, 22, 19);
  wifi.setupWifi(); // Setup wifi
  myMQTT.connectToMQTT(); // Connect to MQTT server
  myMQTT.subscribe("/bkair/airmonitoring");// Subscribe to the feed

  xTaskCreatePinnedToCore(taskGetData, "GetDataTask", 10000, NULL, 1, NULL, 0);
}

float temp = 0, humi = 0, CO = 0, CO2 = 0, SO2 = 0, NO2 = 0, PM25 = 0, PM10 = 0, O3 = 0;
TickType_t lastDataTime = 0;
TickType_t waitTimeInTicks = pdMS_TO_TICKS(300000); // 5 minutes in TICKs

void loop() {
  myMQTT.checkConnect();
  M5.update();
}

void taskGetData(void *parameter) {
  for (;;) {
    SENSOR_RS485 data485;

    // GET TEMP DATA
    Serial.println("Writing to temp with data...");
    Serial2.write(data485.getTemperature(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      temp = (int)receivedData[3] * 256 + (int)receivedData[4];
      temp /= 10;
    }

    // GET HUMI DATA
    Serial.println("Writing to humi with data...");
    Serial2.write(data485.getHumidity(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      humi = (int)receivedData[3] * 256 + (int)receivedData[4];
      humi /= 10;
    }

    // GET CO DATA
    Serial.println("Writing to CO with data...");
    Serial2.write(data485.getCO(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      CO = (int)receivedData[3] * 256 + (int)receivedData[4];
      CO /= 10;
    }

    // GET CO2 DATA
    Serial.println("Writing to CO2 with data...");
    Serial2.write(data485.getCO2(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      CO2 = (int)receivedData[3] * 256 + (int)receivedData[4];
      CO2 /= 10;
    }

    // GET SO2 DATA
    Serial.println("Writing to SO2 with data...");
    Serial2.write(data485.getSO2(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      SO2 = (int)receivedData[3] * 256 + (int)receivedData[4];
      SO2 /= 10;
    }

    // GET NO2 DATA
    Serial.println("Writing to NO2 with data...");
    Serial2.write(data485.getNO2(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      NO2 = (int)receivedData[3] * 256 + (int)receivedData[4];
      NO2 /= 10;
    }

    // GET PM2.5 DATA
    Serial.println("Writing to PM2.5 with data...");
    Serial2.write(data485.getPM2_5(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      PM25 = (int)receivedData[3] * 256 + (int)receivedData[4];
      PM25 /= 10;
    }

    // GET PM10 DATA
    Serial.println("Writing to PM10 with data...");
    Serial2.write(data485.getPM10(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      PM10 = (int)receivedData[3] * 256 + (int)receivedData[4];
      PM10 /= 10;
    }

    // GET O3 DATA
    Serial.println("Writing to O3 with data...");
    Serial2.write(data485.getO3(), 8);
    vTaskDelay(pdMS_TO_TICKS(1000));
    if (Serial2.available()) {
      uint8_t receivedData[7];
      Serial2.readBytes(receivedData, sizeof(receivedData));
      O3 = (int)receivedData[3] * 256 + (int)receivedData[4];
      O3 /= 10;
    }
    vTaskDelay(pdMS_TO_TICKS(1000));
    
    String data_to_pub = data.createAQIStationJSON(temp, humi, CO, CO2, SO2, NO2, PM25, PM10, O3);
    myMQTT.publish("/bkair/airmonitoring", data_to_pub);
    TickType_t currentTime = xTaskGetTickCount();
    if (!lastDataTime) {
      lastDataTime = currentTime;
      vTaskDelay(waitTimeInTicks - pdMS_TO_TICKS(10000)); // Delay 5 minutes
    } else {
      TickType_t tempLastDataTime = currentTime;
      vTaskDelay(waitTimeInTicks - (currentTime - lastDataTime)); // Delay 5 minutes
      lastDataTime = tempLastDataTime;
    }
  }
}
