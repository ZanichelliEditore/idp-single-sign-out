package main;

import database.MySqlConfig;
import models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args){

        try {

            File file = new File("queues.json");
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String jsonData = new String(data, StandardCharsets.UTF_8);
            JSONObject config = new JSONObject(jsonData);

            JSONObject rabbitObj = config.getJSONObject("rabbit");
            RabbitConfig rabbitConfig = new RabbitConfig(rabbitObj.getString("host"),
                    rabbitObj.getString("username"), rabbitObj.getString("password"));

            JSONArray queuesArray = config.getJSONArray("queues");

            for(int i=0; i<queuesArray.length(); i++){
                JSONObject queueObj = queuesArray.getJSONObject(i);

                Queue queue = new Queue(queueObj.getString("applicationName"), queueObj.getString("name"));

                JSONObject mysqlObj = queueObj.getJSONObject("mysql");
                MySqlConfig mySqlConfig = new MySqlConfig(mysqlObj.getString("host"), mysqlObj.getString("database"),
                        mysqlObj.getString("username"), mysqlObj.getString("password"));

                Receiver receiver = new Receiver(rabbitConfig, mySqlConfig, queue);
                receiver.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Configuration file not found");
            System.exit(0);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error during parsing configuration JSON");
            System.exit(0);
        }

    }

}
