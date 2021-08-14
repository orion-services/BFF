package dev.orion.broker.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


public class Consumer extends RabbitConnection{
    final String queueName;
    public Consumer(String queue) throws IOException, TimeoutException {
        super(queue);
        this.queueName = queue;
    }

    public void getMessage() throws IOException {
        DefaultConsumer consumer = new DefaultConsumer(super.channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String mensagem = new String(body, StandardCharsets.UTF_8);
                System.out.println(mensagem);
            }
        };
        channel.basicConsume(queueName,consumer);
    }
}
