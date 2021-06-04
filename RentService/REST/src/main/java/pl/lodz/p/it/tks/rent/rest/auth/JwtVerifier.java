package pl.lodz.p.it.tks.rent.rest.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.ParseException;

@ApplicationScoped
public class JwtVerifier {

    private static final String SECRET;

    static {
        try {
            SECRET = ((Context) new InitialContext().lookup("java:comp/env")).lookup("SECRET").toString();
        } catch (NamingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean validateJwtSignature(String result) {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(result);
            JWSVerifier verifier = new MACVerifier(SECRET);
            return signedJWT.verify(verifier);
        } catch (JOSEException | ParseException e) {
            return false;
        }
    }
}
