package pl.lodz.p.it.tks.rent.rest.auth;

import pl.lodz.p.it.tks.rent.rest.exception.AuthException;
import pl.lodz.p.it.tks.rent.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.rent.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.rent.applicationports.ui.EmployeeUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class RestIdentityStore implements IdentityStore {

    @Inject
    private EmployeeUseCase employeeUseCase;
    @Inject
    private AdminUseCase adminUseCase;
    @Inject
    private CustomerUseCase customerUseCase;

    User getUserByLogin(String login) {
        List<User> allUser = new ArrayList<>(employeeUseCase.getAll());
        allUser.addAll(adminUseCase.getAll());
        allUser.addAll(customerUseCase.getAll());
        return allUser.stream().filter(x -> x.getLogin().equals(login)).findFirst().orElseThrow(AuthException::new);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            String givenPassword = usernamePasswordCredential.getPasswordAsString();
            User user = getUserByLogin(usernamePasswordCredential.getCaller());
            if (user.getPassword().equals(givenPassword) && user.isActive()) {
                return new CredentialValidationResult(user.getLogin(), new HashSet<>(Collections.singletonList(user.getClass().getSimpleName())));
            }
            return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
