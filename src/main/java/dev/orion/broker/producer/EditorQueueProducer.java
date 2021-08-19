package dev.orion.broker.producer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.tools.json.JSONWriter;
import dev.orion.broker.config.RabbitConnection;
import dev.orion.broker.dto.EditorUpdateQueueDto;
import org.eclipse.microprofile.config.ConfigProvider;


import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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

    public void sendMessage(EditorUpdateQueueDto editorUpdateQueueDto) throws IOException {
        AMQP.BasicProperties messageProperties = new AMQP.BasicProperties.Builder().contentType("application/json").build();
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] msg = objectMapper.writeValueAsBytes(editorUpdateQueueDto);

        channel.basicPublish("", queueName, messageProperties, msg);
    }

}
