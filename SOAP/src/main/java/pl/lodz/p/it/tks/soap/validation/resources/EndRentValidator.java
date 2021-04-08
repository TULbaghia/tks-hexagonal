package pl.lodz.p.it.tks.soap.validation.resources;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.soap.dtosoap.RentSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndRentValidator implements ConstraintValidator<EndRentValid, RentSoap> {
    private EndRentValid annotation;

    @Override
    public void initialize(EndRentValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(RentSoap rentSoap, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        return checkConstraint(constraintValidatorContext, rentSoap.getId(), "Reservation ID cannot be null or empty.");
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
        if (StringUtils.isBlank(field)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
