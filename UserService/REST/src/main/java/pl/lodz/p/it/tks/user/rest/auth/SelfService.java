package pl.lodz.p.it.tks.user.rest.auth;

import org.json.JSONObject;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.rest.dto.UserDto;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/auth/self")
public class SelfService {
    @Inject
    private RestIdentityStore restIdentityStore;

    @GET
    public String getSelf(@Context SecurityContext securityContext) {
        User user = restIdentityStore.getUserByLogin(securityContext.getUserPrincipal().getName());
        String filterApplied = JSONObject.wrap(UserDto.toDto(user)).toString();
        JSONObject jsonObject = new JSONObject(filterApplied);
        jsonObject.put("group", user.getClass().getSimpleName());

        return jsonObject.toString();
    }
}
