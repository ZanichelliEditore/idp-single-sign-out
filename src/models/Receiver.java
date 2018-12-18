package models;

import com.rabbitmq.client.*;
import database.MySqlConfig;
import models.interfaces.IReceiver;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receiver extends Thread implements IReceiver {

    private RabbitConfig rabbitConfig;
    private MySqlConfig mySqlConfig;
    private Queue queue;

    public Receiver(RabbitConfig rabbitConfig, MySqlConfig mySqlConfig, Queue queue){
        super();
        this.rabbitConfig = rabbitConfig;
        this.mySqlConfig = mySqlConfig;
        this.queue = queue;
    }

    @Override
    public void process() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.rabbitConfig.getHost());
        factory.setUsername(this.rabbitConfig.getUsername());
        factory.setPassword(this.rabbitConfig.getPassword());

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(this.queue.getName(), false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {

                try {
                    String message = new String(body, StandardCharsets.UTF_8);
                    JSONObject obj = new JSONObject(message);

                    SessionManager sessionManager = new SessionManager(Receiver.this.mySqlConfig);
                    sessionManager.flushSessionByUserId(obj.optInt("user_id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Error during parsing rabbit message. Message by application: " +
                            Receiver.this.queue.getApplicationName());
                }

            }
        };
        channel.basicConsume(this.queue.getName(), true, consumer);
    }

    @Override
    public void run() {
        super.run();

        try {

            this.process();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
