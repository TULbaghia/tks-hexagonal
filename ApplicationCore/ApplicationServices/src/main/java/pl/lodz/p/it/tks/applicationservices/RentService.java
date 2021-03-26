package pl.lodz.p.it.tks.applicationservices;

import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.infrastructure.rent.*;
import pl.lodz.p.it.tks.applicationports.infrastructure.user.customer.UpdateCustomerPort;
import pl.lodz.p.it.tks.applicationports.ui.RentUseCase;
import pl.lodz.p.it.tks.domainmodel.exception.CustomerException;
import pl.lodz.p.it.tks.domainmodel.exception.RentException;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.domainmodel.user.customer.BasicCustomer;
import pl.lodz.p.it.tks.domainmodel.user.customer.SpecialCustomer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class RentService implements RentUseCase {

    private final AddRentPort addRentPort;
    private final GetRentPort getRentPort;
    private final GetAllRentPort getAllRentPort;
    private final UpdateRentPort updateRentPort;
    private final DeleteRentPort deleteRentPort;
    private final UpdateCustomerPort updateCustomerPort;

    @Inject
    public RentService(AddRentPort addRentPort, GetRentPort getRentPort, GetAllRentPort getAllRentPort, UpdateRentPort updateRentPort, DeleteRentPort deleteRentPort, UpdateCustomerPort updateCustomerPort) {
        this.addRentPort = addRentPort;
        this.getRentPort = getRentPort;
        this.getAllRentPort = getAllRentPort;
        this.updateRentPort = updateRentPort;
        this.deleteRentPort = deleteRentPort;
        this.updateCustomerPort = updateCustomerPort;
    }

    @Override
    public Rent add(Rent rent) throws RepositoryAdapterException {
        customerRentLimitCheck(rent);
        if (filterRents(x -> x.getCar().getId().equals(rent.getCar().getId()) && x.getRentEndDate() == null).size() > 0) {
            throw new RepositoryAdapterException("Car is currently rented.");
        }
        if (!rent.getCustomer().isActive()) {
            throw new RepositoryAdapterException("Non active customer cannot rent a car.");
        }
        return addRentPort.add(rent);
    }

    @Override
    public Rent update(Rent rent) throws RepositoryAdapterException {
        Rent currentItem = get(rent.getId());
        if (currentItem.getRentEndDate() != null) {
            throw new RepositoryAdapterException("Cannot edit finished reservation.");
        }
        customerRentLimitCheck(currentItem);
        return updateRentPort.update(rent);
    }

    @Override
    public List<Rent> getAll() {
        return getAllRentPort.getAll();
    }

    @Override
    public Rent get(UUID uuid) throws RepositoryAdapterException {
        return getRentPort.get(uuid);
    }

    @Override
    public void delete(UUID id) throws RepositoryAdapterException {
        Rent rent = get(id);
        if (rent.getRentEndDate() != null) {
            throw new RepositoryAdapterException("Cannot delete finished reservation.");
        }
        deleteRentPort.delete(id);
    }

    @Override
    public synchronized Rent endRent(UUID id) throws RepositoryAdapterException {
        Rent rent = get(id);
        try {
            rent.endRent();
            Customer c = rent.getCustomer();

            double rentsOverallPrice = filterRents(x ->
                    x.getCustomer().getId().equals(c.getId()) && x.getRentEndDate() == null).stream()
                    .mapToDouble(Rent::getPrice).sum();

            if (rentsOverallPrice > 5000d && c.getCustomerType() instanceof BasicCustomer) {
                c.changeCustomerType(new SpecialCustomer());
                updateCustomerPort.update(c);
            }

            return update(rent);
        } catch (RentException | CustomerException e) {
            throw new RepositoryAdapterException(e);
        }
    }

    private List<Rent> filterRents(Predicate<Rent> predicate) {
        return this.getAll().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private void customerRentLimitCheck(Rent item) throws RepositoryAdapterException {
        int customerCarLimit = item.getCustomer().getCustomerType().getMaxVehiclesRentedNumber();
        int currentlyRentedNumber = filterRents(rent ->
                rent.getCustomer().getId().equals(item.getCustomer().getId()) &&
                        rent.getRentEndDate() == null).size();

        if (currentlyRentedNumber > customerCarLimit) {
            throw new RepositoryAdapterException("Customer max cars limit reached.");
        }
    }
}
