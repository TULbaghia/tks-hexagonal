package pl.lodz.p.it.tks.rent.rest.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth/login")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class LoginService {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @POST
    @Path("/")
    public Response authenticate(@NotNull Credentials credentials){
        Credential credential = new UsernamePasswordCredential(credentials.getLogin(), new Password(credentials.getPassword()));
        CredentialValidationResult cValResult = identityStoreHandler.validate(credential);
        if (cValResult.getStatus() == CredentialValidationResult.Status.VALID) {
            String jwtToken = JwtVerifier.generateJwtString(cValResult);
            return Response
                    .accepted()
                    .type("application/jwt")
                    .entity(jwtToken)
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity( Response.Status.UNAUTHORIZED).build();
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Credentials {
        @JsonProperty
        private String login;
        @JsonProperty
        private String password;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

