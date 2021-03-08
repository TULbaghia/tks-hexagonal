package pas.service.validation.resources;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UpdateCarValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateCarValid {
    String message() default "{pl.lodz.mm.service.validate.UpdateEconomyCarValid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


