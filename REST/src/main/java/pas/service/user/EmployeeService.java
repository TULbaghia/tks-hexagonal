package pas.service.user;

import org.json.JSONObject;
import pas.service.dto.UserDto;
import pas.service.exception.RestException;
import pas.service.validation.user.ActivateUserValid;
import pas.service.validation.user.AddUserValid;
import pas.service.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.EmployeeUseCase;
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
    private EmployeeUseCase employeeUseCase;

    @POST
    public String addEmployee(@AddUserValid UserDto userDto) throws RepositoryAdapterException {
        Employee newUser = Employee.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        try {
            return JSONObject.wrap(employeeUseCase.add(newUser)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllEmployees() {
        return JSONObject.valueToString(employeeUseCase.getAll().stream().filter(x -> x instanceof Employee).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getEmployee(@PathParam("uuid") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = employeeUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = employeeUseCase.get(id);
        }
        return JSONObject.wrap(employeeUseCase.get(user.getId())).toString();
    }

    @PUT
    public String updateEmployee(@UpdateUserValid UserDto userDto) throws RepositoryAdapterException {
        Employee editingUser = Employee.builder()
                .id(UUID.fromString(userDto.getId()))
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        try {
            return JSONObject.wrap(employeeUseCase.update(editingUser)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PATCH
    public String activateCustomer(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        Employee user = (Employee) employeeUseCase.get(UUID.fromString(userDto.getId()));

        Employee activatedUser = Employee.builder()
                .id(UUID.fromString(userDto.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userDto.getIsActive())
                .build();
        try {
            return JSONObject.wrap(employeeUseCase.update(activatedUser)).toString();
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
    }
}
