package dev.orion.api.v1.resource;


import dev.orion.api.v1.dto.ActivityCreatedDto;
import dev.orion.api.v1.dto.AddUserActivityDto;
import dev.orion.api.v1.client.ActivityClient;
import dev.orion.api.v1.mappers.models.DefaultErrorResponse;
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
        public Response createActivity(@Valid AddUserActivityDto dto){
            ActivityCreatedDto activityCreatedDto = activityIntegrationService.createActivity(dto);

            return Response.accepted(Response.Status.CREATED).entity(activityCreatedDto).build();
        }
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String document(){
            return "activityIntegrationService.document();";
        }


//    @GET
//    @Path("/{activityUuid}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response findActivity(@PathParam("activityUuid") String activityUuid) {
//        return null;
//    }
//
//    @POST
//    @Path("/{activityUuid}/addUser")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addUserToActivity(@Valid AddUserActivityDto addUserToActivityRequestDtoV1, @PathParam("activityUuid") String activityUuid) {
//        return null;
//    }

}
