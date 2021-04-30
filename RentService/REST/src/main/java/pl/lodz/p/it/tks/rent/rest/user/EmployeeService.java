package pl.lodz.p.it.tks.rent.rest.user;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.rest.dto.UserDto;
import pl.lodz.p.it.tks.rent.rest.exception.RestException;
import pl.lodz.p.it.tks.rent.rest.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.rent.rest.validation.user.AddUserValid;
import pl.lodz.p.it.tks.rent.rest.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.EmployeeUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user/employee")
public class EmployeeService {
    private final EmployeeUseCase employeeUseCase;

    @Inject
    public EmployeeService(EmployeeUseCase employeeUseCase) {
        this.employeeUseCase = employeeUseCase;
    }

    @POST
    public String addEmployee(@AddUserValid UserDto userDto) {
        Employee newUser = Employee.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .build();
        try {
            return JSONObject.wrap(UserDto.toDto(employeeUseCase.add(newUser))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllEmployees() {
        return JSONObject.valueToString(employeeUseCase.getAll().stream().map(UserDto::toDto).collect(Collectors.toList()));
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
        return JSONObject.wrap(UserDto.toDto(employeeUseCase.get(user.getId()))).toString();
    }

    @PUT
    public String updateEmployee(@UpdateUserValid UserDto userDto) {
        Employee editingUser = Employee.builder()
                .id(UUID.fromString(userDto.getId()))
                .login(userDto.getLogin())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        try {
            return JSONObject.wrap(UserDto.toDto(employeeUseCase.update(editingUser))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PATCH
    public String activateCustomer(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        Employee user = employeeUseCase.get(UUID.fromString(userDto.getId()));

        Employee activatedUser = Employee.builder()
                .id(UUID.fromString(userDto.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .isActive(userDto.getActive())
                .build();
        try {
            return JSONObject.wrap(UserDto.toDto(employeeUseCase.update(activatedUser))).toString();
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
    }
}
