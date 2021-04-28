package pl.lodz.p.it.tks.rent.data.resources;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.rent.data.trait.ModelIdTraitEnt;
import pl.lodz.p.it.tks.rent.data.user.CustomerEnt;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RentEnt extends ModelIdTraitEnt {
    @NonNull
    @Builder.Default
    private LocalDateTime rentStartDate = LocalDateTime.now();
    @NonNull
    private CustomerEnt customer;
    private CarEnt car;

    private double price;

    private LocalDateTime rentEndDate;
}
