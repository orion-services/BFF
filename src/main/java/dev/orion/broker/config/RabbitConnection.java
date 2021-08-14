package dev.orion.broker.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class RabbitConnectionFactory {
        protected ConnectionFactory factory;
        protected Connection connection;
        protected Channel channel;
        protected String queueName;


        public RabbitConnectionFactory(String queueName){

            factory = new ConnectionFactory();
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setVirtualHost("/");
            factory.setHost("localhost");
            factory.setPort(5672);


        }

        public ConnectionFactory getConnection(){

            return factory;
        }
}
