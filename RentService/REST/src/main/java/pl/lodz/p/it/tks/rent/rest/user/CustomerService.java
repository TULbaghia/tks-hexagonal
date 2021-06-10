package pl.lodz.p.it.tks.rent.rest.user;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.rest.dto.UserDto;
import pl.lodz.p.it.tks.rent.rest.exception.RestException;
import pl.lodz.p.it.tks.rent.rest.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.rent.rest.validation.user.AddUserValid;
import pl.lodz.p.it.tks.rent.rest.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user/customer")
public class CustomerService {
    private final CustomerUseCase customerUseCase;

    @Inject
    public CustomerService(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @GET
    public String getAllCustomers() {
        return JSONObject.valueToString(customerUseCase.getAll().stream().map(UserDto::toDto).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getCustomer(@PathParam("uuid") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = customerUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = customerUseCase.get(id);
        }
        return JSONObject.wrap(UserDto.toDto(customerUseCase.get(user.getId()))).toString();
    }

}
