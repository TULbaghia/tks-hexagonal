package pl.lodz.p.it.tks.repository;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import java.util.UUID;

public class UserEntRepositoryTests {

    private UserEntRepository userEntRepository;

    @BeforeMethod
    public void beforeTest() {
        userEntRepository = new UserEntRepository();
    }

    @Test
    public void addUserTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        userEntRepository.add(customerEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.add(customerEnt));
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
    }

    @Test
    public void getUserByIdTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        userEntRepository.add(customerEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(customerEnt.getId(), null);

        CustomerEnt getCustomer = (CustomerEnt) userEntRepository.get(customerEnt.getId());
        Assert.assertEquals(getCustomer.getId(), customerEnt.getId());
        Assert.assertEquals(getCustomer.isActive(), customerEnt.isActive());
        Assert.assertEquals(getCustomer.getFirstname(), customerEnt.getFirstname());
        Assert.assertEquals(getCustomer.getLastname(), customerEnt.getLastname());
        Assert.assertEquals(getCustomer.getLogin(), customerEnt.getLogin());
        Assert.assertEquals(getCustomer.getPassword(), customerEnt.getPassword());
        Assert.assertEquals(getCustomer.getRentsNumber(), customerEnt.getRentsNumber());

        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.get(UUID.randomUUID()));
    }

    @Test
    public void getUserByLoginTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        userEntRepository.add(customerEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(customerEnt.getId(), null);

        CustomerEnt getCustomer = (CustomerEnt) userEntRepository.get(customerEnt.getLogin());
        Assert.assertEquals(getCustomer.getId(), customerEnt.getId());
        Assert.assertEquals(getCustomer.isActive(), customerEnt.isActive());
        Assert.assertEquals(getCustomer.getFirstname(), customerEnt.getFirstname());
        Assert.assertEquals(getCustomer.getLastname(), customerEnt.getLastname());
        Assert.assertEquals(getCustomer.getLogin(), customerEnt.getLogin());
        Assert.assertEquals(getCustomer.getPassword(), customerEnt.getPassword());
        Assert.assertEquals(getCustomer.getRentsNumber(), customerEnt.getRentsNumber());

        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.get("."));
    }

    @Test
    public void getAllUserTest() throws RepositoryEntException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            CustomerEnt customerEnt = CustomerEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .password("zaq1@WSX")
                    .build();
            userEntRepository.add(customerEnt);
        }

        Assert.assertEquals(userEntRepository.getAll().size(), usersCount);
    }

    @Test
    public void updateUserTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        userEntRepository.add(customerEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(customerEnt.getId(), null);

        CustomerEnt updatedCustomer = CustomerEnt.builder()
                .id(customerEnt.getId())
                .login(customerEnt.getLogin())
                .firstname("Customer")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .build();

        userEntRepository.update(updatedCustomer);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        CustomerEnt getUpdatedCustomer = (CustomerEnt) userEntRepository.get(customerEnt.getId());
        Assert.assertEquals(getUpdatedCustomer.getId(), updatedCustomer.getId());
        Assert.assertEquals(getUpdatedCustomer.isActive(), updatedCustomer.isActive());
        Assert.assertEquals(getUpdatedCustomer.getFirstname(), updatedCustomer.getFirstname());
        Assert.assertEquals(getUpdatedCustomer.getLastname(), updatedCustomer.getLastname());
        Assert.assertEquals(getUpdatedCustomer.getLogin(), updatedCustomer.getLogin());
        Assert.assertEquals(getUpdatedCustomer.getPassword(), updatedCustomer.getPassword());
        Assert.assertEquals(getUpdatedCustomer.getRentsNumber(), updatedCustomer.getRentsNumber());


        Assert.assertThrows(RepositoryEntException.class, () -> userEntRepository.update(new CustomerEnt()));
    }
}

