package pas.service.resources;

import org.json.JSONObject;
import pas.service.exception.RestException;
import pas.service.validation.resources.UpdateCarValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.ExclusiveCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.ExclusiveCar;

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
            exclusiveCarUseCase.add(exclusiveCar);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(exclusiveCarUseCase.get(exclusiveCar.getId())).toString();
    }

    @GET
    public String getAllExclusiveCars() {
        return JSONObject.valueToString(exclusiveCarUseCase.getAll());
    }

    @Path("/{uuid}")
    @GET
    public String getExclusiveCar(@PathParam("uuid") String id) {
        ExclusiveCar exclusiveCar = exclusiveCarUseCase.get(UUID.fromString(id));
        return JSONObject.wrap(exclusiveCarUseCase.get(exclusiveCar.getId())).toString();
    }

    @PUT
    public String updateExclusiveCar(@UpdateCarValid @Valid ExclusiveCar exclusiveCar) {
        ExclusiveCar editingCar = ExclusiveCar.builder()
                .id(exclusiveCar.getId())
                .engineCapacity(exclusiveCar.getEngineCapacity())
                .brand(exclusiveCar.getBrand())
                .doorNumber(exclusiveCar.getDoorNumber())
                .basePricePerDay(exclusiveCar.getBasePricePerDay())
                .driverEquipment(exclusiveCar.getDriverEquipment())
                .boardPcName(exclusiveCar.getBoardPcName())
                .build();
        try {
            exclusiveCarUseCase.update(editingCar);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(exclusiveCarUseCase.get(editingCar.getId())).toString();
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
