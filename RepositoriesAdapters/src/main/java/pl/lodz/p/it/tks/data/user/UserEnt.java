package pl.lodz.p.it.tks.data.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.trait.IdTraitEnt;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class UserEnt extends IdTraitEnt {
    @NotEmpty
    @NonNull
    private String login;

    @NotEmpty
    @NonNull
    private String password;

    @NotEmpty
    @NonNull
    private String firstname;

    @NotEmpty
    @NonNull
    private String lastname;

    @Builder.Default
    @NonNull
    private boolean active = true;
}