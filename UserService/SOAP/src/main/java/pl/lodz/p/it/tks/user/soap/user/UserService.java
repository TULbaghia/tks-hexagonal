package pl.lodz.p.it.tks.user.soap.user;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.user.domainmodel.user.UserType;
import pl.lodz.p.it.tks.user.soap.dtosoap.UserSoap;
import pl.lodz.p.it.tks.user.soap.exception.SoapException;
import pl.lodz.p.it.tks.user.soap.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.user.soap.validation.user.AddUserValid;
import pl.lodz.p.it.tks.user.soap.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "UserAPI")
public class UserService {
    @Inject
    private UserUseCase userUseCase;

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap addUser(@AddUserValid UserSoap userSoap) {
        User newUser = User.builder()
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .userType(UserType.ADMIN)
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(userUseCase.add(newUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public List<UserSoap> getAllUsers() {
        List<UserSoap> list = userUseCase.getAll().stream().map(UserSoap::toSoap).collect(Collectors.toList());
        list.forEach(x -> x.setPassword(null));
        return list;
    }

    @WebMethod
    public UserSoap getUser(@WebParam(name="id") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = userUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = userUseCase.get(id);
        }
        UserSoap us = UserSoap.toSoap(userUseCase.get(user.getId()));
        us.setPassword(null);
        return us;
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap updateUser(@UpdateUserValid UserSoap userSoap) {
        User editingUser = UserSoap.fromSoap(userSoap);
        try {
            UserSoap addSoap = UserSoap.toSoap(userUseCase.update(editingUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap activateUser(@ActivateUserValid UserSoap userSoap) throws RepositoryAdapterException {
        User user = userUseCase.get(UUID.fromString(userSoap.getId()));

        User activatedUser = UserSoap.fromSoap(UserSoap.toSoap(user));
        user.setActive(userSoap.getActive());

        try {
            UserSoap addSoap = UserSoap.toSoap(userUseCase.update(activatedUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e ) {
            throw new SoapException(e.getMessage());
        }
    }
}
