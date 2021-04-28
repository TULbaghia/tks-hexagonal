package pl.lodz.p.it.tks.rent.applicationports.adapters;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.rent.applicationports.adapters.driven.AdminRepositoryAdapter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.data.user.AdminEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import java.lang.reflect.Field;

public class AdminRepositoryAdapterTests {

    private AdminRepositoryAdapter adminRepositoryAdapter;
    private UserEntRepository userEntRepository;

    @BeforeMethod
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
                .password("zaq1@WSX")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        adminRepositoryAdapter.add(admin);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotSame(userEntRepository.get("Klient"), admin);
    }

    @Test
    public void getAdminByIdTest() throws RepositoryAdapterException, RepositoryEntException {
        AdminEnt admin = AdminEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(admin);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        Admin getAdmin = adminRepositoryAdapter.get(admin.getId());
        Assert.assertNotSame(getAdmin, admin);
        Assert.assertEquals(getAdmin.getId(), admin.getId());
        Assert.assertEquals(getAdmin.isActive(), admin.isActive());
        Assert.assertEquals(getAdmin.getFirstname(), admin.getFirstname());
        Assert.assertEquals(getAdmin.getLastname(), admin.getLastname());
        Assert.assertEquals(getAdmin.getLogin(), admin.getLogin());
        Assert.assertEquals(getAdmin.getPassword(), admin.getPassword());
    }

    @Test
    public void getAdminByLoginTest() throws RepositoryAdapterException, RepositoryEntException {
        AdminEnt admin = AdminEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        Assert.assertEquals(userEntRepository.getAll().size(), 0);
        userEntRepository.add(admin);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        Admin getAdmin = adminRepositoryAdapter.get("Klient");
        Assert.assertNotSame(getAdmin, admin);
        Assert.assertEquals(getAdmin.getId(), admin.getId());
        Assert.assertEquals(getAdmin.isActive(), admin.isActive());
        Assert.assertEquals(getAdmin.getFirstname(), admin.getFirstname());
        Assert.assertEquals(getAdmin.getLastname(), admin.getLastname());
        Assert.assertEquals(getAdmin.getLogin(), admin.getLogin());
        Assert.assertEquals(getAdmin.getPassword(), admin.getPassword());
    }

    @Test
    public void getAllAdmin() throws RepositoryEntException, RepositoryAdapterException {
        int usersCount = 5;
        for (int i = 0; i < usersCount; i++) {
            AdminEnt adminEnt = AdminEnt.builder()
                    .firstname("Customer" + i)
                    .lastname("Kowalski")
                    .login("Klient" + i)
                    .password("zaq1@WSX")
                    .build();
            userEntRepository.add(adminEnt);
        }

        Assert.assertEquals(adminRepositoryAdapter.getAll().size(), usersCount);
    }

    @Test
    public void updateAdmin() throws RepositoryEntException, RepositoryAdapterException {
        AdminEnt adminEnt  = AdminEnt.builder()
                .firstname("Customer")
                .lastname("Kowalski")
                .login("Klient")
                .password("zaq1@WSX")
                .build();

        userEntRepository.add(adminEnt);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);
        Assert.assertNotEquals(adminEnt.getId(), null);

        Admin updatedAdmin = Admin.builder()
                .id(adminEnt.getId())
                .login(adminEnt.getLogin())
                .firstname("Admin")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .build();

        adminRepositoryAdapter.update(updatedAdmin);
        Assert.assertEquals(userEntRepository.getAll().size(), 1);

        AdminEnt getUpdatedAdmin = (AdminEnt) userEntRepository.get(adminEnt.getId());
        Assert.assertEquals(getUpdatedAdmin.getId(), updatedAdmin.getId());
        Assert.assertEquals(getUpdatedAdmin.isActive(), updatedAdmin.isActive());
        Assert.assertEquals(getUpdatedAdmin.getFirstname(), updatedAdmin.getFirstname());
        Assert.assertEquals(getUpdatedAdmin.getLastname(), updatedAdmin.getLastname());
        Assert.assertEquals(getUpdatedAdmin.getLogin(), updatedAdmin.getLogin());
        Assert.assertEquals(getUpdatedAdmin.getPassword(), updatedAdmin.getPassword());

        Admin updatedAdmin2 = Admin.builder()
                .login(adminEnt.getLogin())
                .firstname("Admin")
                .lastname("TestoweNazwisko")
                .password("zaq1@WSX")
                .build();

        Assert.assertThrows(RepositoryAdapterException.class, () -> adminRepositoryAdapter.update(updatedAdmin2));
    }
}
