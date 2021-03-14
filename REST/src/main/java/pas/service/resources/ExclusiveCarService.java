package pas.service.resources;

import org.json.JSONObject;
import pas.service.exception.RestException;
import pas.service.validation.resources.UpdateCarValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.ExclusiveCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.Car;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("car/exclusive")
public class ExclusiveCarService {
    @Inject
    private ExclusiveCarUseCase exclusiveCarUseCase;

    @POST
    public String addExclusiveCar(@Valid ExclusiveCar exclusiveCar) {
        try {
            return JSONObject.wrap(exclusiveCarUseCase.add(exclusiveCar)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @GET
    public String getAllExclusiveCars() {
        return JSONObject.valueToString(exclusiveCarUseCase.getAll());
    }

    @Path("/{uuid}")
    @GET
    public String getExclusiveCar(@PathParam("uuid") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            uuid = null;
        }

        Car car;

        if(uuid == null) {
            try {
                car = exclusiveCarUseCase.get(id);
            } catch (RepositoryAdapterException e) {
                throw new RestException(e.getMessage());
            }
        } else {
            try {
                car = exclusiveCarUseCase.get(uuid);
            } catch (RepositoryAdapterException e) {
                throw new RestException(e.getMessage());
            }
        }

        return JSONObject.wrap(car).toString();
    }

    @PUT
    public String updateExclusiveCar(@UpdateCarValid @Valid ExclusiveCar exclusiveCar) {
        ExclusiveCar editingCar = ExclusiveCar.builder()
                .id(exclusiveCar.getId())
                .engineCapacity(exclusiveCar.getEngineCapacity())
                .vin(exclusiveCar.getVin())
                .brand(exclusiveCar.getBrand())
                .doorNumber(exclusiveCar.getDoorNumber())
                .basePricePerDay(exclusiveCar.getBasePricePerDay())
                .driverEquipment(exclusiveCar.getDriverEquipment())
                .boardPcName(exclusiveCar.getBoardPcName())
                .build();
        try {
            return JSONObject.wrap(exclusiveCarUseCase.update(editingCar)).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @Path("/{uuid}")
    @DELETE
    public String deleteExclusiveCar(@PathParam("uuid") String economyCarId) {
        try {
            ExclusiveCar exclusiveCar = exclusiveCarUseCase.get(UUID.fromString(economyCarId));
            exclusiveCarUseCase.delete(exclusiveCar.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            return jsonObject.toString();
        } catch (Exception e) {
            throw new RestException(e.getMessage());
        }
    }
}
