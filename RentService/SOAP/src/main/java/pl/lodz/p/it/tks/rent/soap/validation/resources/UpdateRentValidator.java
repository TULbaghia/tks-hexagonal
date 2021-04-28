package pl.lodz.p.it.tks.rent.soap.validation.resources;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.rent.soap.dtosoap.RentSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class UpdateRentValidator implements ConstraintValidator<UpdateRentValid, RentSoap> {
    private UpdateRentValid annotation;

    @Override
    public void initialize(UpdateRentValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(RentSoap rentSoap, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean id = checkConstraint(constraintValidatorContext, rentSoap.getId(), "Reservation ID cannot be null or empty.");
        boolean carId = checkConstraint(constraintValidatorContext, rentSoap.getCarId(), "Car ID cannot be null or empty.");
        boolean customerId = checkConstraint(constraintValidatorContext, rentSoap.getCustomerId(), "Customer ID cannot be null or empty.");
        boolean dateNotBlank = checkConstraint(constraintValidatorContext, rentSoap.getRentStartDate(), "RentStartDate cannot be null.");
        boolean date = true;

        if (dateNotBlank) {
            try {
                LocalDateTime.parse(rentSoap.getRentStartDate());
            } catch (DateTimeParseException e) {
                date = false;
                constraintValidatorContext.buildConstraintViolationWithTemplate("RentStartDate should be formatted as - yyyy-MM-ddTHH:mm:ss").addConstraintViolation();
            }
        }
        return id && carId && customerId && date && dateNotBlank;
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
        if (StringUtils.isBlank(field)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
