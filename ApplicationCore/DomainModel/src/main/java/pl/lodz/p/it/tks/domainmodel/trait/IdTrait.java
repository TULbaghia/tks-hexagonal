package pl.lodz.p.it.tks.domainmodel.trait;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
public abstract class IdTrait {
    private UUID id;
}
