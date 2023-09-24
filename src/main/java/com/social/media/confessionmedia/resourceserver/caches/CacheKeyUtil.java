package com.social.media.confessionmedia.resourceserver.caches;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CacheKeyUtil {

    public static String generatePostCacheKeyByMinute(){
        String PATTERN_FORMAT = "YYYY_MM_dd_HH_mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());
        Instant instant = Instant.now();
        String formattedInstant = formatter.format(instant);

        return "POST_"+formattedInstant;
    }

}
