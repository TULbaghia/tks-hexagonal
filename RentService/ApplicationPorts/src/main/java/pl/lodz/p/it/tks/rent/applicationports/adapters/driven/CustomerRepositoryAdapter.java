package pl.lodz.p.it.tks.rent.applicationports.adapters.driven;

import pl.lodz.p.it.tks.rent.applicationports.converters.UserConverter;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer.*;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer.*;
import pl.lodz.p.it.tks.rent.applicationports.infrastructure.user.customer.*;
import pl.lodz.p.it.tks.rent.repository.UserEntRepository;
import pl.lodz.p.it.tks.rent.repository.exception.RepositoryEntException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerRepositoryAdapter implements AddCustomerPort, UpdateCustomerPort, GetAllCustomerPort,
        GetCustomerByLoginPort, GetCustomerByIdPort, DeleteCustomerPort {

    private final UserEntRepository userEntRepository;

    @Inject
    public CustomerRepositoryAdapter(UserEntRepository userEntRepository) {
        this.userEntRepository = userEntRepository;
    }

    @Override
    public Customer add(Customer customer) throws RepositoryAdapterException {
        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);
        try {
            return UserConverter.convertEntToDomain((CustomerEnt) userEntRepository.add(customerEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Customer get(UUID uuid) throws RepositoryAdapterException {
        try {
            return UserConverter.convertEntToDomain((CustomerEnt) userEntRepository.get(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public Customer get(String login) throws RepositoryAdapterException {
        try {
            return UserConverter.convertEntToDomain((CustomerEnt) userEntRepository.get(login));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        return userEntRepository.getAll().stream()
                .filter(x -> x instanceof CustomerEnt)
                .map(x -> UserConverter.convertEntToDomain((CustomerEnt) x))
                .collect(Collectors.toList());
    }

    @Override
    public Customer update(Customer customer) throws RepositoryAdapterException {
        CustomerEnt customerEnt = UserConverter.convertDomainToEnt(customer);
        try {
            return UserConverter.convertEntToDomain((CustomerEnt) userEntRepository.update(customerEnt));
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    @Override
    public void delete(UUID uuid) throws RepositoryAdapterException {
        try {
            userEntRepository.delete(uuid);
        } catch (RepositoryEntException e) {
            throw new RepositoryAdapterException(e);
        }
    }
}
