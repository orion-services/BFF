package dev.orion.broker.config;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer extends RabbitConnection {

    public Producer(String queueName) throws IOException, TimeoutException {
        super(queueName);
    }

    public void sendMessage(byte[] msg) throws IOException {
        channel.basicPublish("", queueName, null, msg);
    }

}
