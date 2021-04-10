package pl.lodz.p.it.tks.soap.resources;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;
import pl.lodz.p.it.tks.soap.dtosoap.EconomyCarSoap;
import pl.lodz.p.it.tks.soap.exception.SoapException;
import pl.lodz.p.it.tks.soap.validation.resources.UpdateCarValid;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "EconomyCarAPI")
public class EconomyCarService {
    @Inject
    private EconomyCarUseCase economyCarUseCase;

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public EconomyCarSoap addEconomyCar(@Valid EconomyCarSoap economyCarSoap) {
        EconomyCar economyCar = EconomyCar.builder()
                .engineCapacity(economyCarSoap.getEngineCapacity())
                .vin(economyCarSoap.getVin())
                .brand(economyCarSoap.getBrand())
                .doorNumber(economyCarSoap.getDoorNumber())
                .basePricePerDay(economyCarSoap.getBasePricePerDay())
                .driverEquipment(economyCarSoap.getDriverEquipment())
                .build();
        try {
            return EconomyCarSoap.toSoap(economyCarUseCase.add(economyCar));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public List<EconomyCarSoap> getAllEconomyCars() {
        return economyCarUseCase.getAll().stream().map(EconomyCarSoap::toSoap).collect(Collectors.toList());
    }

    @WebMethod
    public EconomyCarSoap getEconomyCar(@WebParam(name = "id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            uuid = null;
        }

        EconomyCar car;

        if (uuid == null) {
            try {
                car = economyCarUseCase.get(id);
            } catch (RepositoryAdapterException e) {
                throw new SoapException(e.getMessage());
            }
        } else {
            try {
                car = economyCarUseCase.get(uuid);
            } catch (RepositoryAdapterException e) {
                throw new SoapException(e.getMessage());
            }
        }

        return EconomyCarSoap.toSoap(car);
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public EconomyCarSoap updateEconomyCar(@UpdateCarValid @Valid EconomyCarSoap economyCar) {
        EconomyCar editingCar = EconomyCar.builder()
                .id(UUID.fromString(economyCar.getId()))
                .engineCapacity(economyCar.getEngineCapacity())
                .vin(economyCar.getVin())
                .brand(economyCar.getBrand())
                .doorNumber(economyCar.getDoorNumber())
                .basePricePerDay(economyCar.getBasePricePerDay())
                .driverEquipment(economyCar.getDriverEquipment())
                .build();
        try {
            return EconomyCarSoap.toSoap(economyCarUseCase.update(editingCar));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public String deleteEconomyCar(@WebParam(name = "id") String id) {
        try {
            EconomyCar economyCar = economyCarUseCase.get(UUID.fromString(id));
            economyCarUseCase.delete(economyCar.getId());
            return "Success";
        } catch (Exception e) {
            throw new SoapException(e.getMessage());
        }
    }
}
