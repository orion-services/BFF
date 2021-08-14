package dev.orion.broker.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitConnection {
        ConnectionFactory factory;
        Connection connection;
        Channel channel;
        String queueName;
        
        public RabbitConnection(String queueName) throws IOException, TimeoutException {
            this.queueName = queueName;

            this.factory = new ConnectionFactory();
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            factory.setHost("localhost");
            factory.setPort(5672);

            this.connection = factory.newConnection();

            this.channel = connection.createChannel();

            channel.queueDeclare(queueName,false,false,false,null);
        }

        public void close() throws IOException, TimeoutException {
            this.channel.close();
            this.connection.close();
        }
}
