package pas.service.user;

import org.json.JSONObject;
import pas.service.dto.UserDto;
import pas.service.exception.RestException;
import pas.service.validation.user.ActivateUserValid;
import pas.service.validation.user.AddUserValid;
import pas.service.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user/admin")
public class AdminService {
    @Inject
    private AdminUseCase adminUseCase;

    @POST
    public String addAdmin(@AddUserValid UserDto userDto) throws RepositoryAdapterException {
        Admin newUser = Admin.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        try {
            return JSONObject.wrap(adminUseCase.add(newUser)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllAdmins() {
        return JSONObject.valueToString(adminUseCase.getAll().stream().filter(x -> x instanceof Admin).collect(Collectors.toList()));
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
        return JSONObject.wrap(adminUseCase.get(user.getId())).toString();
    }

    @PUT
    public String updateAdmin(@UpdateUserValid UserDto userDto) throws RepositoryAdapterException {
        Admin editingUser = Admin.builder()
                .id(UUID.fromString(userDto.getId()))
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        try {
            return JSONObject.wrap(adminUseCase.update(editingUser)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PATCH
    public String activateAdmin(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        Admin user = (Admin) adminUseCase.get(UUID.fromString(userDto.getId()));

        Admin activatedUser = Admin.builder()
                .id(UUID.fromString(userDto.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userDto.getIsActive())
                .build();
        try {
            return JSONObject.wrap(adminUseCase.update(activatedUser)).toString();
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
    }
}
