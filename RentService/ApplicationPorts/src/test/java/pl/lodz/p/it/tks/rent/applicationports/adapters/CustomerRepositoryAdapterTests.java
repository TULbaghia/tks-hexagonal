package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.CustomerRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class CustomerRepositoryAdapterTests {
    private CustomerRepositoryAdapter customerRepositoryAdapter;
    private UserEntRepository userEntRepository;

    @BeforeMethod
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        customerRepositoryAdapter = new CustomerRepositoryAdapter(new UserEntRepository());
        userEntRepository = new UserEntRepository();
        Field field = customerRepositoryAdapter.getClass().getDeclaredField("userEntRepository");
        field.setAccessible(true);
        field.set(customerRepositoryAdapter, userEntRepository);
    }

    @Test
    public void addCustomerTest() throws RepositoryAdapterException, RepositoryEntException {
        Customer customer = Customer.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        customerRepositoryAdapter.add(customer);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotSame(userEntRepository.get("Klient"), customer);
    }

    @Test
    public void getCustomerByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        CustomerEnt customer = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(customer);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        Customer getAdmin = customerRepositoryAdapter.get(customer.getId());
        Assert.assertNotSame(getAdmin, customer);
        Assert.assertEquals(getAdmin.getId(), customer.getId());
        Assert.assertEquals(getAdmin.isActive(), customer.isActive());
        Assert.assertEquals(getAdmin.getFirstname(), customer.getFirstname());
        Assert.assertEquals(getAdmin.getLastname(), customer.getLastname());
        Assert.assertEquals(getAdmin.getLogin(), customer.getLogin());
    }

    @Test
    public void getCustomerByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        CustomerEnt customer = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(customer);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        Customer getAdmin = customerRepositoryAdapter.get("Klient");
        Assert.assertNotSame(getAdmin, customer);
        Assert.assertEquals(getAdmin.getId(), customer.getId());
        Assert.assertEquals(getAdmin.isActive(), customer.isActive());
        Assert.assertEquals(getAdmin.getFirstname(), customer.getFirstname());
        Assert.assertEquals(getAdmin.getLastname(), customer.getLastname());
        Assert.assertEquals(getAdmin.getLogin(), customer.getLogin());
    }

    @Test
    public void getAllCustomer() throws RepositoryEntException, RepositoryAdapterException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            CustomerEnt adminEnt = CustomerEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .build();
            userEntRepository.add(adminEnt);
        }

        Assert.assertEquals(customerRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateCustomer() throws RepositoryEntException, RepositoryAdapterException {
        CustomerEnt adminEnt  = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(adminEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(adminEnt.getId(), null);

        Customer updatedCustomer = Customer.builder()
                .id(adminEnt.getId())
                .login(adminEnt.getLogin())
                .firstname("Customer")
                .lastname("TestoweNazwisko")
                .build();

        customerRepositoryAdapter.update(updatedCustomer);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        CustomerEnt getUpdatedAdmin = (CustomerEnt) userEntRepository.get(adminEnt.getId());
        Assert.assertEquals(getUpdatedAdmin.getId(), updatedCustomer.getId());
        Assert.assertEquals(getUpdatedAdmin.isActive(), updatedCustomer.isActive());
        Assert.assertEquals(getUpdatedAdmin.getFirstname(), updatedCustomer.getFirstname());
        Assert.assertEquals(getUpdatedAdmin.getLastname(), updatedCustomer.getLastname());
        Assert.assertEquals(getUpdatedAdmin.getLogin(), updatedCustomer.getLogin());

        Customer updatedCustomer2 = Customer.builder()
                .login(adminEnt.getLogin())
                .firstname("Customer")
                .lastname("TestoweNazwisko")
                .build();

        Assert.assertThrows(RepositoryAdapterException.class, () -> customerRepositoryAdapter.update(updatedCustomer2));
    }
}