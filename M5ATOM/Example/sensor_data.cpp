/*
 * sensor_data.h
 *
 *  Created on: Oct 23, 2023
 *      Author: Huy Ly
 */

#include "sensor_data.h"

String SENSOR_DATA::floatToString(float value) {
  char buffer[20];  // Đủ lớn để chứa chuỗi
  sprintf(buffer, "%.2f", value);
  return String(buffer);
}

String SENSOR_DATA::createAQIStationJSON(float temp, float humi, float co, float co2, float so2, float no2, float pm25, float pm10, float o3) {
  DynamicJsonDocument doc(1024);

  doc["station_id"] = "bkair_0001";
  doc["station_name"] = "BK AIR 0001";
  doc["gps_longitude"] = 106.89;
  doc["gps_latitude"] = 10.5;

  JsonArray sensors = doc.createNestedArray("sensors");

  JsonObject temp_sensor = sensors.createNestedObject();
  temp_sensor["sensor_id"] = "temp_0001";
  temp_sensor["sensor_name"] = "Nhiệt Độ";
  temp_sensor["sensor_value"] = floatToString(temp);
  temp_sensor["sensor_unit"] = "°C";

  JsonObject humi_sensor = sensors.createNestedObject();
  humi_sensor["sensor_id"] = "humi_0001";
  humi_sensor["sensor_name"] = "Độ Ẩm";
  humi_sensor["sensor_value"] = floatToString(humi);
  humi_sensor["sensor_unit"] = "%";

  JsonObject co2_sensor = sensors.createNestedObject();
  co2_sensor["sensor_id"] = "CO2_0001";
  co2_sensor["sensor_name"] = "CO2";
  co2_sensor["sensor_value"] = floatToString(co2);
  co2_sensor["sensor_unit"] = "ppm";

  JsonObject pm25_sensor = sensors.createNestedObject();
  pm25_sensor["sensor_id"] = "PM25_0001";
  pm25_sensor["sensor_name"] = "Bụi 2.5";
  pm25_sensor["sensor_value"] = floatToString(pm25);
  pm25_sensor["sensor_unit"] = "ppm";

  JsonObject pm10_sensor = sensors.createNestedObject();
  pm10_sensor["sensor_id"] = "PM10_0001";
  pm10_sensor["sensor_name"] = "Bụi 10";
  pm10_sensor["sensor_value"] = floatToString(pm10);
  pm10_sensor["sensor_unit"] = "ppm";

  JsonObject co_sensor = sensors.createNestedObject();
  co_sensor["sensor_id"] = "CO_0001";
  co_sensor["sensor_name"] = "CO";
  co_sensor["sensor_value"] = floatToString(co);
  co_sensor["sensor_unit"] = "ppm";

  JsonObject so2_sensor = sensors.createNestedObject();
  so2_sensor["sensor_id"] = "SO2_0001";
  so2_sensor["sensor_name"] = "SO2";
  so2_sensor["sensor_value"] = floatToString(so2);
  so2_sensor["sensor_unit"] = "ppm";

  JsonObject no2_sensor = sensors.createNestedObject();
  no2_sensor["sensor_id"] = "NO2_0001";
  no2_sensor["sensor_name"] = "NO2";
  no2_sensor["sensor_value"] = floatToString(no2);
  no2_sensor["sensor_unit"] = "ppm";

  JsonObject o3_sensor = sensors.createNestedObject();
  o3_sensor["sensor_id"] = "O3_0001";
  o3_sensor["sensor_name"] = "Ozone";
  o3_sensor["sensor_value"] = floatToString(o3);
  o3_sensor["sensor_unit"] = "ppm";

  String jsonString;
  serializeJson(doc, jsonString);
  Serial.println("Data to pub:");
  serializeJsonPretty(doc, Serial);
  doc.clear();
  Serial.println();
  return jsonString;
}


///////////////////////////////////////

SENSOR_RS485::SENSOR_RS485(){
    data_temperature = new uint8_t[8]{3, 3, 0, 0, 0, 1, 133, 232};
    data_humidity = new uint8_t[8]{3, 3, 0, 1, 0, 1, 212, 40};
    data_co = new uint8_t[8]{0x0E, 0x03, 0x00, 0x02, 0x00, 0x01, 0x25, 0x35};
    data_co2 = new uint8_t[8]{2, 3, 0, 4, 0, 1, 197, 248};
    data_so2 = new uint8_t[8]{0x0D, 0x03, 0x00, 0x02, 0x00, 0x01, 0x25, 0x06};
    data_no2 = new uint8_t[8]{0x0C, 0x03, 0x00, 0x02, 0x00, 0x01, 0x24, 0xD7};
    data_pm2_5 = new uint8_t[8]{4, 3, 0, 12, 0, 1, 68, 92};
    data_o3 = new uint8_t[8]{11, 3, 0, 2, 0, 1, 37, 96};
    data_pm10 = new uint8_t[8]{4, 3, 0, 13, 0, 1, 21, 156};
};

SENSOR_RS485::~SENSOR_RS485() {
    delete[] data_temperature;
    delete[] data_humidity;
    delete[] data_co;
    delete[] data_co2;
    delete[] data_so2;
    delete[] data_no2;
    delete[] data_pm2_5;
    delete[] data_o3;
    delete[] data_pm10;
};

// Hàm để lấy con trỏ tới giá trị nhiệt độ
uint8_t* SENSOR_RS485::getTemperature() {
    return data_temperature;
}

// Hàm để lấy con trỏ tới giá trị độ ẩm
uint8_t* SENSOR_RS485::getHumidity() {
    return data_humidity;
}

// Hàm để lấy con trỏ tới giá trị CO
uint8_t* SENSOR_RS485::getCO() {
    return data_co;
}

// Hàm để lấy con trỏ tới giá trị CO2
uint8_t* SENSOR_RS485::getCO2() {
    return data_co2;
}

// Hàm để lấy con trỏ tới giá trị SO2
uint8_t* SENSOR_RS485::getSO2() {
    return data_so2;
}

// Hàm để lấy con trỏ tới giá trị NO2
uint8_t* SENSOR_RS485::getNO2() {
    return data_no2;
}

// Hàm để lấy con trỏ tới giá trị PM2.5
uint8_t* SENSOR_RS485::getPM2_5() {
    return data_pm2_5;
}

// Hàm để lấy con trỏ tới giá trị O3
uint8_t* SENSOR_RS485::getO3() {
    return data_o3;
}

// Hàm để lấy con trỏ tới giá trị PM10
uint8_t* SENSOR_RS485::getPM10() {
    return data_pm10;
}
