package pl.lodz.p.it.tks.user.rest.error;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/errors")
public class ErrorsService {

    @GET
    @Path("/403")
    public Response forbidden(@Context SecurityContext securityContext) {
        return Response.status(Response.Status.FORBIDDEN).entity( Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("/401")
    public Response unauthorized(@Context SecurityContext securityContext) {
        return Response.status(Response.Status.UNAUTHORIZED).entity( Response.Status.UNAUTHORIZED).build();
    }
}
