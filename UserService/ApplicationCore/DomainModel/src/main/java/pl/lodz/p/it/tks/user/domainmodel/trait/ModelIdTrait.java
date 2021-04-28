package pl.lodz.p.it.tks.user.domainmodel.trait;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ModelIdTrait {
    private UUID id;
}
