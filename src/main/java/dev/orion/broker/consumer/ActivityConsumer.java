package dev.orion.broker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import dev.orion.api.v1.sockets.room.ActivityRoom;
import dev.orion.broker.config.RabbitConnection;
import dev.orion.broker.dto.ActivityUpdateQueueDto;
import io.quarkus.arc.log.LoggerName;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class ActivityConsumer extends RabbitConnection {
    @Inject
    ActivityRoom activityRoom;

    @LoggerName("DocumentEditorConsumer")
    Logger logger;

    final static String queueName = ConfigProvider.getConfig().getValue("rabbit.queue.consumer.activity", String.class);
    final Boolean autoAck = false;
    private Boolean hasStarted = false;
    public ActivityConsumer() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this(queueName);

    }

    public ActivityConsumer(String queue) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        super(queue);
    }

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValueAsString(delivery);
        var message = objectMapper.readValue(delivery.getBody(), ActivityUpdateQueueDto.class);

        activityRoom.broadcast(message.toString(), message.uuid);
    };


    public void attachQueueListener() throws IOException {
        if (!hasStarted) {
            logger.info(MessageFormat.format("Started listening queue: {0}", queueName));
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {});
            hasStarted = true;
        }
    }
}
