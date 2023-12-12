#include <stdint.h>
/*
 * sensor_data.h
 *
 *  Created on: Oct 23, 2023
 *      Author: Huy Ly
 */

#ifndef INC_SENSOR_DATA_H_
#define INC_SENSOR_DATA_H_

#include <ArduinoJson.h>
#include <WiFi.h>
#include <NTPClient.h>
#include <WiFiUdp.h>
#include <TimeLib.h> // Thêm thư viện TimeLib

class SENSOR_DATA{
  public:
    SENSOR_DATA(){};
    String floatToString(float value);
    String formatTimestamp(time_t timestamp);
    
    String createAQIStationJSON(float temp, float humi, float co, float co2, float so2, float no2, float pm25, float pm10, float o3);
};

class SENSOR_RS485{
  private:
    uint8_t* data_temperature;
    uint8_t* data_humidity;
    uint8_t* data_co;
    uint8_t* data_co2;
    uint8_t* data_so2;
    uint8_t* data_no2;
    uint8_t* data_pm2_5;
    uint8_t* data_o3;
    uint8_t* data_pm10;

  public:
    SENSOR_RS485();
    ~SENSOR_RS485();
    uint8_t* getTemperature();
    uint8_t* getHumidity();
    uint8_t* getCO();
    uint8_t* getCO2();
    uint8_t* getSO2();
    uint8_t* getNO2();
    uint8_t* getPM2_5();
    uint8_t* getO3();
    uint8_t* getPM10();
};

#endif
