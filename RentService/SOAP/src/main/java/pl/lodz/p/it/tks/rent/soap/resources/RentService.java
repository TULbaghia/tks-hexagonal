package pl.lodz.p.it.tks.rent.soap.resources;

import lombok.NoArgsConstructor;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.*;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Car;
import pl.lodz.p.it.tks.rent.domainmodel.resources.Rent;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;
import pl.lodz.p.it.tks.rent.soap.validation.resources.AddRentValid;
import pl.lodz.p.it.tks.rent.soap.validation.resources.UpdateRentValid;
import pl.lodz.p.it.tks.rent.soap.dtosoap.RentSoap;
import pl.lodz.p.it.tks.rent.soap.exception.SoapException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "RentAPI")
public class RentService {
    @Inject
    private RentUseCase rentUseCase;
    @Inject
    private EmployeeUseCase employeeUseCase;
    @Inject
    private CustomerUseCase customerUseCase;
    @Inject
    private EconomyCarUseCase economyCarUseCase;
    @Inject
    private ExclusiveCarUseCase exclusiveCarUseCase;

    @Resource
    private WebServiceContext sessionContext;

    private User getUser(String login) throws RepositoryAdapterException {
        try {
            User user = employeeUseCase.get(login);
            if (user != null) {
                return user;
            }
        } catch (RepositoryAdapterException | ClassCastException ignore) {
        }

        return customerUseCase.get(login);
    }


    @WebMethod
    public List<RentSoap> getAllRents() throws RepositoryAdapterException {
        String currentUser = sessionContext.getUserPrincipal().getName();

        User user = getUser(currentUser);

        if (user instanceof Employee) {
            return rentUseCase.getAll().stream().map(RentSoap::toSoap).collect(Collectors.toList());
        } else if (user instanceof Customer) {
            return rentUseCase.getAll().stream()
                    .filter(x -> x.getCustomer().getId().equals(user.getId())).map(RentSoap::toSoap).collect(Collectors.toList());
        }
        return null;
    }

    @WebMethod
    public RentSoap getRent(@WebParam(name = "id") String id) throws RepositoryAdapterException {
        String currentUser = sessionContext.getUserPrincipal().getName();
        User user = getUser(currentUser);
        Rent r = rentUseCase.get(UUID.fromString(id));
        if (user instanceof Employee) {
            return RentSoap.toSoap(r);
        } else if (user instanceof Customer && r.getCustomer().getLogin().equals(currentUser)) {
            return RentSoap.toSoap(r);
        }
        return null;
    }

    private Car getCarById(UUID id) throws RepositoryAdapterException {
        try {
            Car car = economyCarUseCase.get(id);
            if (car != null) {
                return car;
            }
        } catch (RepositoryAdapterException ignore) {
        }

        return exclusiveCarUseCase.get(id);
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public RentSoap addRent(@AddRentValid RentSoap rentSoap) {
        String currentUser = sessionContext.getUserPrincipal().getName();

        Car car;
        Customer customer;
        User user;
        LocalDateTime dateTime = LocalDateTime.parse(rentSoap.getRentStartDate());
        try {
            car = getCarById(UUID.fromString(rentSoap.getCarId()));
            customer = customerUseCase.get(UUID.fromString(rentSoap.getCustomerId()));
            carCustomerNull(customer, car);
            user = getUser(currentUser);
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
        if (user instanceof Customer && !customer.getLogin().equals(currentUser)) {
            throw new SoapException("Permissions error.");
        }
        Rent newRent = Rent.builder()
                .customer(customer)
                .car(car)
                .rentStartDate(dateTime)
                .build();

        try {
            return RentSoap.toSoap(rentUseCase.add(newRent));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public RentSoap updateRent(@UpdateRentValid RentSoap reservationDto) {
        Car car;
        Customer customer;
        try {
            car = getCarById(UUID.fromString(reservationDto.getCarId()));
            customer = customerUseCase.get(UUID.fromString(reservationDto.getCustomerId()));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
        carCustomerNull(customer, car);

        Rent newRent = Rent.builder()
                .id(UUID.fromString(reservationDto.getId()))
                .car(car)
                .customer(customer)
                .rentStartDate(LocalDateTime.parse(reservationDto.getRentStartDate()))
                .build();

        try {
            return RentSoap.toSoap(rentUseCase.update(newRent));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    public String deleteRent(@WebParam(name = "id") String id) {
        try {
            Rent reservation = rentUseCase.get(UUID.fromString(id));
            rentUseCase.delete(reservation.getId());
            return "Success";
        } catch (Exception e) {
            throw new SoapException(e.getMessage());
        }
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public RentSoap endRent(RentSoap rentSoap) {
        try {
            return RentSoap.toSoap(rentUseCase.endRent(UUID.fromString(rentSoap.getId())));
        } catch (RepositoryAdapterException e) {
            throw new SoapException(e.getMessage());
        }
    }

    private void carCustomerNull(Customer customer, Car car) {
        if (customer == null) {
            throw new SoapException("Customer is null.");
        }
        if (car == null) {
            throw new SoapException("Car is null.");
        }
    }
}
