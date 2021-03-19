package pl.lodz.p.it.tks.rest.validation.resources;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.rest.dto.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AddRentValidator implements ConstraintValidator<AddRentValid, ReservationDto> {
    private AddRentValid annotation;

    @Override
    public void initialize(AddRentValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean carId = checkConstraint(constraintValidatorContext, reservationDto.getCarId(), "Car ID cannot be null or empty.");
        boolean customerId = checkConstraint(constraintValidatorContext, reservationDto.getCustomerId(), "Customer ID cannot be null or empty.");
        boolean dateNotBlank = checkConstraint(constraintValidatorContext, reservationDto.getRentStartDate(), "RentStartDate cannot be null.");
        boolean date = true;

        if (dateNotBlank) {
            try {
                LocalDateTime.parse(reservationDto.getRentStartDate());
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
