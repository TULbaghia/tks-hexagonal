package pl.lodz.p.it.tks.rent.soap.resources;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.rent.soap.validation.resources.UpdateCarValid;
import pl.lodz.p.it.tks.rent.soap.dtosoap.ExclusiveCarSoap;
import pl.lodz.p.it.tks.rent.soap.exception.SoapException;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.ExclusiveCarUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.resources.ExclusiveCar;

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
@WebService(serviceName = "ExclusiveCarAPI")
public class ExclusiveCarService {
    @Inject
    private ExclusiveCarUseCase exclusiveCarUseCase;

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public ExclusiveCarSoap addExclusiveCar(@Valid ExclusiveCarSoap exclusiveCarDto) {
        ExclusiveCar exclusiveCar = ExclusiveCar.builder()
                .engineCapacity(exclusiveCarDto.getEngineCapacity())
                .vin(exclusiveCarDto.getVin())
                .brand(exclusiveCarDto.getBrand())
                .doorNumber(exclusiveCarDto.getDoorNumber())
                .basePricePerDay(exclusiveCarDto.getBasePricePerDay())
                .driverEquipment(exclusiveCarDto.getDriverEquipment())
                .boardPcName(exclusiveCarDto.getBoardPcName())
                .build();
        try {
            return ExclusiveCarSoap.toSoap(exclusiveCarUseCase.add(exclusiveCar));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public List<ExclusiveCarSoap> getAllExclusiveCars() {
        return exclusiveCarUseCase.getAll().stream().map(ExclusiveCarSoap::toSoap).collect(Collectors.toList());
    }

    @WebMethod
    public ExclusiveCarSoap getExclusiveCar(@WebParam(name = "id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            uuid = null;
        }

        ExclusiveCar car;

        if(uuid == null) {
            try {
                car = exclusiveCarUseCase.get(id);
            } catch (RepositoryAdapterException e) {
                throw new SoapException(e.getMessage());
            }
        } else {
            try {
                car = exclusiveCarUseCase.get(uuid);
            } catch (RepositoryAdapterException e) {
                throw new SoapException(e.getMessage());
            }
        }

        return ExclusiveCarSoap.toSoap(car);
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public ExclusiveCarSoap updateExclusiveCar(@UpdateCarValid @Valid ExclusiveCarSoap exclusiveCar) {
        ExclusiveCar editingCar = ExclusiveCar.builder()
                .id(UUID.fromString(exclusiveCar.getId()))
                .engineCapacity(exclusiveCar.getEngineCapacity())
                .vin(exclusiveCar.getVin())
                .brand(exclusiveCar.getBrand())
                .doorNumber(exclusiveCar.getDoorNumber())
                .basePricePerDay(exclusiveCar.getBasePricePerDay())
                .driverEquipment(exclusiveCar.getDriverEquipment())
                .boardPcName(exclusiveCar.getBoardPcName())
                .build();
        try {
            return ExclusiveCarSoap.toSoap(exclusiveCarUseCase.update(editingCar));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public String deleteExclusiveCar(@WebParam(name = "id") String id) {
        try {
            ExclusiveCar exclusiveCar = exclusiveCarUseCase.get(UUID.fromString(id));
            exclusiveCarUseCase.delete(exclusiveCar.getId());
            return "Success";
        } catch (Exception e) {
            throw new SoapException(e.getMessage());
        }
    }
}
