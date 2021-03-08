//package pas.service.validation.user;
//
//import org.apache.commons.lang3.StringUtils;
//import pl.lodz.mm.pas.service.dto.UserDto;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class UpdateUserValidator implements ConstraintValidator<UpdateUserValid, UserDto> {
//    private UpdateUserValid annotation;
//
//    @Override
//    public void initialize(UpdateUserValid constraintAnnotation) {
//        annotation = constraintAnnotation;
//    }
//
//    @Override
//    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
//        constraintValidatorContext.disableDefaultConstraintViolation();
//
//        boolean firstname = checkConstraint(constraintValidatorContext, userDto.getFirstname(), "Firstname cannot be null or empty.");
//        boolean lastname = checkConstraint(constraintValidatorContext, userDto.getLastname(), "Lastname cannot be null or empty.");
//        boolean login = checkConstraint(constraintValidatorContext, userDto.getLogin(), "Login cannot be null or empty.");
//        boolean password = checkConstraint(constraintValidatorContext, userDto.getPassword(), "Password cannot be null or empty.");
//        boolean id = checkConstraint(constraintValidatorContext, userDto.getId(), "Id cannot be null.");
//
//        return id && firstname && lastname && login && password;
//    }
//
//    private boolean checkConstraint(ConstraintValidatorContext constraintValidatorContext, String field, String message) {
//        if (StringUtils.isBlank(field)) {
//            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//            return false;
//        }
//        return true;
//    }
//}
