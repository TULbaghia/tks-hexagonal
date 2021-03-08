package pl.lodz.p.it.tks.applicationservices;

import lombok.NonNull;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.user.*;
import pl.lodz.p.it.tks.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.applicationservices.exception.CoreServiceException;
import pl.lodz.p.it.tks.domainmodel.user.Admin;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.Employee;
import pl.lodz.p.it.tks.domainmodel.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService implements UserUseCase {
    @Inject
    private AddAdminPort addAdminPort;
    @Inject
    private AddEmployeePort addEmployeePort;
    @Inject
    private AddCustomerPort addCustomerPort;
    @Inject
    private GetAllUserPort getAllUserPort;
    @Inject
    private GetUserByIdPort getUserByIdPort;
    @Inject
    private GetUserByLoginPort getUserByLoginPort;
    @Inject
    private UpdateAdminPort updateAdminPort;
    @Inject
    private UpdateEmployeePort updateEmployeePort;
    @Inject
    private UpdateCustomerPort updateCustomerPort;

    @Override
    public void add(Admin admin) throws RepositoryAdapterException {
        checkPassword(admin.getPassword());
        addAdminPort.add(admin);
    }

    @Override
    public void add(Employee employee) throws RepositoryAdapterException {
        checkPassword(employee.getPassword());
        addEmployeePort.add(employee);
    }

    @Override
    public void add(Customer customer) throws RepositoryAdapterException {
        checkPassword(customer.getPassword());
        addCustomerPort.add(customer);
    }

    @Override
    public void update(Admin admin) throws RepositoryAdapterException {
        checkPassword(admin.getPassword());
        updateAdminPort.update(admin);
    }

    @Override
    public void update(Employee employee) throws RepositoryAdapterException {
        checkPassword(employee.getPassword());
        updateEmployeePort.update(employee);

    }

    @Override
    public void update(Customer customer) throws RepositoryAdapterException {
        checkPassword(customer.getPassword());
        updateCustomerPort.update(customer);
    }

    @Override
    public List<User> getAll() {
        return getAllUserPort.getAll();
    }

    @Override
    public User get(UUID uuid) {
        return getUserByIdPort.get(uuid);
    }

    @Override
    public User get(String login) {
        return getUserByLoginPort.get(login);
    }

    private void checkPassword(String password) throws CoreServiceException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new CoreServiceException("Password is not secure.");
        }
    }
}
