package pl.lodz.p.it.tks.applicationcore.domainmodel.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.applicationcore.domainmodel.trait.IdTrait;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class User extends IdTrait {
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