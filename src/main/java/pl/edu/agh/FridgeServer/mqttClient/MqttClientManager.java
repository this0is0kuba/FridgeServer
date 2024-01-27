package pl.edu.agh.FridgeServer.mqttClient;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.History;
import pl.edu.agh.FridgeServer.service.FridgeService;

import java.time.LocalDateTime;

@Component
public class MqttClientManager {

    private FridgeService fridgeService;

    private String broker       = "tcp://localhost:1883";
    private String clientId     = "JavaClient";
    private MemoryPersistence persistence = new MemoryPersistence();
    private String topic = "fridge_app/#";
    private int qos = 0;
    private MqttClient sampleClient;


    @Autowired
    MqttClientManager(FridgeService fridgeService) {

        this.fridgeService = fridgeService;

        try {

            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = setConnectionOptions();

            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            sampleClient.subscribe(topic, qos);

            setCallback();

        } catch(MqttException me) {

            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();

        }
    }

    private MqttConnectOptions setConnectionOptions() {

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName("iotclient");
        connOpts.setPassword(new char[] {'k', 'u', 'b', 'a', '1', '2', '3', '4', '5'});

        return connOpts;
    }

    private void setCallback() {

        sampleClient.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                System.out.println("Message received:\n" + "\tTopic: " + topic + "\n\tMessage: " + new String(message.getPayload()));

                String[] topicArray = topic.split("/");

                String deviceId = topicArray[1];
                String subTopic = topicArray[2];

                saveData(deviceId, subTopic, new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery completed");
            }
        });
    }

    private void saveData(String deviceId, String subTopic, String message) {

        switch (subTopic) {
            case "temp" -> saveTemp(deviceId, Double.parseDouble(message));
            case "door" -> saveInfoAboutDoor(deviceId, Boolean.parseBoolean(message));
            case "delete" -> deleteDevice(deviceId);
        }
    }

    private void saveTemp(String deviceId, Double temp) {

        Device device = fridgeService.findDeviceById(deviceId);

        if(device == null)
            return;

        LocalDateTime localDateTime = LocalDateTime.now();

        History history = new History(temp, null, localDateTime);
        history.setDevice(device);

        System.out.println(history);

        fridgeService.saveHistory(history);
    }

    private void saveInfoAboutDoor(String deviceId, Boolean closed_door) {

        Device device = fridgeService.findDeviceById(deviceId);

        if(device == null)
            return;

        LocalDateTime localDateTime = LocalDateTime.now();

        History history = new History(null, closed_door, localDateTime);
        history.setDevice(device);

        fridgeService.saveHistory(history);
    }

    private void deleteDevice(String deviceId) {
        fridgeService.deleteDevice(deviceId);
    }
}
