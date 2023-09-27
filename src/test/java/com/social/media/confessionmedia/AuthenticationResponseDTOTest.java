package com.social.media.confessionmedia;


import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AuthenticationResponseDTOTest {

    @Test
    public void testDateToken(){
        String PATTERN_FORMAT = "dd/MM/YYYY hh:mm:ss";
        Instant instant = Instant.now().plusSeconds(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        String formattedInstant = formatter.format(instant);
        System.out.println(formattedInstant);

    }
}
