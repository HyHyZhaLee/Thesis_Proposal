package com.example.androidui_androidstudio;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import info.mqtt.android.service.Ack;
import info.mqtt.android.service.MqttAndroidClient;
//import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTTHelper {
    public MqttAndroidClient mqttAndroidClient;

    public final String[] arrayTopics = {"AI_ProjectHGL/feeds/test"};

    final String clientId = "12345678";
    final String username = "AI_ProjectHGL";
    String password = "aio_" + "CHRX08OBUahjbuHBFWdtmdIVwibh";

    final String serverUri = "tcp://io.adafruit.com:1883";

    public MQTTHelper(Context context){
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId, Ack.AUTO_ACK);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("mqtt_log", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("mqtt_log", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (Exception ex){ // Thay thế MqttException bằng Exception hoặc ngoại lệ cụ thể khác
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        for(int i = 0; i < arrayTopics.length; i++) {
            try {
                mqttAndroidClient.subscribe(arrayTopics[i], 0, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d("mqtt_log", "Subscribed!");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.d("mqtt_log", "Subscribed fail!");
                    }
                });

            } catch (Exception ex) { // Thay thế MqttException bằng Exception hoặc ngoại lệ cụ thể khác
                System.err.println("Exception subscribing");
                ex.printStackTrace();
            }
        }
    }

}