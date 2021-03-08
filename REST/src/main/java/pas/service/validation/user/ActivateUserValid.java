package pas.service.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ActivateUserValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivateUserValid {
    String message() default "{pl.lodz.mm.service.validate.ActivateUserValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


