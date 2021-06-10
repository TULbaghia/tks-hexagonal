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

}
