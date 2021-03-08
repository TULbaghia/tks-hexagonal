package pl.lodz.p.it.tks.repository;


import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CarEntRepository extends EntRepository<CarEnt> {
    @Override
    public synchronized void add(CarEnt item) throws RepositoryEntException {
        super.add(item);
    }

    @Override
    public CarEnt get(UUID id) {
        return super.get(id);
    }

    @Override
    public List<CarEnt> getAll() {
        return super.getAll();
    }

    @Override
    public synchronized void update(CarEnt item) throws RepositoryEntException, InvocationTargetException, IllegalAccessException {
        super.update(item);
    }

    @Override
    public synchronized void delete(UUID id) {
        super.delete(id);
    }

    @PostConstruct
    public void loadSampleData() {
        for (int i = 1; i < 9; i++) {
            try {
                add(EconomyCarEnt.builder()
                        .id(UUID.fromString("bcc5f975-d52c-4493-a9f6-50be79f7222" + i))
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
