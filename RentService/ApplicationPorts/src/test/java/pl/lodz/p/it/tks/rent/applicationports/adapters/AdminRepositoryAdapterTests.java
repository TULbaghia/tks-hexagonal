package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.AdminRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.user.AdminEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class AdminRepositoryAdapterTests {

    private static AdminRepositoryAdapter adminRepositoryAdapter;
    private static UserEntRepository userEntRepository;

    @BeforeEach
    public void beforeTest() throws IllegalAccessException, NoSuchFieldException {
        adminRepositoryAdapter = new AdminRepositoryAdapter(new UserEntRepository());
        userEntRepository = new UserEntRepository();
        Field field = adminRepositoryAdapter.getClass().getDeclaredField("userEntRepository");
        field.setAccessible(true);
        field.set(adminRepositoryAdapter, userEntRepository);
    }

    @Test
    public void addAdminTest() throws RepositoryAdapterException, RepositoryEntException {
        Admin admin = Admin.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        adminRepositoryAdapter.add(admin);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotSame(userEntRepository.get("Klient"), admin);
    }

    @Test
    public void getAdminByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        AdminEnt admin = AdminEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(admin);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        Admin getAdmin = adminRepositoryAdapter.get(admin.getId());
        Assertions.assertNotSame(getAdmin, admin);
        Assertions.assertEquals(getAdmin.getId(), admin.getId());
        Assertions.assertEquals(getAdmin.isActive(), admin.isActive());
        Assertions.assertEquals(getAdmin.getFirstname(), admin.getFirstname());
        Assertions.assertEquals(getAdmin.getLastname(), admin.getLastname());
        Assertions.assertEquals(getAdmin.getLogin(), admin.getLogin());
    }

    @Test
    public void getAdminByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        AdminEnt admin = AdminEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        Assertions.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(admin);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        Admin getAdmin = adminRepositoryAdapter.get("Klient");
        Assertions.assertNotSame(getAdmin, admin);
        Assertions.assertEquals(getAdmin.getId(), admin.getId());
        Assertions.assertEquals(getAdmin.isActive(), admin.isActive());
        Assertions.assertEquals(getAdmin.getFirstname(), admin.getFirstname());
        Assertions.assertEquals(getAdmin.getLastname(), admin.getLastname());
        Assertions.assertEquals(getAdmin.getLogin(), admin.getLogin());
    }

    @Test
    public void getAllAdmin() throws RepositoryEntException, RepositoryAdapterException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            AdminEnt adminEnt = AdminEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .build();
            userEntRepository.add(adminEnt);
        }

        Assertions.assertEquals(adminRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateAdmin() throws RepositoryEntException, RepositoryAdapterException {
        AdminEnt adminEnt  = AdminEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .build();

        userEntRepository.add(adminEnt);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);
        Assertions.assertNotEquals(adminEnt.getId(), null);

        Admin updatedAdmin = Admin.builder()
                .id(adminEnt.getId())
                .login(adminEnt.getLogin())
                .firstname("Admin")
                .lastname("TestoweNazwisko")
                .build();

        adminRepositoryAdapter.update(updatedAdmin);
        Assertions.assertEquals(userEntRepository.getAll().size(), 1);

        AdminEnt getUpdatedAdmin = (AdminEnt) userEntRepository.get(adminEnt.getId());
        Assertions.assertEquals(getUpdatedAdmin.getId(), updatedAdmin.getId());
        Assertions.assertEquals(getUpdatedAdmin.isActive(), updatedAdmin.isActive());
        Assertions.assertEquals(getUpdatedAdmin.getFirstname(), updatedAdmin.getFirstname());
        Assertions.assertEquals(getUpdatedAdmin.getLastname(), updatedAdmin.getLastname());
        Assertions.assertEquals(getUpdatedAdmin.getLogin(), updatedAdmin.getLogin());

        Admin updatedAdmin2 = Admin.builder()
                .login(adminEnt.getLogin())
                .firstname("Admin")
                .lastname("TestoweNazwisko")
                .build();

        Assertions.assertThrows(RepositoryAdapterException.class, () -> adminRepositoryAdapter.update(updatedAdmin2));
    }
}
