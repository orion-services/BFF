package dev.orion.broker.producer;

import dev.orion.broker.config.RabbitConnection;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EditorQueueProducer extends RabbitConnection {

    public EditorQueueProducer(String queueName) throws IOException, TimeoutException {
        super(queueName);
    }

    public void sendMessage(byte[] msg) throws IOException {



        channel.basicPublish("", queueName, null, msg);
    }

}
