package pas.service.user;

import org.json.JSONObject;
import pas.service.dto.UserDto;
import pas.service.exception.RestException;
import pas.service.validation.user.ActivateUserValid;
import pas.service.validation.user.AddUserValid;
import pas.service.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user/employee")
public class EmployeeService {
    @Inject
    private UserUseCase userUseCase;

    @POST
    public String addEmployee(@AddUserValid UserDto userDto) {
        Employee newUser = Employee.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        try {
            userUseCase.add(newUser);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(userUseCase.get(newUser.getId())).toString();
    }

    @GET
    public String getAllEmployees() {
        return JSONObject.valueToString(userUseCase.getAll().stream().filter(x -> x instanceof Employee).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getEmployee(@PathParam("uuid") String id) {
        User user;
        try {
            user = userUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            user = userUseCase.get(id);
        }
        return JSONObject.wrap(userUseCase.get(user.getId())).toString();
    }

    @PUT
    public String updateEmployee(@UpdateUserValid UserDto userDto) {
        Employee editingUser = Employee.builder()
                .id(UUID.fromString(userDto.getId()))
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        try {
            userUseCase.update(editingUser);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(userUseCase.get(UUID.fromString(userDto.getId()))).toString();
    }

    @PATCH
    public String activateCustomer(@ActivateUserValid UserDto userDto) {
        Employee user = (Employee) userUseCase.get(UUID.fromString(userDto.getId()));

        Employee activatedUser = Employee.builder()
                .id(UUID.fromString(userDto.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userDto.getIsActive())
                .build();
        try {
            userUseCase.update(activatedUser);
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(userUseCase.get(UUID.fromString(userDto.getId()))).toString();
    }
}
