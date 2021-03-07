package pl.lodz.p.it.tks.data.model.trait;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
public abstract class IdTraitEnt {
    private UUID id;
}
