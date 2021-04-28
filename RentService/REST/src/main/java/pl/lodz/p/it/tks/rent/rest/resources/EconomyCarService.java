package pl.lodz.p.it.tks.rent.rest.resources;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.rest.dto.EconomyCarDto;
import pl.lodz.p.it.tks.rent.rest.validation.resources.UpdateCarValid;
import pl.lodz.p.it.tks.rent.rest.exception.RestException;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.resources.EconomyCar;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("car/economy")
public class EconomyCarService {
    private final EconomyCarUseCase economyCarUseCase;

    @Inject
    public EconomyCarService(EconomyCarUseCase economyCarUseCase) {
        this.economyCarUseCase = economyCarUseCase;
    }

    @POST
    public String addEconomyCar(@Valid EconomyCarDto economyCarDto) {
        EconomyCar economyCar = EconomyCar.builder()
                .engineCapacity(economyCarDto.getEngineCapacity())
                .vin(economyCarDto.getVin())
                .brand(economyCarDto.getBrand())
                .doorNumber(economyCarDto.getDoorNumber())
                .basePricePerDay(economyCarDto.getBasePricePerDay())
                .driverEquipment(economyCarDto.getDriverEquipment())
                .build();
        try {
            return JSONObject.wrap(EconomyCarDto.toDto(economyCarUseCase.add(economyCar))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllEconomyCars() {
        return JSONObject.valueToString(economyCarUseCase.getAll().stream().map(EconomyCarDto::toDto).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getEconomyCar(@PathParam("uuid") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            uuid = null;
        }

        EconomyCar car;

        if(uuid == null) {
            try {
                car = economyCarUseCase.get(id);
            } catch (RepositoryAdapterException e) {
                throw new RestException(e.getMessage());
            }
        } else {
            try {
                car = economyCarUseCase.get(uuid);
            } catch (RepositoryAdapterException e) {
                throw new RestException(e.getMessage());
            }
        }

        return JSONObject.wrap(EconomyCarDto.toDto(car)).toString();
    }

    @PUT
    public String updateEconomyCar(@UpdateCarValid @Valid EconomyCarDto economyCar) {
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
            return JSONObject.wrap(EconomyCarDto.toDto(economyCarUseCase.update(editingCar))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @Path("/{uuid}")
    @DELETE
    public String deleteEconomyCar(@PathParam("uuid") String economyCarId) {
        try {
            EconomyCar economyCar = economyCarUseCase.get(UUID.fromString(economyCarId));
            economyCarUseCase.delete(economyCar.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            return jsonObject.toString();
        } catch (Exception e) {
            throw new RestException(e.getMessage());
        }
    }
}
