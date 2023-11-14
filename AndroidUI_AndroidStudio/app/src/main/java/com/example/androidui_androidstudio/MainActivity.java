package com.example.androidui_androidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    MQTTHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startMQTT();

        // Thiết lập các nút và xử lý sự kiện click cho chúng
        Button btn_temp = findViewById(R.id.button_temp_page);
        btn_temp.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TempPage.class)));

        Button btn_humid = findViewById(R.id.button_humid_page);
        btn_humid.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HumidPage.class)));

        Button btn_pm10 = findViewById(R.id.button_pm10_page);
        btn_pm10.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PM10Page.class)));

        Button btn_pm25 = findViewById(R.id.button_pm25_page);
        btn_pm25.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PM25Page.class)));

        Button btn_co2 = findViewById(R.id.button_co2_page);
        btn_co2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CO2Page.class)));

        Button btn_so2 = findViewById(R.id.button_so2_page);
        btn_so2.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SO2Page.class)));

        Button btn_co = findViewById(R.id.button_co_page);
        btn_co.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, COPage.class)));

        Button btn_o3 = findViewById(R.id.button_o3_page);
        btn_o3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, O3Page.class)));
    }

    public void startMQTT() {
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("mqtt_log", "Connected!");
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d("mqtt_log", "Connection lost...");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("mqtt_log", "Message arrived at topic: " + topic + "; Payload: " + message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d("mqtt_log", "Delivery Success!");
            }
        });
    }
}
