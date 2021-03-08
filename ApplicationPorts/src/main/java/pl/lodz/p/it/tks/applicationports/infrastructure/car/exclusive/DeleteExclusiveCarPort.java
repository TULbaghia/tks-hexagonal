package pl.lodz.p.it.tks.applicationports.infrastructure.car.exclusive;

import lombok.NonNull;

import java.util.UUID;

public interface DeleteExclusiveCarPort {
    void delete(@NonNull UUID id);
}
