package pl.lodz.p.it.tks.user.rest.validation.user;

import org.apache.commons.lang3.StringUtils;
import pl.lodz.p.it.tks.user.rest.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActivateUserValidator implements ConstraintValidator<ActivateUserValid, UserDto> {
    private ActivateUserValid annotation;

    @Override
    public void initialize(ActivateUserValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean id = true;
        boolean isActive = true;

        if (StringUtils.isBlank(userDto.getId())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Id cannot be null.").addConstraintViolation();
            id = false;
        }

        if (userDto.getActive() == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("IsActive cannot be null.").addConstraintViolation();
            isActive = false;
        }
        return id && isActive;
    }
}
