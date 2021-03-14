package pl.lodz.p.it.tks.data.resources;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.it.tks.data.exception.CustomerEntException;
import pl.lodz.p.it.tks.data.exception.RentEntException;
import pl.lodz.p.it.tks.data.trait.ModelIdTraitEnt;
import pl.lodz.p.it.tks.data.user.CustomerEnt;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
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
