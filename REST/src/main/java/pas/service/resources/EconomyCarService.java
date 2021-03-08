package pas.service.resources;

import org.json.JSONObject;
import pas.service.exception.RestException;
import pas.service.validation.resources.UpdateCarValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.EconomyCarUseCase;
import pl.lodz.p.it.tks.domainmodel.resources.EconomyCar;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("car/economy")
public class EconomyCarService {
    @Inject
    private EconomyCarUseCase economyCarUseCase;

    @POST
    public String addEconomyCar(@Valid EconomyCar economyCar) {
        try {
            economyCarUseCase.add(economyCar);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(economyCarUseCase.get(economyCar.getId())).toString();
    }

    @GET
    public String getAllEconomyCars() {
        return JSONObject.valueToString(economyCarUseCase.getAll());
    }

    @Path("/{uuid}")
    @GET
    public String getEconomyCar(@PathParam("uuid") String id) {
        EconomyCar economyCar = economyCarUseCase.get(UUID.fromString(id));
        return JSONObject.wrap(economyCarUseCase.get(economyCar.getId())).toString();
    }

    @PUT
    public String updateEconomyCar(@UpdateCarValid @Valid EconomyCar economyCar) {
        EconomyCar editingCar = EconomyCar.builder()
                .id(economyCar.getId())
                .engineCapacity(economyCar.getEngineCapacity())
                .brand(economyCar.getBrand())
                .doorNumber(economyCar.getDoorNumber())
                .basePricePerDay(economyCar.getBasePricePerDay())
                .driverEquipment(economyCar.getDriverEquipment())
                .build();
        try {
            economyCarUseCase.update(editingCar);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(economyCarUseCase.get(editingCar.getId())).toString();
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