package pl.lodz.p.it.tks.rest.user;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rest.dto.UserDto;
import pl.lodz.p.it.tks.rest.exception.RestException;
import pl.lodz.p.it.tks.rest.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.rest.validation.user.AddUserValid;
import pl.lodz.p.it.tks.rest.validation.user.UpdateUserValid;
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
    private final CustomerUseCase customerUseCase;

    @Inject
    public CustomerService(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @POST
    public String addCustomer(@AddUserValid UserDto userDto) {
        Customer newUser = Customer.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .build();
        try {
            return JSONObject.wrap(UserDto.toDto(customerUseCase.add(newUser))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
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

    @PUT
    public String updateCustomer(@UpdateUserValid UserDto userDto) {
        Customer editingUser = Customer.builder()
                .id(UUID.fromString(userDto.getId()))
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        try {
            return JSONObject.wrap(UserDto.toDto(customerUseCase.update(editingUser))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PATCH
    public String activateCustomer(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        Customer user = customerUseCase.get(UUID.fromString(userDto.getId()));

        Customer activatedUser = Customer.builder()
                .id(UUID.fromString(userDto.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userDto.getActive())
                .build();
        try {
            return JSONObject.wrap(UserDto.toDto(customerUseCase.update(activatedUser))).toString();
        } catch (RepositoryAdapterException e ) {
            throw new RestException(e.getMessage());
        }
    }
}
