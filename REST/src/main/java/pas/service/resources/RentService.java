package pas.service.resources;

import org.json.JSONObject;
import pas.service.dto.ReservationDto;
import pas.service.exception.RestException;
import pas.service.validation.resources.AddRentValid;
import pas.service.validation.resources.UpdateRentValid;
import pl.lodz.p.it.tks.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.applicationports.ui.RentUseCase;
import pl.lodz.p.it.tks.domainmodel.exception.CustomerException;
import pl.lodz.p.it.tks.domainmodel.exception.RentException;
import pl.lodz.p.it.tks.domainmodel.resources.Car;
import pl.lodz.p.it.tks.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.domainmodel.user.Customer;
import pl.lodz.p.it.tks.repository.exception.RepositoryEntException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.UUID;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("rent")
public class RentService {

    @Inject
    private RentUseCase rentUseCase;

    @GET
    public String getAllRents(@Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user = userManager.get(currentUser);
        if (user instanceof Employee) {
            return JSONObject.valueToString(rentUseCase.getAll());
        } else if (user instanceof Customer){
            return JSONObject.valueToString(rentUseCase.getAll());
        }
        return null;
    }

    @Path("/{uuid}")
    @GET
    public String getRent(@PathParam("uuid") String id,  @Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user = userManager.get(currentUser);
        Rent r = rentUseCase.get(UUID.fromString(id));
        if (user instanceof Employee) {
            return JSONObject.wrap(r).toString();
        } else if (user instanceof Customer && r.getCustomer().getLogin().equals(currentUser)){
            return JSONObject.wrap(r).toString();
        }
        return null;
    }

    @POST
    public String addRent(@AddRentValid ReservationDto reservationDto, @Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();

        Car car = carManager.get(UUID.fromString(reservationDto.getCarId()));
        Customer customer = (Customer) userManager.get(UUID.fromString(reservationDto.getCustomerId()));
        LocalDateTime dateTime = LocalDateTime.parse(reservationDto.getRentStartDate());
        carCustomerNull(customer, car);

        User user = userManager.get(currentUser);
        if (user instanceof Customer && !customer.getLogin().equals(currentUser)) {
            throw new RestException("Permissions error.");
        }
        Rent newRent = Rent.builder()
                .customer(customer)
                .car(car)
                .rentStartDate(dateTime)
                .build();

        try {
            rentManager.add(newRent);
        } catch (ManagerException | RepositoryException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(rentManager.get(newRent.getId())).toString();
    }

    @PUT
    public String updateRent(@UpdateRentValid ReservationDto reservationDto) {
        Car car = carManager.get(UUID.fromString(reservationDto.getCarId()));
        Customer customer = (Customer) userManager.get(UUID.fromString(reservationDto.getCustomerId()));
        carCustomerNull(customer, car);

        Rent newRent = Rent.builder()
                .id(UUID.fromString(reservationDto.getId()))
                .car(car)
                .customer(customer)
                .rentStartDate(LocalDateTime.parse(reservationDto.getRentStartDate()))
                .build();

        try {
            rentManager.update(newRent);
        } catch (ManagerException | RepositoryException | IllegalAccessException | InvocationTargetException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(rentManager.get(newRent.getId())).toString();
    }

    @Path("/{uuid}")
    @DELETE
    public String deleteRent(@PathParam("uuid") String reservationId) {
        try {
            Rent reservation = rentUseCase.get(UUID.fromString(reservationId));
            rentUseCase.delete(reservation.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            return jsonObject.toString();
        } catch (Exception e) {
            throw new RestException(e.getMessage());
        }
    }

    @Path("/end")
    @PATCH
    public String endRent(ReservationDto reservationDto) {
        Rent rent;
        try {
            rent = rentUseCase.get(UUID.fromString(reservationDto.getId()));
            rent.endRent();
            return JSONObject.wrap(rent).toString();
        } catch (RentException | CustomerException | RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    private void carCustomerNull(Customer customer, Car car) {
        if(customer == null) {
            throw new RestException("Customer is null.");
        }
        if(car == null) {
            throw new RestException("Car is null.");
        }
    }
}
