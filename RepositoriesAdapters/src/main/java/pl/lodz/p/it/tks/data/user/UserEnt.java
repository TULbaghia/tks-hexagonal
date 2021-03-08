package pl.lodz.p.it.tks.data.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.trait.ModelIdTraitEnt;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserEnt extends ModelIdTraitEnt {
    @NonNull
    private String login;
    @NonNull
    private String password;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @Builder.Default
    @NonNull
    @Getter
    private boolean isActive = true;

    public String getPassword() {
        return password;
    }
}
