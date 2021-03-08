package pas.service.validation.user;

import org.apache.commons.lang3.StringUtils;
import pas.service.dto.UserDto;

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

        if (userDto.getIsActive() == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("IsActive cannot be null.").addConstraintViolation();
            isActive = false;
        }
        return id && isActive;
    }
}