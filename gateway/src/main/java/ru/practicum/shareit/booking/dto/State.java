package ru.practicum.shareit.booking.dto;

import java.util.Optional;

public enum State {
    WAITING, REJECTED, ALL, CURRENT, PAST, FUTURE;

    public static Optional<State> from(String stringState) {
        for (State state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
