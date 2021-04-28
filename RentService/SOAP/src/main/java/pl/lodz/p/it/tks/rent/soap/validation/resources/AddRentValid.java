package pl.lodz.p.it.tks.rent.soap.validation.resources;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddRentValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AddRentValid {
    String message() default "{pl.lodz.mm.service.validate.AddRentValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


