package dev.orion.api.v1.client;


import dev.orion.api.v1.dto.ActivityCreatedDto;
import dev.orion.api.v1.dto.AddUserActivityDto;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/v1/activity")
@ApplicationScoped
@RegisterRestClient(configKey = "api.activity-service.client")
public interface ActivityClient {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ActivityCreatedDto createActivity(AddUserActivityDto dto);

    @POST
    @Path("/{activityUuid}/addUser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addUserToActivity(@PathParam("activityUuid") String activityUuid, AddUserActivityDto userExternalId);

    @PATCH
    @Path("/{activityUuid}/disconnectUser/{userExternalId}")
    Response disconnectUserFromActivity(@PathParam("activityUuid")String activityUuid, @PathParam("userExternalId") String userExternalId);

}
