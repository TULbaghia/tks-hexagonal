package pl.lodz.p.it.tks.rent.soap.user;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.rent.soap.dtosoap.UserSoap;
import pl.lodz.p.it.tks.rent.soap.exception.SoapException;
import pl.lodz.p.it.tks.rent.soap.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.rent.soap.validation.user.AddUserValid;
import pl.lodz.p.it.tks.rent.soap.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "CustomerAPI")
public class CustomerService {

    @Inject
    private CustomerUseCase customerUseCase;

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap addCustomer(@AddUserValid UserSoap userSoap) {
        Customer newUser = Customer.builder()
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(customerUseCase.add(newUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public List<UserSoap>  getAllCustomers() {
        List<UserSoap> list = customerUseCase.getAll().stream().map(UserSoap::toSoap).collect(Collectors.toList());
        list.forEach(x -> x.setPassword(null));
        return list;
    }

    @WebMethod
    public UserSoap getCustomer(@WebParam(name="id") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = customerUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = customerUseCase.get(id);
        }
        UserSoap us = UserSoap.toSoap(customerUseCase.get(user.getId()));
        us.setPassword(null);
        return us;
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap updateCustomer(@UpdateUserValid UserSoap userSoap) {
        Customer editingUser = Customer.builder()
                .id(UUID.fromString(userSoap.getId()))
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(customerUseCase.update(editingUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap activateCustomer(@ActivateUserValid UserSoap userSoap) throws RepositoryAdapterException {
        Customer user = customerUseCase.get(UUID.fromString(userSoap.getId()));

        Customer activatedUser = Customer.builder()
                .id(UUID.fromString(userSoap.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userSoap.getActive())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(customerUseCase.update(activatedUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e ) {
            throw new SoapException(e.getMessage());
        }
    }
}
