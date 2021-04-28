package pl.lodz.p.it.tks.user.soap.validation.user;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.user.soap.dtosoap.UserSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateUserValidator implements ConstraintValidator<UpdateUserValid, UserSoap> {
    private UpdateUserValid annotation;

    @Override
    public void initialize(UpdateUserValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(UserSoap userSoap, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean firstname = checkConstraint(constraintValidatorContext, userSoap.getFirstname(), "Firstname cannot be null or empty.");
        boolean lastname = checkConstraint(constraintValidatorContext, userSoap.getLastname(), "Lastname cannot be null or empty.");
        boolean login = checkConstraint(constraintValidatorContext, userSoap.getLogin(), "Login cannot be null or empty.");
        boolean password = checkConstraint(constraintValidatorContext, userSoap.getPassword(), "Password cannot be null or empty.");
        boolean id = checkConstraint(constraintValidatorContext, userSoap.getId(), "Id cannot be null.");

        return id && firstname && lastname && login && password;
    }

    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
        if (StringUtils.isBlank(field)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
