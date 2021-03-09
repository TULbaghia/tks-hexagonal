package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.user.*;
import pl.lodz.p.it.tks.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.applicationservices.exception.CoreServiceException;
import pl.lodz.p.it.tks.domainmodel.user.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService implements CustomerUseCase {
    @Inject
    private AddCustomerPort addCustomerPort;
    @Inject
    private UpdateCustomerPort updateCustomerPort;
    @Inject
    private GetAllCustomerPort getAllCustomerPort;
    @Inject
    private GetCustomerByIdPort getCustomerByIdPort;
    @Inject
    private GetCustomerByLoginPort getCustomerByLoginPort;

    @Override
    public Customer add(Customer customer) throws RepositoryAdapterException {
        checkPassword(customer.getPassword());
        return addCustomerPort.add(customer);
    }

    @Override
    public Customer update(Customer customer) throws RepositoryAdapterException {
        checkPassword(customer.getPassword());
        return updateCustomerPort.update(customer);
    }

    @Override
    public List<Customer> getAll() {
        return getAllCustomerPort.getAll();
    }

    @Override
    public Customer get(UUID uuid) throws RepositoryAdapterException {
        return getCustomerByIdPort.get(uuid);
    }

    @Override
    public Customer get(String login) throws RepositoryAdapterException {
        return getCustomerByLoginPort.get(login);
    }

    private void checkPassword(String password) throws CoreServiceException {
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new CoreServiceException("Password is not secure.");
        }
    }
}
