package pl.lodz.p.it.tks.applicationservices;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.lodz.p.it.tks.applicationports.adapters.driven.AdminRepositoryAdapter;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.repository.UserEntRepository;

import java.util.List;

public class AdminServiceTest {

    private AdminService adminService;
    private AdminRepositoryAdapter adminRepositoryAdapter;

    @BeforeMethod
    public void beforeMethod() {
        adminRepositoryAdapter = new AdminRepositoryAdapter(new UserEntRepository());
        adminService = new AdminService(adminRepositoryAdapter, adminRepositoryAdapter, adminRepositoryAdapter, adminRepositoryAdapter, adminRepositoryAdapter);
    }

    @Test
    public void addAdminTest() throws RepositoryAdapterException {
        adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin").build());
        Assert.assertThrows(RepositoryAdapterException.class, () ->
                adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin").build()));

        Assert.assertEquals(adminRepositoryAdapter.get("admin").getLogin(), "admin");
        Assert.assertEquals(adminRepositoryAdapter.get("admin").getLastname(), "Admin");
        Assert.assertEquals(adminRepositoryAdapter.get("admin").getFirstname(), "Jan");
        Assert.assertEquals(adminRepositoryAdapter.get("admin").getPassword(), "zaq1@WSX");
    }

    @Test
    public void updateAdminTest() throws RepositoryAdapterException {
        adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin").build());

        Admin old = adminService.get("admin");
        old.setFirstname("T1");
        old.setLastname("T2");
        old.setPassword("ZAQ!2wsx");

        Admin update = adminService.update(old);

        Assert.assertEquals(update.getFirstname(), "T1");
        Assert.assertEquals(update.getLastname(), "T2");
        Assert.assertEquals(update.getPassword(), "ZAQ!2wsx");

        Assert.assertEquals(adminRepositoryAdapter.get("admin").getFirstname(), "T1");
        Assert.assertEquals(adminRepositoryAdapter.get("admin").getLastname(), "T2");
        Assert.assertEquals(adminRepositoryAdapter.get("admin").getPassword(), "ZAQ!2wsx");
    }

    @Test
    public void getAllAdminTest() throws RepositoryAdapterException {
        adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin1").build());
        adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin2").build());
        adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin3").build());

        List<Admin> adminList = adminService.getAll();

        Assert.assertEquals(adminList.size(), 3);
        Assert.assertEquals(adminList.get(0).getLogin(), "admin1");
        Assert.assertEquals(adminList.get(1).getLogin(), "admin2");
        Assert.assertEquals(adminList.get(2).getLogin(), "admin3");

    }

    @Test
    public void getByUUIDAdminTest() throws RepositoryAdapterException {
        Admin a = adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin1").build());

        Admin get = adminService.get(a.getId());

        Assert.assertEquals(get, a);
    }

    @Test
    public void getByLoginAdminTest() throws RepositoryAdapterException {
        Admin a = adminService.add(Admin.builder().firstname("Jan").lastname("Admin").password("zaq1@WSX").login("admin1").build());

        Admin get = adminService.get(a.getLogin());

        Assert.assertEquals(get, a);
    }
}
