package pl.lodz.p.it.tks.rent.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.CustomerRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;

import java.util.List;

public class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepositoryAdapter customerRepositoryAdapter;

    @BeforeMethod
    public void beforeMethod() {
        customerRepositoryAdapter = new CustomerRepositoryAdapter(new UserEntRepository());
        customerService = new CustomerService(customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter);
    }

    @Test
    public void addCustomerTest() throws RepositoryAdapterException {
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer").build());
        Assert.assertThrows(RepositoryAdapterException.class, () ->
                customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer").build()));

        Assert.assertEquals(customerRepositoryAdapter.get("customer").getLogin(), "customer");
        Assert.assertEquals(customerRepositoryAdapter.get("customer").getLastname(), "Customer");
        Assert.assertEquals(customerRepositoryAdapter.get("customer").getFirstname(), "Jan");
    }

    @Test
    public void updateCustomerTest() throws RepositoryAdapterException {
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer").build());

        Customer old = customerService.get("customer");
        old.setFirstname("T1");
        old.setLastname("T2");

        Customer update = customerService.update(old);

        Assert.assertEquals(update.getFirstname(), "T1");
        Assert.assertEquals(update.getLastname(), "T2");

        Assert.assertEquals(customerRepositoryAdapter.get("customer").getFirstname(), "T1");
        Assert.assertEquals(customerRepositoryAdapter.get("customer").getLastname(), "T2");
    }

    @Test
    public void getAllCustomerTest() throws RepositoryAdapterException {
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer1").build());
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer2").build());
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer3").build());

        List<Customer> customerList = customerService.getAll();

        Assert.assertEquals(customerList.size(), 3);
        Assert.assertEquals(customerList.get(0).getLogin(), "customer1");
        Assert.assertEquals(customerList.get(1).getLogin(), "customer2");
        Assert.assertEquals(customerList.get(2).getLogin(), "customer3");

    }

    @Test
    public void getByUUIDCustomerTest() throws RepositoryAdapterException {
        Customer a = customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer1").build());

        Customer get = customerService.get(a.getId());

        Assert.assertEquals(get, a);
    }

    @Test
    public void getByLoginCustomerTest() throws RepositoryAdapterException {
        Customer a = customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer1").build());

        Customer get = customerService.get(a.getLogin());

        Assert.assertEquals(get, a);
    }
}
