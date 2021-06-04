package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.CustomerRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class CustomerRepositoryAdapterTests {
    private static CustomerRepositoryAdapter customerRepositoryAdapter;
    private static UserEntRepository userEntRepository;

    @BeforeEach
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

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        customerRepositoryAdapter.add(customer);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotSame(userEntRepository.get("Klient"), customer);
    }

    @Test
    public void getCustomerByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        CustomerEnt customer = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(customer);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        Customer getAdmin = customerRepositoryAdapter.get(customer.getId());
        Assertions.assertNotSame(getAdmin, customer);
        Assertions.assertEquals(getAdmin.getId(), customer.getId());
        Assertions.assertEquals(getAdmin.isActive(), customer.isActive());
        Assertions.assertEquals(getAdmin.getFirstname(), customer.getFirstname());
        Assertions.assertEquals(getAdmin.getLastname(), customer.getLastname());
        Assertions.assertEquals(getAdmin.getLogin(), customer.getLogin());
    }

    @Test
    public void getCustomerByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        CustomerEnt customer = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(customer);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        Customer getAdmin = customerRepositoryAdapter.get("Klient");
        Assertions.assertNotSame(getAdmin, customer);
        Assertions.assertEquals(getAdmin.getId(), customer.getId());
        Assertions.assertEquals(getAdmin.isActive(), customer.isActive());
        Assertions.assertEquals(getAdmin.getFirstname(), customer.getFirstname());
        Assertions.assertEquals(getAdmin.getLastname(), customer.getLastname());
        Assertions.assertEquals(getAdmin.getLogin(), customer.getLogin());
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

        Assertions.assertEquals(customerRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateCustomer() throws RepositoryEntException, RepositoryAdapterException {
        CustomerEnt adminEnt  = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(adminEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(adminEnt.getId(), null);

        Customer updatedCustomer = Customer.builder()
                .id(adminEnt.getId())
                .login(adminEnt.getLogin())
                .firstname("Customer")
                .lastname("TestoweNazwisko")
                .build();

        customerRepositoryAdapter.update(updatedCustomer);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        CustomerEnt getUpdatedAdmin = (CustomerEnt) userEntRepository.get(adminEnt.getId());
        Assertions.assertEquals(getUpdatedAdmin.getId(), updatedCustomer.getId());
        Assertions.assertEquals(getUpdatedAdmin.isActive(), updatedCustomer.isActive());
        Assertions.assertEquals(getUpdatedAdmin.getFirstname(), updatedCustomer.getFirstname());
        Assertions.assertEquals(getUpdatedAdmin.getLastname(), updatedCustomer.getLastname());
        Assertions.assertEquals(getUpdatedAdmin.getLogin(), updatedCustomer.getLogin());

        Customer updatedCustomer2 = Customer.builder()
                .login(adminEnt.getLogin())
                .firstname("Customer")
                .lastname("TestoweNazwisko")
                .build();

        Assertions.assertThrows(RepositoryAdapterException.class, () -> customerRepositoryAdapter.update(updatedCustomer2));
    }
}
