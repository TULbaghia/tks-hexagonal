package pl.lodz.p.it.tks.rent.rest.validation.user;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.rent.rest.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddUserValidator implements ConstraintValidator<AddUserValid, UserDto> {
    private AddUserValid annotation;

    @Override
    public void initialize(AddUserValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        boolean firstname = checkConstraint(constraintValidatorContext, userDto.getFirstname(), "Firstname cannot be null or empty.");
        boolean lastname = checkConstraint(constraintValidatorContext, userDto.getLogin(), "Lastname cannot be null or empty.");
        boolean login = checkConstraint(constraintValidatorContext, userDto.getLastname(), "Login cannot be null or empty.");
        boolean password = checkConstraint(constraintValidatorContext, userDto.getPassword(), "Password cannot be null or empty.");

        boolean id = true;
        if (userDto.getId() != null) {
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
