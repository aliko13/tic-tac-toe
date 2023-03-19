package com.example.tictactoe.common;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface Clock {
    String APPLICATION_TIME_ZONE = "Europe/Berlin";
    ZoneId APPLICATION_ZONE_ID = ZoneId.of(APPLICATION_TIME_ZONE);

    ZonedDateTime getApplicationZonedDateTime();
}
