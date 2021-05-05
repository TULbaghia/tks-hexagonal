package pl.lodz.p.it.tks.user.domainmodel.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.user.domainmodel.trait.ModelIdTrait;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends ModelIdTrait {
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
    private boolean isActive = true;
    @NonNull
    private UserType userType;
}
