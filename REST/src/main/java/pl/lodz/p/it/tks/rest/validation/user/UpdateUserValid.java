package pl.lodz.p.it.tks.rest.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdateUserValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateUserValid {
    String message() default "{pl.lodz.mm.service.validate.UserUpdateValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


