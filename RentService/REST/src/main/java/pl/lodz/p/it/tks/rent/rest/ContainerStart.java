package pl.lodz.p.it.tks.rent.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("start")
public class ContainerStart {

    @GET
    public Response start() {
        return Response.status(200).build();
    }
}
