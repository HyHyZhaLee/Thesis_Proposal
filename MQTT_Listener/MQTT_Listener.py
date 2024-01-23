import paho.mqtt.client as mqtt
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import json
from datetime import datetime

# Khởi tạo Firebase
cred = credentials.Certificate("firebase-key.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://database-for-air-monitoring-default-rtdb.firebaseio.com/'
})

# Hàm xử lý và chuẩn hóa timestamp
def normalize_timestamp(timestamp):
    # Chuyển đổi timestamp thành đối tượng datetime
    datetime_obj = datetime.strptime(timestamp, "%Y-%m-%d %H:%M:%S %Z%z")
    # Định dạng lại ngày và giờ theo định dạng 24 giờ
    date_str = datetime_obj.strftime("%d-%m-%Y")
    time_str = datetime_obj.strftime("%H:%M")  # Định dạng giờ theo chuẩn 24 giờ
    return date_str, time_str

# Hàm gọi khi kết nối với MQTT thành công
def on_connect(client, userdata, flags, rc):
    print("Connected with result code " + str(rc))
    client.subscribe("/bkair/airmonitoring")

# Hàm gọi khi nhận được tin nhắn từ MQTT
def on_message(client, userdata, msg):
    print(msg.topic + " " + str(msg.payload))
    data = json.loads(msg.payload.decode())

    # Lấy và chuẩn hóa timestamp
    timestamp = data.get('timestamp')
    if timestamp:
        # Chuyển đổi timestamp thành đối tượng datetime
        datetime_obj = datetime.strptime(timestamp, "%Y-%m-%d %H:%M:%S %Z%z")

        # Kiểm tra nếu năm nhỏ hơn 2010
        if datetime_obj.year < 2010:
            # Nếu năm nhỏ hơn 2010, không ghi dữ liệu vào Firebase
            print("Dữ liệu có năm nhỏ hơn 2010, không ghi vào Firebase.")
            return
<<<<<<< Updated upstream
        normalized_date, normalized_time = normalize_timestamp(timestamp)
        print(normalized_date)
        # Ghi dữ liệu vào Firebase
        db.reference(f"/airmonitoring/{normalized_date}/{normalized_time}" + "_GMT_0700").set(data)
=======
>>>>>>> Stashed changes

        date_str, time_str = normalize_timestamp(timestamp)

        # Ghi dữ liệu của từng cảm biến vào Firebase
        for sensor in data.get('sensors', []):
            sensor_name = sensor.get('sensor_name')
            if sensor_name:
                # Chỉnh sửa tên sensor nếu cần
                if sensor_name == 'Bụi 2.5':
                    sensor_name = 'PM25'
                elif sensor_name == 'Bụi 10':
                    sensor_name = 'PM10'
                elif sensor_name == 'Nhiệt Độ':
                    sensor_name = 'Temperature'
                elif sensor_name == 'Độ Ẩm':
                    sensor_name = 'Humidity'

                db.reference(f"/airmonitoringV2/{sensor_name}/{date_str}/{time_str}").set(sensor.get('sensor_value'))

        # Ghi dữ liệu cập nhật mới nhất vào Firebase
        db.reference("/airmonitoringV2/lastUpdate").set(timestamp)
        # Ghi thông tin của trạm vào Firebase
        station_info = {
            'station_id': data.get('station_id'),
            'gps_longitude': data.get('gps_longitude'),
            'gps_latitude': data.get('gps_latitude'),
            'station_name': data.get('station_name')
        }
        db.reference("/airmonitoringV2/station_info").set(station_info)


# Hàm xóa toàn bộ dữ liệu trong nhánh /airmonitoringV2
def delete_airmonitoring_data():
    db.reference("/airmonitoringV2").delete()
    print("Đã xóa toàn bộ dữ liệu trong /airmonitoringV2")

# Gọi hàm để xóa dữ liệu
# delete_airmonitoring_data()

# Kết nối với MQTT Server
client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.username_pw_set("bkair", password="bkair_RgPQAZoA5N")
client.connect("mqttserver.tk", 1883, 60)

client.loop_forever()