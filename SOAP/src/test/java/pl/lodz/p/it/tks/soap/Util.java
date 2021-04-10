package pl.lodz.p.it.tks.soap;

import pl.soap.target.Credentials;
import pl.soap.target.LoginAPI;
import pl.soap.target.LoginService;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    private static final String LOGINAPI_URI = "http://localhost:8080/SOAP-1.0-SNAPSHOT/LoginAPI?wsdl";

    public static void authenticateUser(BindingProvider bindingProvider, String user, String password) {
        Map<String, List<String>> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", Collections.singletonList("Bearer " + authenticate(user, password)));
        bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
    }


    private static String authenticate(String user, String password) {
        LoginAPI loginAPI = new LoginAPI(LoginAPI.class.getResource("LoginAPI.wsdl"));
        LoginService loginService = loginAPI.getLoginServicePort();

        Map<String, Object> rc = ((BindingProvider) loginService).getRequestContext();
        rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, LOGINAPI_URI);

        Credentials credentials = new Credentials();
        credentials.setLogin(user);
        credentials.setPassword(password);

        return loginService.authenticate(credentials);
    }
}
