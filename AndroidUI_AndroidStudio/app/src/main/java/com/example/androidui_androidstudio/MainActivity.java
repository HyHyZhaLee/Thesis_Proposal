package com.example.androidui_androidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("mqtt_log","Connected!");
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d("mqtt_log","Connection lost...");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("mqtt_log","Message arrvied at topic: "  +topic + "; Payload: " +  message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d("mqtt_log","Delivery Success!");
            }
        });
    }
}