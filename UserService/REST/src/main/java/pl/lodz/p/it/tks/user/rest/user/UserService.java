package pl.lodz.p.it.tks.user.rest.user;

import org.json.JSONObject;
import pl.lodz.p.it.tks.user.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.user.rest.dto.UserDto;
import pl.lodz.p.it.tks.user.rest.exception.RestException;
import pl.lodz.p.it.tks.user.rest.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.user.rest.validation.user.AddUserValid;
import pl.lodz.p.it.tks.user.rest.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user")
public class UserService {
    private final UserUseCase userUseCase;

    @Inject
    public UserService(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @POST
    public String addUser(@AddUserValid UserDto userDto) {
        User newUser = UserDto.fromDto(userDto);
        try {
            return JSONObject.wrap(UserDto.toDto(userUseCase.add(newUser))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllUsers() {
        return JSONObject.valueToString(userUseCase.getAll().stream().map(UserDto::toDto).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getUser(@PathParam("uuid") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = userUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = userUseCase.get(id);
        }
        return JSONObject.wrap(UserDto.toDto(userUseCase.get(user.getId()))).toString();
    }

    @PUT
    public String updateUser(@UpdateUserValid UserDto userDto) {
        User editingUser = UserDto.fromDto(userDto);
        try {
            return JSONObject.wrap(UserDto.toDto(userUseCase.update(editingUser))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PATCH
    public String activateUser(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        User user = userUseCase.get(UUID.fromString(userDto.getId()));

        User activatedUser = UserDto.fromDto(UserDto.toDto(user));
        activatedUser.setActive(userDto.getActive());
        try {
            return JSONObject.wrap(UserDto.toDto(userUseCase.update(activatedUser))).toString();
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
    }
}
