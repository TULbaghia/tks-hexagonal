package pl.lodz.p.it.tks.rent.soap.resources;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.soap.Util;
import pl.soap.target.RentService;
import pl.soap.target.*;

import javax.xml.ws.BindingProvider;
import java.util.List;

public class RentServiceTests {
    private final String RENTAPI_WSDL_URI = "http://localhost:8080/RentSoap/RentAPI?wsdl";

    private RentService rentService;

    @BeforeMethod
    public void beforeEach() {
        RentAPI rentAPI = new RentAPI(RentAPI.class.getResource("RentAPI.wsdl"));
        rentService = rentAPI.getRentServicePort();
        Util.authenticateUser((BindingProvider) rentService, "TestEmployee", "zaq1@WSX");
        ((BindingProvider) rentService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RENTAPI_WSDL_URI);
    }

    @Test
    public void getRentTest() throws RepositoryAdapterException_Exception {
        List<RentSoap> allRents = rentService.getAllRents();
        RentSoap rs = allRents.get(0);

        RentSoap rentSoap = rentService.getRent(rs.getId());

        Assert.assertEquals(rentSoap.getId(), rs.getId());
        Assert.assertEquals(rentSoap.getCarId(), rs.getCarId());
        Assert.assertEquals(rentSoap.getCustomerId(), rs.getCustomerId());
        Assert.assertEquals(rentSoap.getRentStartDate(), rs.getRentStartDate());
        Assert.assertEquals(rentSoap.getRentEndDate(), rs.getRentEndDate());
        Assert.assertEquals(rentSoap.getPrice(), rs.getPrice());
    }

    @Test
    public void getAllRentsTest() {
        List<RentSoap> allRents = rentService.getAllRents();

        Assert.assertNotEquals(allRents.size(), 0);
    }
}
