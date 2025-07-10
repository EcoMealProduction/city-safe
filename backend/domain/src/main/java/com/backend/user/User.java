package com.backend.user;

import lombok.Builder;
import lombok.NonNull;

import java.util.UUID;

/**
 * Reprezintă un utilizator al aplicației.
 * Fiecare utilizator este identificat unic printr-un {@code UUID} și are un username validat.
 *
 * <p>Validări:</p>
 * <ul>
 *   <li>Username-ul nu poate fi gol sau format doar din spații.</li>
 *   <li>Username-ul trebuie să aibă cel puțin 3 caractere.</li>
 *   <li>Username-ul trebuie să conțină doar caractere alfanumerice, punct (.), liniuță (-) sau underscore (_).</li>
 * </ul>
 *
 * @param id        ID-ul unic al utilizatorului.
 * @param username  Numele de utilizator (username), folosit pentru identificare și afișare.
 */
@Builder(toBuilder = true)
public record User(
        @NonNull UUID id,
        @NonNull String username
) {

    /**
     * Constructorul de validare pentru {@code User}.
     *
     * @throws IllegalArgumentException dacă:
     * <ul>
     *   <li>Username-ul este gol sau doar spații.</li>
     *   <li>Username-ul are mai puțin de 3 caractere.</li>
     *   <li>Username-ul conține caractere nepermise.</li>
     * </ul>
     */
    public User {

        if (username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty or only spaces.");
        }

        if (username.length() < 3)
            throw new IllegalArgumentException("Username must be at least 3 characters.");

        if (!username.matches("[a-zA-Z0-9._-]+")) {
            throw new IllegalArgumentException("Invalid username format.");
        }
    }
}
