package pl.lodz.p.it.tks.repository;


import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CarEntRepository extends EntRepository<CarEnt> {
    @Override
    public synchronized CarEnt add(CarEnt item) throws RepositoryEntException {
        if (getAll().stream().anyMatch(x -> x.getVin().equals(item.getVin()))) {
            throw new RepositoryEntException("VIN must be unique");
        }
        return super.add(item);
    }

    @Override
    public CarEnt get(UUID id) throws RepositoryEntException {
        return super.get(id);
    }

    public CarEnt get(String vin) throws RepositoryEntException {
        return getAll().stream()
                .filter(economyCar -> economyCar.getVin().equals(vin))
                .findFirst().orElseThrow(() -> new RepositoryEntException("vin does not exist"));
    }

    @Override
    public List<CarEnt> getAll() {
        return super.getAll();
    }

    @Override
    public synchronized CarEnt update(CarEnt item) throws RepositoryEntException {
        return super.update(item);
    }

    @Override
    public synchronized void delete(UUID id) throws RepositoryEntException {
        super.delete(id);
    }

    @PostConstruct
    public void loadSampleData() {
        for (int i = 1; i < 9; i++) {
            try {
                add(EconomyCarEnt.builder()
                        .vin("1111111111111111" + i)
                        .engineCapacity(1.5)
                        .doorNumber(5)
                        .brand("BMW" + i)
                        .basePricePerDay(1000d)
                        .driverEquipment("Immobilizer")
                        .build()
                );
            } catch (RepositoryEntException e) {
                e.printStackTrace();
            }
        }
    }
}
