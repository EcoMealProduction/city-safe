package com.backend.happening;

import com.backend.shared.Location;
import com.backend.user.Comment;
import com.backend.user.User;
import lombok.Builder;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Reprezintă un eveniment publicat de un utilizator.
 * Conține informații despre locație, autor, timp de început/sfârșit și reacții (like/dislike/comentarii).
 */
@Builder(toBuilder = true)
public record Event(
        @NonNull UUID id,
        @NonNull String title,
        @NonNull String description,
        @NonNull User user,
        @NonNull Location location,
        List<Comment> comments,
        int likes,
        int dislikes,
        @NonNull LocalDateTime startTime,
        @NonNull LocalDateTime endTime
) implements Happening{

    /**
     * Constructorul cu validări pentru titlu, descriere, reacții și timp.
     */
    public Event {
        if (title.length() < 10) {
            throw new IllegalArgumentException("Title too short.");
        }

        if (description.length() < 10) {
            throw new IllegalArgumentException("Description too short.");
        }

        if (likes < 0 || dislikes < 0) {
            throw new IllegalArgumentException("Likes and dislikes cannot be negative.");
        }

        if (startTime.isBefore(LocalDateTime.now()) || endTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start and end time cannot be before now.");
        }

        if (endTime.isBefore(startTime.plusMinutes(30))) {
            throw new IllegalArgumentException("Event duration must be at least 30 minutes.");
        }

        comments = comments == null ? List.of() : new ArrayList<>(comments);

    }

    /**
     * Creează o instanță nouă a evenimentului cu un număr actualizat de like-uri.
     *
     * @param likes noul număr de like-uri
     * @return o copie a evenimentului actualizat
     */
    @Override
    public Event withLikes(int likes) {
        return this.toBuilder()
                .likes(likes)
                .build();
    }

    /**
     * Creează o instanță nouă a evenimentului cu un număr actualizat de dislike-uri.
     *
     * @param dislikes noul număr de dislike-uri
     * @return o copie a evenimentului actualizat
     */
    @Override
    public Event withDislikes(int dislikes) {
        return this.toBuilder()
                .dislikes(dislikes)
                .build();
    }

    /**
     * Creează o instanță nouă a evenimentului cu lista actualizată de comentarii.
     *
     * @param comments lista comentariilor noi
     * @return o copie a evenimentului actualizat
     */
    @Override
    public Event withComments(List<Comment> comments) {
        return this.toBuilder()
                .comments(comments)
                .build();
    }

    /**
     * Verifică dacă evenimentul este finalizat în raport cu timpul curent.
     *
     * @return true dacă endTime este în trecut
     */
    public boolean isFinished() {
        return LocalDateTime.now().isAfter(this.endTime);
    }

    /**
     * Verifică dacă evenimentul este finalizat în raport cu un timp extern.
     *
     * @param now timpul de referință
     * @return true dacă `now` este după `endTime`
     */
    public boolean isFinished(LocalDateTime now) {
        return now.isAfter(this.endTime);
    }
}
