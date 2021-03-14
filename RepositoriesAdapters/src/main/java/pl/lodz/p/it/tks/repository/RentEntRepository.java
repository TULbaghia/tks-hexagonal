package pl.lodz.p.it.tks.repository;

import pl.lodz.p.it.tks.data.resources.CarEnt;
import pl.lodz.p.it.tks.data.resources.EconomyCarEnt;
import pl.lodz.p.it.tks.data.resources.RentEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject
    private CarEntRepository carEntRepository;
    @Inject
    private UserEntRepository userEntRepository;

    @PostConstruct
    public void loadSampleData()  {
        for (int i = 1; i < 5; i++) {
            try {
                CustomerEnt customer = (CustomerEnt) userEntRepository.get(UUID.fromString("bcc5f975-d52c-4493-a9f6-50be79f7111" + i));

                CarEnt car = carEntRepository.get(UUID.fromString("bcc5f975-d52c-4493-a9f6-50be79f7222" + i));

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
