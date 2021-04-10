package pl.lodz.p.it.tks.soap.user;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.soap.dtosoap.UserSoap;
import pl.lodz.p.it.tks.soap.exception.SoapException;
import pl.lodz.p.it.tks.soap.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.soap.validation.user.AddUserValid;
import pl.lodz.p.it.tks.soap.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
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
@WebService(serviceName = "AdminAPI")
public class AdminService {
    @Inject
    private AdminUseCase adminUseCase;

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap addAdmin(@AddUserValid UserSoap userSoap) {
        Admin newUser = Admin.builder()
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(adminUseCase.add(newUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public List<UserSoap> getAllAdmins() {
        List<UserSoap> list = adminUseCase.getAll().stream().map(UserSoap::toSoap).collect(Collectors.toList());
        list.forEach(x -> x.setPassword(null));
        return list;
    }

    @WebMethod
    public UserSoap getAdmin(@WebParam(name="id") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = adminUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = adminUseCase.get(id);
        }
        UserSoap us = UserSoap.toSoap(adminUseCase.get(user.getId()));
        us.setPassword(null);
        return us;
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap updateAdmin(@UpdateUserValid UserSoap userSoap) {
        Admin editingUser = Admin.builder()
                .id(UUID.fromString(userSoap.getId()))
                .login(userSoap.getLogin())
                .password(userSoap.getPassword())
                .firstname(userSoap.getFirstname())
                .lastname(userSoap.getLastname())
                .build();
        try {
            UserSoap addSoap = UserSoap.toSoap(adminUseCase.update(editingUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap activateAdmin(@ActivateUserValid UserSoap userSoap) throws RepositoryAdapterException {
        Admin user = adminUseCase.get(UUID.fromString(userSoap.getId()));

        Admin activatedUser = Admin.builder()
                .id(UUID.fromString(userSoap.getId()))
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .login(user.getLogin())
                .password(user.getPassword())
                .isActive(userSoap.getActive())
                .build();

        try {
            UserSoap addSoap = UserSoap.toSoap(adminUseCase.update(activatedUser));
            addSoap.setPassword(null);
            return addSoap;
        } catch (RepositoryAdapterException e ) {
            throw new SoapException(e.getMessage());
        }
    }
}
