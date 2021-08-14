package dev.orion;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import dev.orion.broker.config.RabbitConnectionFactory;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class MovieProducer {
    @Inject
    RabbitConnectionFactory connectionFactory;

    public void send(Movie movie) throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.getConnection().newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare("MovieExchange", "fanout");
            channel.queueDeclare("Movie-IN", false, false, false, null);
            channel.queueBind("Movie-IN","MovieExchange","");
            channel.basicPublish("MovieExchange", "Movie-IN", null, movie.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

}
