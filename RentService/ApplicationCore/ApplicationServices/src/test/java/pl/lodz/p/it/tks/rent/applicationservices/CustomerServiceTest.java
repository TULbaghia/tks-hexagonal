package pl.lodz.p.it.tks.rent.applicationservices;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.CustomerRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;

import java.util.List;

public class CustomerServiceTest {

    private static CustomerService customerService;
    private static CustomerRepositoryAdapter customerRepositoryAdapter;

    @BeforeEach
    public void beforeMethod() {
        customerRepositoryAdapter = new CustomerRepositoryAdapter(new UserEntRepository());
        customerService = new CustomerService(customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter, customerRepositoryAdapter);
    }

    @Test
    public void addCustomerTest() throws RepositoryAdapterException {
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer").build());
        Assertions.assertThrows(RepositoryAdapterException.class, () ->
                customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer").build()));

        Assertions.assertEquals(customerRepositoryAdapter.get("customer").getLogin(), "customer");
        Assertions.assertEquals(customerRepositoryAdapter.get("customer").getLastname(), "Customer");
        Assertions.assertEquals(customerRepositoryAdapter.get("customer").getFirstname(), "Jan");
    }

    @Test
    public void updateCustomerTest() throws RepositoryAdapterException {
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer").build());

        Customer old = customerService.get("customer");
        old.setFirstname("T1");
        old.setLastname("T2");

        Customer update = customerService.update(old);

        Assertions.assertEquals(update.getFirstname(), "T1");
        Assertions.assertEquals(update.getLastname(), "T2");

        Assertions.assertEquals(customerRepositoryAdapter.get("customer").getFirstname(), "T1");
        Assertions.assertEquals(customerRepositoryAdapter.get("customer").getLastname(), "T2");
    }

    @Test
    public void getAllCustomerTest() throws RepositoryAdapterException {
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer1").build());
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer2").build());
        customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer3").build());

        List<Customer> customerList = customerService.getAll();

        Assertions.assertEquals(customerList.size(), 3);
        Assertions.assertEquals(customerList.get(0).getLogin(), "customer1");
        Assertions.assertEquals(customerList.get(1).getLogin(), "customer2");
        Assertions.assertEquals(customerList.get(2).getLogin(), "customer3");

    }

    @Test
    public void getByUUIDCustomerTest() throws RepositoryAdapterException {
        Customer a = customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer1").build());

        Customer get = customerService.get(a.getId());

        Assertions.assertEquals(get, a);
    }

    @Test
    public void getByLoginCustomerTest() throws RepositoryAdapterException {
        Customer a = customerService.add(Customer.builder().firstname("Jan").lastname("Customer").login("customer1").build());

        Customer get = customerService.get(a.getLogin());

        Assertions.assertEquals(get, a);
    }
}
