package pl.lodz.p.it.tks.soap.validation.resources;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.soap.dtosoap.RentSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AddRentValidator implements ConstraintValidator<AddRentValid, RentSoap> {
    private AddRentValid annotation;

    @Override
    public void initialize(AddRentValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(RentSoap rentSoap, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

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
        return carId && customerId && date && dateNotBlank;
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
        if (StringUtils.isBlank(field)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
