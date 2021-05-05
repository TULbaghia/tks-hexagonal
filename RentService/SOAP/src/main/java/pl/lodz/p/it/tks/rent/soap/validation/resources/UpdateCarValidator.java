package pl.lodz.p.it.tks.rent.soap.validation.resources;

import pl.lodz.p.it.tks.rent.soap.dtosoap.CarSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateCarValidator implements ConstraintValidator<UpdateCarValid, CarSoap> {
    private UpdateCarValid annotation;

    @Override
    public void initialize(UpdateCarValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(CarSoap car, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        return checkConstraint(constraintValidatorContext, car.getId(), "Car ID cannot be null or empty.");
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
        if (field == null || field.equals("")) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
