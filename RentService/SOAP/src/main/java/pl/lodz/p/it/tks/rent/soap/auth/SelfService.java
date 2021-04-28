package pl.lodz.p.it.tks.rent.soap.auth;

import pl.lodz.p.it.tks.rent.domainmodel.user.User;
import pl.lodz.p.it.tks.rent.soap.dtosoap.UserSoap;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(serviceName = "SelfService")
public class SelfService {
    @Inject
    private SoapIdentityStore soapIdentityStore;

    @Resource
    private WebServiceContext sessionContext;

    @WebMethod
    public String getSelf() {
        User user = soapIdentityStore.getUserByLogin(sessionContext.getUserPrincipal().getName());
        return UserSoap.toSoap(user) + "\nGroup: " + user.getClass().getSimpleName();
    }
}
