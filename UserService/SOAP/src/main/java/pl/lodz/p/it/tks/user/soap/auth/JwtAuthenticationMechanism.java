package pl.lodz.p.it.tks.user.soap.auth;

import com.nimbusds.jwt.SignedJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@ApplicationScoped
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {

    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER = "Bearer ";

    @Inject
    private JwtVerifier jwtVerifier;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) {
        if(httpServletRequest.getRequestURL().toString().endsWith("/LoginAPI")) {
            return httpMessageContext.doNothing();
        }
//        return httpMessageContext.doNothing();

        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            return httpMessageContext.responseUnauthorized();
        }

        String jwtSerializedToken = authorizationHeader.substring(BEARER.length()).trim();
        if(JwtVerifier.validateJwtSignature(jwtSerializedToken)) {
            try {
                SignedJWT signedJWT = SignedJWT.parse(jwtSerializedToken);
                String login = signedJWT.getJWTClaimsSet().getSubject();
                String groups = signedJWT.getJWTClaimsSet().getStringClaim("auth");
                Date expirationTime = (Date) (signedJWT.getJWTClaimsSet().getClaim("exp"));

                if(new Date().after(expirationTime)) {
                    return httpMessageContext.responseUnauthorized();
                }

//                 from now container knows user login and user groups, so web.xml can verify authority
                return httpMessageContext.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));
            } catch (ParseException e) {
                System.err.println(e.getMessage());
                return httpMessageContext.responseUnauthorized();
            }
        }
        return null;
    }
}
