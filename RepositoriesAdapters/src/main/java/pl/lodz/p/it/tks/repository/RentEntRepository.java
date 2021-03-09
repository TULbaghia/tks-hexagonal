package pl.lodz.p.it.tks.repository;

import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RentEntRepository extends EntRepository<RentEnt> {
    @Override
    public synchronized RentEnt add(RentEnt item) throws RepositoryEntException {
        return super.add(item);
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
        return super.update(item);
    }

    @Override
    public synchronized void delete(UUID id) throws RepositoryEntException {
        super.delete(id);
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
