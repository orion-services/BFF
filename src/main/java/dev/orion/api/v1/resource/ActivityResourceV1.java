package dev.orion.api.v1.resource;

import dev.orion.api.v1.dto.ActivityCreatedDto;
import dev.orion.api.v1.dto.AddUserActivityDto;
import dev.orion.api.v1.client.ActivityClient;
import dev.orion.api.v1.model.Activity;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/activity")
@ApplicationScoped
public class ActivityResourceV1 {
        @Inject
        @RestClient
        ActivityClient activityIntegrationService;

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response createActivity(@Valid AddUserActivityDto dto) {
                ActivityCreatedDto addUserActivityDto = activityIntegrationService.createActivity(dto);
                return Response.accepted().entity(addUserActivityDto).build();
        }

}
