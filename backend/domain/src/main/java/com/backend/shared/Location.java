package com.backend.shared;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

import static com.backend.shared.GeoLimits.*;

/**
 * Reprezintă o locație geografică cu coordonate și adresă textuală.
 * Această entitate este folosită pentru a stoca locațiile incidentelor, evenimentelor sau utilizatorilor.
 * Locațiile sunt validate să se încadreze în granițele geografice ale Republicii Moldova.
 *
 * @param latitude  Latitudinea locației. Trebuie să fie între {@link GeoLimits#MIN_LATITUDE} și {@link GeoLimits#MAX_LATITUDE}.
 * @param longitude Longitudinea locației. Trebuie să fie între {@link GeoLimits#MIN_LONGITUDE} și {@link GeoLimits#MAX_LONGITUDE}.
 * @param address   Adresa completă a locației. Trebuie să conțină cel puțin 10 caractere.
 */
@Builder(toBuilder = true)
public record Location(
        @NonNull BigDecimal latitude,
        @NonNull BigDecimal longitude,
        @NonNull String address
) {

    /**
     * Constructor de validare a valorilor geografice.
     * Aruncă excepții dacă datele nu respectă limitele geografice sau formatul minim al adresei.
     */
    public Location {
        if (latitude.compareTo(MIN_LATITUDE) < 0 ||
                latitude.compareTo(MAX_LATITUDE) > 0)
            throw new IllegalArgumentException("Latitude must be within Moldova.");

        if (longitude.compareTo(MIN_LONGITUDE) < 0 ||
                longitude.compareTo(MAX_LONGITUDE) > 0)
            throw new IllegalArgumentException("Longitude must be within Moldova.");

        if (address.length() < 10)
            throw new IllegalArgumentException("Address must have at least 10 characters.");
    }
}
