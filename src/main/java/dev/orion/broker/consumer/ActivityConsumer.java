package dev.orion.broker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import dev.orion.api.v1.sockets.room.ActivityRoom;
import dev.orion.broker.config.RabbitConnection;
import dev.orion.broker.dto.ActivityUpdateQueueDto;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Singleton
public class ActivityConsumer extends RabbitConnection {
    @Inject
    ActivityRoom activityRoom;

    final static String queueName = ConfigProvider.getConfig().getValue("rabbit.queue.consumer.activity", String.class);
    final Boolean autoAck = true;

    public ActivityConsumer() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this(queueName);

    }

    public ActivityConsumer(String queue) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        super(queue);
        this.attachQueueListener();

    }
    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(delivery);
        var message = objectMapper.readValue(delivery.getBody(), ActivityUpdateQueueDto.class);

        System.out.println(" [x] Received '" + message + "'");
        try {
        //
        } finally {
            System.out.println(" [x] Done");
        };
    };


    public void attachQueueListener() throws IOException {
        channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {});
    }
}
