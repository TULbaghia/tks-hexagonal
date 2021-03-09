package pl.lodz.p.it.tks.repository;

import pl.lodz.p.it.tks.data.exception.CustomerEntException;
import pl.lodz.p.it.tks.data.exception.RentEntException;
import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.data.user.customer.BasicCustomerEnt;
import pl.lodz.p.it.tks.data.user.customer.SpecialCustomerEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class RentEntRepository extends EntRepository<RentEnt> {

    @Override
    public synchronized RentEnt add(RentEnt item) throws RepositoryEntException {
        customerRentLimitCheck(item);
        if (filterRents(rent -> rent.getCar().getId().equals(item.getCar().getId()) && rent.getRentEndDate() == null).size() > 0) {
            throw new RepositoryEntException("Car is currently rented.");
        }
        if (!item.getCustomer().isActive()) {
            throw new RepositoryEntException("Non active customer cannot rent a car.");
        }
        return super.add(item);
    }

    private void customerRentLimitCheck(RentEnt item) throws RepositoryEntException {
        int customerCarLimit = item.getCustomer().getCustomerTypeEnt().getMaxVehiclesRentedNumber();
        int currentlyRentedNumber = filterRents(rent ->
                rent.getCustomer().getId().equals(item.getCustomer().getId()) &&
                        rent.getRentEndDate() == null).size();

        if (currentlyRentedNumber + 1 > customerCarLimit) {
            throw new RepositoryEntException("Customer max cars limit reached.");
        }
    }

    @Override
    public RentEnt get(UUID id) throws RepositoryEntException {
        return super.get(id);
    }

    @Override
    public List<RentEnt> getAll() {
        return super.getAll();
    }

    @Override
    public synchronized RentEnt update(RentEnt item) throws RepositoryEntException {
        RentEnt currentItem = get(item.getId());
        if (currentItem.getRentEndDate() != null) {
            throw new RepositoryEntException("Cannot edit finished reservation.");
        }
        customerRentLimitCheck(currentItem);
        return super.update(item);
    }

    @Override
    public synchronized void delete(UUID id) throws RepositoryEntException {
        RentEnt rentEnt = get(id);
        if (rentEnt.getRentEndDate() != null) {
            throw new RepositoryEntException("Cannot delete finished reservation.");
        }
        super.delete(id);
    }

    public RentEnt endRent(UUID id) throws RepositoryEntException {
        RentEnt rentEnt = get(id);
        try {
            rentEnt.endRent();

            CustomerEnt c = rentEnt.getCustomer();
            double rentsOverallPrice = filterRents(rent ->
                    rent.getCustomer().getId().equals(c.getId()) && rent.getRentEndDate() == null).stream()
                    .mapToDouble(RentEnt::getPrice).sum();

            if (rentsOverallPrice > 5000d && c.getCustomerTypeEnt() instanceof BasicCustomerEnt) {
                c.changeCustomerType(new SpecialCustomerEnt());
            }

            return get(id);
        } catch (RentEntException | CustomerEntException e) {
            throw new RepositoryEntException(e);
        }
    }

    public List<RentEnt> filterRents(Predicate<RentEnt> predicate) {
        return this.getAll().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void loadSampleData() {
        for (int i = 1; i < 5; i++) {
            CustomerEnt customer = CustomerEnt.builder()
                    .id(UUID.fromString("bcc5f975-d52c-4493-a9f6-50be79f7111" + i))
                    .firstname("Customer" + i)
                    .lastname("Kowalski" + i)
                    .login("Klient" + i)
                    .password("zaq1@WSX")
                    .build();


            CarEnt car = EconomyCarEnt.builder()
                    .id(UUID.fromString("bcc5f975-d52c-4493-a9f6-50be79f7222" + i))
                    .engineCapacity(1.5)
                    .doorNumber(5)
                    .brand("BMW" + i)
                    .basePricePerDay(1000d)
                    .driverEquipment("Immobilizer")
                    .build();

            try {
                add(RentEnt.builder()
                        .id(UUID.fromString("bcc5f975-d52c-4493-a9f6-50be79f7333" + i))
                        .car(car)
                        .customer(customer)
                        .build()
                );
            } catch (RepositoryEntException e) {
                e.printStackTrace();
            }
        }
    }
}
