package pl.lodz.p.it.tks.soap.validation.user;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.soap.dtosoap.UserSoap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddUserValidator implements ConstraintValidator<AddUserValid, UserSoap> {
    private AddUserValid annotation;

    @Override
    public void initialize(AddUserValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(UserSoap userSoap, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean firstname = checkConstraint(constraintValidatorContext, userSoap.getFirstname(), "Firstname cannot be null or empty.");
        boolean lastname = checkConstraint(constraintValidatorContext, userSoap.getLogin(), "Lastname cannot be null or empty.");
        boolean login = checkConstraint(constraintValidatorContext, userSoap.getLastname(), "Login cannot be null or empty.");
        boolean password = checkConstraint(constraintValidatorContext, userSoap.getPassword(), "Password cannot be null or empty.");

        boolean id = true;
        if (userSoap.getId() != null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Id cannot be set while sending a request.").addConstraintViolation();
            id = false;
        }
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
