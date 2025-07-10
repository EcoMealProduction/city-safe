package com.backend.user;

import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reprezintă un comentariu adăugat la un {@code Happening} (Incident sau Event).
 * Fiecare comentariu este legat de un utilizator, un eveniment/incident și conține textul comentariului
 * împreună cu data creării.
 *
 * <p>Validări:</p>
 * <ul>
 *   <li>Textul trebuie să aibă minim 5 caractere și să nu fie gol.</li>
 *   <li>Data creării nu poate fi în viitor.</li>
 * </ul>
 *
 * @param id           ID-ul unic al comentariului.
 * @param happeningId  ID-ul entității (Incident sau Event) la care aparține comentariul.
 * @param user         Utilizatorul care a scris comentariul.
 * @param text         Conținutul comentariului.
 * @param createdAt    Data și ora la care a fost creat comentariul.
 */
@Builder(toBuilder = true)
public record Comment(
        @NonNull UUID id,
        @NonNull UUID happeningId,
        @NonNull User user,
        @NonNull String text,
        @NonNull LocalDateTime createdAt
        ) {

    /**
     * Constructor de validare. Asigură consistența datelor la crearea unui comentariu.
     *
     * @throws IllegalArgumentException dacă:
     *         - textul este gol sau are mai puțin de 5 caractere,
     *         - data creării este în viitor.
     */
    public Comment {
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty or only spaces.");
        }

        if (text.length() < 5) {
            throw new IllegalArgumentException("Text must have at least 5 characters.");
        }

        if (createdAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Comment date cannot be in the future.");
        }
    }
}
