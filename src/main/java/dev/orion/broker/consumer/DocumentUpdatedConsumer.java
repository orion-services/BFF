package dev.orion.broker.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import dev.orion.api.v1.sockets.room.ActivityRoom;
import dev.orion.broker.config.RabbitConnection;
import dev.orion.broker.dto.EditorUpdateQueueDto;
import io.quarkus.arc.log.LoggerName;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


@ApplicationScoped
public class DocumentUpdatedConsumer extends RabbitConnection {
    @Inject
    ActivityRoom activityRoom;

    @LoggerName("DocumentEditorConsumer")
    Logger logger;

    final static String queueName = ConfigProvider.getConfig().getValue("rabbit.queue.consumer.document", String.class);
    final Boolean autoAck = true;
    private Boolean hasStarted = false;
    public DocumentUpdatedConsumer() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this(queueName);
    }

    public DocumentUpdatedConsumer(String queue) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        super(queue);
    }

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        ObjectMapper objectMapper = new ObjectMapper();
        var message = objectMapper.readValue(delivery.getBody(), EditorUpdateQueueDto.class);

        activityRoom.broadcast(objectMapper.writeValueAsString(message), UUID.fromString(message.activityUuid));
    };


    public void attachQueueListener() throws IOException {
        if (!hasStarted) {
            logger.info(MessageFormat.format("Started listening queue: {0}", queueName));
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {});
            hasStarted = true;
        }
    }
}
