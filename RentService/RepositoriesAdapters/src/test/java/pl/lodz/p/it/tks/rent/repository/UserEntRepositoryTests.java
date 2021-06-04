package pl.lodz.p.it.tks.rent.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.util.UUID;

public class UserEntRepositoryTests {

    private static UserEntRepository userEntRepository;

    @BeforeEach
    public void beforeTest() {
        userEntRepository = new UserEntRepository();
    }

    @Test
    public void addUserTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(customerEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.add(customerEnt));
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
    }

    @Test
    public void getUserByIdTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(customerEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(customerEnt.getId(), null);

        CustomerEnt getCustomer = (CustomerEnt) userEntRepository.get(customerEnt.getId());
        Assertions.assertEquals(getCustomer.getId(), customerEnt.getId());
        Assertions.assertEquals(getCustomer.isActive(), customerEnt.isActive());
        Assertions.assertEquals(getCustomer.getFirstname(), customerEnt.getFirstname());
        Assertions.assertEquals(getCustomer.getLastname(), customerEnt.getLastname());
        Assertions.assertEquals(getCustomer.getLogin(), customerEnt.getLogin());
        Assertions.assertEquals(getCustomer.getRentsNumber(), customerEnt.getRentsNumber());

        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.get(UUID.randomUUID()));
    }

    @Test
    public void getUserByLoginTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(customerEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(customerEnt.getId(), null);

        CustomerEnt getCustomer = (CustomerEnt) userEntRepository.get(customerEnt.getLogin());
        Assertions.assertEquals(getCustomer.getId(), customerEnt.getId());
        Assertions.assertEquals(getCustomer.isActive(), customerEnt.isActive());
        Assertions.assertEquals(getCustomer.getFirstname(), customerEnt.getFirstname());
        Assertions.assertEquals(getCustomer.getLastname(), customerEnt.getLastname());
        Assertions.assertEquals(getCustomer.getLogin(), customerEnt.getLogin());
        Assertions.assertEquals(getCustomer.getRentsNumber(), customerEnt.getRentsNumber());

        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.get("."));
    }

    @Test
    public void getAllUserTest() throws RepositoryEntException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            CustomerEnt customerEnt = CustomerEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .build();
            userEntRepository.add(customerEnt);
        }

        Assertions.assertEquals(userEntRepository.getAll().size(), usersCount);
    }

    @Test
    public void updateUserTest() throws RepositoryEntException {
        CustomerEnt customerEnt = CustomerEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(customerEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(customerEnt.getId(), null);

        CustomerEnt updatedCustomer = CustomerEnt.builder()
                .id(customerEnt.getId())
                .login(customerEnt.getLogin())
                .firstname("Customer")
                .lastname("TestoweNazwisko")
                .build();

        userEntRepository.update(updatedCustomer);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        CustomerEnt getUpdatedCustomer = (CustomerEnt) userEntRepository.get(customerEnt.getId());
        Assertions.assertEquals(getUpdatedCustomer.getId(), updatedCustomer.getId());
        Assertions.assertEquals(getUpdatedCustomer.isActive(), updatedCustomer.isActive());
        Assertions.assertEquals(getUpdatedCustomer.getFirstname(), updatedCustomer.getFirstname());
        Assertions.assertEquals(getUpdatedCustomer.getLastname(), updatedCustomer.getLastname());
        Assertions.assertEquals(getUpdatedCustomer.getLogin(), updatedCustomer.getLogin());
        Assertions.assertEquals(getUpdatedCustomer.getRentsNumber(), updatedCustomer.getRentsNumber());


        Assertions.assertThrows(RepositoryEntException.class, () -> userEntRepository.update(new CustomerEnt()));
    }
}

