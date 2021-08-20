package dev.orion.broker.producer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.AMQP;
import dev.orion.broker.config.RabbitConnection;
import dev.orion.broker.dto.EditorQueueDto;
import org.eclipse.microprofile.config.ConfigProvider;


import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class EditorQueueProducer extends RabbitConnection {

    final static String queueName = ConfigProvider.getConfig().getValue("rabbit.queue.producer.editor", String.class);

    public EditorQueueProducer() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        this(queueName);
    }


    public EditorQueueProducer(String queueName) throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        super(queueName);
    }

    public void sendMessage(EditorQueueDto editorQueueDto) throws IOException {
        AMQP.BasicProperties messageProperties = new AMQP.BasicProperties.Builder().contentType("application/json").build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        byte[] msg = objectMapper.writeValueAsBytes(editorQueueDto);

        channel.basicPublish("", queueName, messageProperties, msg);
    }

}
