package pl.lodz.p.it.tks.rent.rest.resources;

import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.applicationports.ui.*;
import pl.lodz.p.it.tks.rent.rest.dto.ReservationDto;
import pl.lodz.p.it.tks.rent.rest.validation.resources.AddRentValid;
import pl.lodz.p.it.tks.rent.rest.validation.resources.UpdateRentValid;
import pl.lodz.p.it.tks.rent.rest.exception.RestException;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Car;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("rent")
public class RentService {

    private final RentUseCase rentUseCase;

    private final EmployeeUseCase employeeUseCase;

    private final CustomerUseCase customerUseCase;

    private final EconomyCarUseCase economyCarUseCase;

    private final ExclusiveCarUseCase exclusiveCarUseCase;

    @Inject
    public RentService(RentUseCase rentUseCase, EmployeeUseCase employeeUseCase, CustomerUseCase customerUseCase, EconomyCarUseCase economyCarUseCase, ExclusiveCarUseCase exclusiveCarUseCase) {
        this.rentUseCase = rentUseCase;
        this.employeeUseCase = employeeUseCase;
        this.customerUseCase = customerUseCase;
        this.economyCarUseCase = economyCarUseCase;
        this.exclusiveCarUseCase = exclusiveCarUseCase;
    }

    private User getUser(String login) throws RepositoryAdapterException {
        try {
            User user = employeeUseCase.get(login);
            if(user != null) {
                return user;
            }
        } catch (RepositoryAdapterException | ClassCastException ignore) {
        }

        return customerUseCase.get(login);
    }


    @GET
    public String getAllRents(@Context SecurityContext securityContext) throws RepositoryAdapterException {
        String currentUser = securityContext.getUserPrincipal().getName();

        User user = getUser(currentUser);

        if (user instanceof Employee) {
            return JSONObject.valueToString(rentUseCase.getAll().stream().map(ReservationDto::toDto).collect(Collectors.toList()));
        } else if (user instanceof Customer){
            return JSONObject.valueToString(rentUseCase.getAll().stream()
                    .filter(x -> x.getCustomer().getId().equals(user.getId())).map(ReservationDto::toDto).collect(Collectors.toList()));
        }
        return null;
    }

    @Path("/{uuid}")
    @GET
    public String getRent(@PathParam("uuid") String id, @Context SecurityContext securityContext) throws RepositoryAdapterException {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user = getUser(currentUser);
        Rent r = rentUseCase.get(UUID.fromString(id));
        if (user instanceof Employee) {
            return JSONObject.wrap(ReservationDto.toDto(r)).toString();
        } else if (user instanceof Customer && r.getCustomer().getLogin().equals(currentUser)) {
            return JSONObject.wrap(ReservationDto.toDto(r)).toString();
        }
        return null;
    }

    private Car getCarById(UUID id) throws RepositoryAdapterException {
        try {
            Car car = economyCarUseCase.get(id);
            if(car != null) {
                return car;
            }
        } catch (RepositoryAdapterException ignore) {
        }

        return exclusiveCarUseCase.get(id);
    }

    @POST
    public String addRent(@AddRentValid ReservationDto reservationDto, @Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();

        Car car;
        Customer customer;
        User user;
        LocalDateTime dateTime = LocalDateTime.parse(reservationDto.getRentStartDate());
        try {
            car = getCarById(UUID.fromString(reservationDto.getCarId()));
            customer = customerUseCase.get(UUID.fromString(reservationDto.getCustomerId()));
            carCustomerNull(customer, car);
            user = getUser(currentUser);
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        if (user instanceof Customer && !customer.getLogin().equals(currentUser)) {
            throw new RestException("Permissions error.");
        }
        Rent newRent = Rent.builder()
                .customer(customer)
                .car(car)
                .rentStartDate(dateTime)
                .build();

        try {
            return JSONObject.wrap(ReservationDto.toDto(rentUseCase.add(newRent))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    @PUT
    public String updateRent(@UpdateRentValid ReservationDto reservationDto) {
        Car car;
        Customer customer;
        try {
            car = getCarById(UUID.fromString(reservationDto.getCarId()));
            customer = customerUseCase.get(UUID.fromString(reservationDto.getCustomerId()));
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
        carCustomerNull(customer, car);

        Rent newRent = Rent.builder()
                .id(UUID.fromString(reservationDto.getId()))
                .car(car)
                .customer(customer)
                .rentStartDate(LocalDateTime.parse(reservationDto.getRentStartDate()))
                .build();

        try {
            return JSONObject.wrap(ReservationDto.toDto(rentUseCase.update(newRent))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
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
        try {
            return JSONObject.wrap(ReservationDto.toDto(rentUseCase.endRent(UUID.fromString(reservationDto.getId())))).toString();
        } catch (RepositoryAdapterException e) {
            throw new RestException(e.getMessage());
        }
    }

    private void carCustomerNull(Customer customer, Car car) {
        if (customer == null) {
            throw new RestException("Customer is null.");
        }
        if (car == null) {
            throw new RestException("Car is null.");
        }
    }
}
