package dev.orion.api.v1.client;

import dev.orion.api.v1.dto.ActivityCreatedDto;
import dev.orion.api.v1.dto.AddUserActivityDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/activity")
@ApplicationScoped
@RegisterRestClient(configKey = "api.activity-service.client")
public interface ActivityClient {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ActivityCreatedDto createActivity(AddUserActivityDto dto);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String document();
}
