package pl.lodz.p.it.tks.rent.rest.validation.resources;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.rent.rest.dto.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndRentValidator implements ConstraintValidator<EndRentValid, ReservationDto> {
    private EndRentValid annotation;

    @Override
    public void initialize(EndRentValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        return checkConstraint(constraintValidatorContext, reservationDto.getId(), "Reservation ID cannot be null or empty.");
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
        if (StringUtils.isBlank(field)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
