package dev.orion.broker.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public abstract class RabbitConnection {
        protected ConnectionFactory factory;
        protected Connection connection;
        protected Channel channel;
        protected String queueName;

        
        protected RabbitConnection(String queueName) throws IOException, TimeoutException {

            this.queueName = queueName;

            this.factory = new ConnectionFactory();

            factory.setUsername(ConfigProvider.getConfig().getValue("rabbit.username", String.class));
            factory.setPassword(ConfigProvider.getConfig().getValue("rabbit.password",String.class));
            factory.setVirtualHost(ConfigProvider.getConfig().getValue("rabbit.virtualHost",String.class));
            factory.setHost(ConfigProvider.getConfig().getValue("rabbit.host",String.class));
            factory.setPort(ConfigProvider.getConfig().getValue("rabbit.port",Integer.class));

            this.connection = factory.newConnection();

            this.channel = connection.createChannel();

            channel.queueDeclare(queueName,false,false,false,null);
        }

        public void close() throws IOException, TimeoutException {
            this.channel.close();
            this.connection.close();
        }
}
