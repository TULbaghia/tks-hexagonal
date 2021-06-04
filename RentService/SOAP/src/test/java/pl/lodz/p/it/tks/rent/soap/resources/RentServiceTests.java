package pl.lodz.p.it.tks.rent.soap.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.soap.AbstractContainer;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.RentService;
import pl.soap.target.*;

import javax.xml.ws.BindingProvider;
import java.util.List;

public class RentServiceTests extends AbstractContainer {
    private static String RENTAPI_WSDL_URI = "http://localhost:%d/RentServiceApp-1.0-SNAPSHOT/RentAPI?wsdl";

    private static RentService rentService;

    @BeforeAll
    public static void setup() {
        getService();
        RENTAPI_WSDL_URI = String.format(RENTAPI_WSDL_URI, serviceOne.getMappedPort(8080));
    }

    @BeforeEach
    public void beforeEach() {
        RentAPI rentAPI = new RentAPI(RentAPI.class.getResource("RentAPI.wsdl"));
        rentService = rentAPI.getRentServicePort();
        Util.authenticateUser((BindingProvider) rentService, "TestEmployee", "zaq1@WSX",
                serviceOne.getMappedPort(8080));
        ((BindingProvider) rentService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RENTAPI_WSDL_URI);
    }

    @Test
    public void getRentTest() throws RepositoryAdapterException_Exception {
        List<RentSoap> allRents = rentService.getAllRents();
        RentSoap rs = allRents.get(0);

        RentSoap rentSoap = rentService.getRent(rs.getId());

        Assertions.assertEquals(rentSoap.getId(), rs.getId());
        Assertions.assertEquals(rentSoap.getCarId(), rs.getCarId());
        Assertions.assertEquals(rentSoap.getCustomerId(), rs.getCustomerId());
        Assertions.assertEquals(rentSoap.getRentStartDate(), rs.getRentStartDate());
        Assertions.assertEquals(rentSoap.getRentEndDate(), rs.getRentEndDate());
        Assertions.assertEquals(rentSoap.getPrice(), rs.getPrice());
    }

    @Test
    public void getAllRentsTest() {
        List<RentSoap> allRents = rentService.getAllRents();

        Assertions.assertNotEquals(allRents.size(), 0);
    }
}
