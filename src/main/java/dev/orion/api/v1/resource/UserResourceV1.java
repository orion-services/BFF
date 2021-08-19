package dev.orion.api.v1.resource;




import dev.orion.api.v1.dto.LoginUserDto;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/user")
public class UserResourceV1 {


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(@Valid LoginUserDto user){
        return null;
    }
}
