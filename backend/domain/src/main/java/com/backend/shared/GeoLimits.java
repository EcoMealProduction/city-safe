package com.backend.shared;

import java.math.BigDecimal;

/**
 * Limite geografice statice care definesc granițele Republicii Moldova.
 * Aceste constante pot fi folosite pentru validarea pozițiilor GPS ale locațiilor din aplicație.
 */
public class GeoLimits {
    /**
     * Latitudinea minimă permisă în granițele Republicii Moldova (aproximativ sudul țării).
     */
    public static final BigDecimal MIN_LATITUDE = BigDecimal.valueOf(45.467);

    /**
     * Latitudinea maximă permisă în granițele Republicii Moldova (aproximativ nordul țării).
     */
    public static final BigDecimal MAX_LATITUDE = BigDecimal.valueOf(48.491);

    /**
     * Longitudinea minimă permisă în granițele Republicii Moldova (aproximativ vestul țării).
     */
    public static final BigDecimal MIN_LONGITUDE = BigDecimal.valueOf(26.616);

    /**
     * Longitudinea maximă permisă în granițele Republicii Moldova (aproximativ estul țării).
     */
    public static final BigDecimal MAX_LONGITUDE = BigDecimal.valueOf(30.133);
}
