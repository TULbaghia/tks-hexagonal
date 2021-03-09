package pas.service.user;

import org.json.JSONObject;
import pas.service.dto.UserDto;
import pas.service.exception.RestException;
import pas.service.validation.user.ActivateUserValid;
import pas.service.validation.user.AddUserValid;
import pas.service.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("user/customer")
public class CustomerService {
    @Inject
    private CustomerUseCase customerUseCase;

    @POST
    public String addCustomer(@AddUserValid UserDto userDto) throws RepositoryAdapterException {
        Customer newUser = Customer.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        try {
            return JSONObject.wrap(customerUseCase.add(newUser)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllCustomers() {
        return JSONObject.valueToString(customerUseCase.getAll().stream().filter(x -> x instanceof Customer).collect(Collectors.toList()));
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
        return JSONObject.wrap(customerUseCase.get(user.getId())).toString();
    }

    @PUT
    public String updateCustomer(@UpdateUserValid UserDto userDto) throws RepositoryAdapterException {
        Customer editingUser = Customer.builder()
                .id(UUID.fromString(userDto.getId()))
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        try {
            return JSONObject.wrap(customerUseCase.update(editingUser)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PATCH
    public String activateCustomer(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        Customer user = (Customer) customerUseCase.get(UUID.fromString(userDto.getId()));

        Customer activatedUser = Customer.builder()
                .id(UUID.fromString(userDto.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userDto.getIsActive())
                .build();
        try {
            return JSONObject.wrap(customerUseCase.update(activatedUser)).toString();
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
    }
}
