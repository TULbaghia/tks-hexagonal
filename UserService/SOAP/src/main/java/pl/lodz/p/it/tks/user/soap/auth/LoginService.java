package pl.lodz.p.it.tks.user.soap.auth;


import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;

@WebService(serviceName = "LoginAPI")
public class LoginService {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public String authenticate(Credentials credentials){
        Credential credential = new UsernamePasswordCredential(credentials.getLogin(), new Password(credentials.getPassword()));
        CredentialValidationResult cValResult = identityStoreHandler.validate(credential);
        if (cValResult.getStatus() == CredentialValidationResult.Status.VALID) {
            return JwtVerifier.generateJwtString(cValResult);
        } else {
            return "UNAUTHORIZED";
        }
    }
}

