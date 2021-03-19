package pl.lodz.p.it.tks.rest.validation.resources;

import pl.lodz.p.it.tks.rest.dto.CarDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UpdateCarValidator implements ConstraintValidator<UpdateCarValid, CarDto> {
    private UpdateCarValid annotation;

    @Override
    public void initialize(UpdateCarValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(CarDto car, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        return checkConstraint(constraintValidatorContext, car.getId(), "Car ID cannot be null or empty.");
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, UUID field, String message) {
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
