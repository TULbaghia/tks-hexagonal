package pas.service.validation.resources;

import pl.lodz.p.it.tks.domainmodel.resources.Car;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UpdateCarValidator implements ConstraintValidator<UpdateCarValid, Car> {
    private UpdateCarValid annotation;

    @Override
    public void initialize(UpdateCarValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Car economyCar, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        return checkConstraint(constraintValidatorContext, economyCar.getId(), "Car ID cannot be null or empty.");
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, UUID field, String message) {
        if (field == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
