package pl.lodz.p.it.tks.rent.rest.validation.resources;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EndRentValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndRentValid {
    String message() default "{pl.lodz.mm.service.validate.EndRentValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


