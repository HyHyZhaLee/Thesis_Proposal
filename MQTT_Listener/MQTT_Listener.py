import paho.mqtt.client as mqtt
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import json
import re
from datetime import datetime

# Khởi tạo Firebase
cred = credentials.Certificate("firebase-key.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://database-for-air-monitoring-default-rtdb.firebaseio.com/'
})


# Hàm gọi khi kết nối với MQTT thành công
def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))
    client.subscribe("/bkair/airmonitoring")


# Hàm xử lý và chuẩn hóa timestamp
def normalize_timestamp(timestamp):
    # Tách timestamp thành ngày và giờ, loại bỏ các ký tự không hợp lệ
    date_part, time_part = timestamp.split(' ')[0], timestamp.split(' ')[1]
    normalized_date = re.sub(r'[^\w]', '_', date_part)
    normalized_time = re.sub(r'[^\w]', '_', time_part)
    return normalized_date, normalized_time


# Hàm gọi khi nhận được tin nhắn từ MQTT
def on_message(client, userdata, msg):
    print(msg.topic + " " + str(msg.payload))
    data = json.loads(msg.payload.decode())

    # Lấy và chuẩn hóa timestamp
    timestamp = data.get('timestamp')
    if timestamp:
        # Chuyển đổi timestamp thành đối tượng datetime để dễ dàng so sánh
        datetime_obj = datetime.strptime(timestamp, "%Y-%m-%d %H:%M:%S %Z%z")

        # Kiểm tra nếu năm nhỏ hơn 2010
        if datetime_obj.year < 2010:
            # Nếu năm nhỏ hơn 2010, không ghi dữ liệu vào Firebase
            print("Dữ liệu có năm nhỏ hơn 2010, không ghi vào Firebase.")
            return
        normalized_date, normalized_time = normalize_timestamp(timestamp)
        print(normalized_date)
        # Ghi dữ liệu vào Firebase
        db.reference(f"/airmonitoring/{normalized_date}/{normalized_time}" + "_GMT_0700").set(data)


# Kết nối với MQTT Server
client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.username_pw_set("bkair", password="bkair_RgPQAZoA5N")
client.connect("mqttserver.tk", 1883, 60)

client.loop_forever()
