package pl.lodz.p.it.tks.rent.rest.user;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.rest.dto.UserDto;
import pl.lodz.p.it.tks.rent.rest.exception.RestException;
import pl.lodz.p.it.tks.rent.rest.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.rent.rest.validation.user.AddUserValid;
import pl.lodz.p.it.tks.rent.rest.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user/admin")
public class AdminService {
    private final AdminUseCase adminUseCase;

    @Inject
    public AdminService(AdminUseCase adminUseCase) {
        this.adminUseCase = adminUseCase;
    }

    @GET
    public String getAllAdmins() {
        return JSONObject.valueToString(adminUseCase.getAll().stream().map(UserDto::toDto).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getAdmin(@PathParam("uuid") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = adminUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = adminUseCase.get(id);
        }
        return JSONObject.wrap(UserDto.toDto(adminUseCase.get(user.getId()))).toString();
    }
}
