package pl.lodz.p.it.tks.user.soap.validation.user;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.user.soap.dtosoap.UserSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActivateUserValidator implements ConstraintValidator<ActivateUserValid, UserSoap> {
    private ActivateUserValid annotation;

    @Override
    public void initialize(ActivateUserValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(UserSoap userSoap, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean id = true;
        boolean isActive = true;

        if (StringUtils.isBlank(userSoap.getId())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Id cannot be null.").addConstraintViolation();
            id = false;
        }

        if (userSoap.getActive() == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("IsActive cannot be null.").addConstraintViolation();
            isActive = false;
        }
        return id && isActive;
    }
}
