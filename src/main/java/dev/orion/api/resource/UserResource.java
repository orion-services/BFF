package dev.orion.api.resource;

import dev.orion.api.dto.User;
import dev.orion.broker.consumer.Consumer;
import dev.orion.broker.producer.Producer;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


@Path("/")
public class UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(User user) throws IOException, TimeoutException {
        Producer producer = new Producer("User-IN");
        Consumer consumer = new Consumer("User-IN");

        producer.sendMessage(user.toString().getBytes(StandardCharsets.UTF_8));
        consumer.getMessage();

        return  Response.accepted().build();
    }

}
