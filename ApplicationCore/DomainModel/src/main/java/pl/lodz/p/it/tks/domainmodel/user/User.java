package pl.lodz.p.it.tks.domainmodel.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.json.JSONPropertyIgnore;
import pl.lodz.p.it.tks.domainmodel.trait.ModelIdTrait;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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
    @Getter
    private boolean isActive = true;

    @JSONPropertyIgnore
    public String getPassword() {
        return password;
    }
}
