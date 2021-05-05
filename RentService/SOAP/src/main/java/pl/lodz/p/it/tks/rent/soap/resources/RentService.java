package pl.lodz.p.it.tks.rent.soap.resources;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.RentUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.soap.dtosoap.RentSoap;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "RentAPI")
public class RentService {
    @Inject
    private RentUseCase rentUseCase;

    @WebMethod
    public List<RentSoap> getAllRents() {
        return rentUseCase.getAll().stream().map(RentSoap::toSoap).collect(Collectors.toList());
    }

    @WebMethod
    public RentSoap getRent(@WebParam(name = "id") String id) throws RepositoryAdapterException {
        Rent r = rentUseCase.get(UUID.fromString(id));
        return RentSoap.toSoap(r);
    }
}
