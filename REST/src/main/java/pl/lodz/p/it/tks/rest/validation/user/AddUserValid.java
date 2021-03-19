package pl.lodz.p.it.tks.rest.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddUserValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddUserValid {
    String message() default "{pl.lodz.mm.service.validate.AddUserValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


