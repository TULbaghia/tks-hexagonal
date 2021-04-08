package pl.lodz.p.it.tks.soap.user;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.soap.dtosoap.UserSoap;
import pl.lodz.p.it.tks.soap.exception.SoapException;
import pl.lodz.p.it.tks.soap.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.soap.validation.user.AddUserValid;
import pl.lodz.p.it.tks.soap.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.EmployeeUseCase;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.domainmodel.user.User;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "EmployeeService")
public class EmployeeService {
    @Inject
    private EmployeeUseCase employeeUseCase;


    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap addEmployee(@AddUserValid UserSoap userSoap) {
        Employee newUser = Employee.builder()
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(employeeUseCase.add(newUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public List<UserSoap> getAllEmployees() {
        List<UserSoap> list = employeeUseCase.getAll().stream().map(UserSoap::toSoap).collect(Collectors.toList());
        list.forEach(x -> x.setPassword(null));
        return list;
    }

    @WebMethod
    public UserSoap getEmployee(@WebParam(name="id") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = employeeUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = employeeUseCase.get(id);
        }
        UserSoap us = UserSoap.toSoap(employeeUseCase.get(user.getId()));
        us.setPassword(null);
        return us;
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap updateEmployee(@UpdateUserValid UserSoap userSoap) {
        Employee editingUser = Employee.builder()
                .id(UUID.fromString(userSoap.getId()))
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(employeeUseCase.update(editingUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap activateCustomer(@ActivateUserValid UserSoap userSoap) throws RepositoryAdapterException {
        Employee user = employeeUseCase.get(UUID.fromString(userSoap.getId()));

        Employee activatedUser = Employee.builder()
                .id(UUID.fromString(userSoap.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userSoap.getActive())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(employeeUseCase.update(activatedUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e ) {
            throw new SoapException(e.getMessage());
        }
    }
}
