package pl.lodz.p.it.tks.user.soap.auth;

import pl.lodz.p.it.tks.user.soap.exception.AuthException;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

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
public class SoapIdentityStore implements IdentityStore {

    @Inject
    private UserUseCase userUseCase;

    User getUserByLogin(String login) {
        List<User> allUser = new ArrayList<>(userUseCase.getAll());
        return allUser.stream().filter(x -> x.getLogin().equals(login)).findFirst().orElseThrow(AuthException::new);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            String givenPassword = usernamePasswordCredential.getPasswordAsString();
            User user = getUserByLogin(usernamePasswordCredential.getCaller());
            if (user.getPassword().equals(givenPassword) && user.isActive()) {
                String role = user.getUserType().getValue().toLowerCase();
                role = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
                return new CredentialValidationResult(user.getLogin(), new HashSet<>(Collections.singletonList(role)));
            }
            return CredentialValidationResult.INVALID_RESULT;
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
