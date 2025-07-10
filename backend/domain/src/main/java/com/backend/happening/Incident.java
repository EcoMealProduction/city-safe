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
 * Reprezintă o întâmplare de tip incident raportată de un utilizator.
 * Fiecare incident are un titlu, o descriere, locație, autor, timp de expirare și statistici de reacții.
 */
@Builder(toBuilder = true)
public record Incident(
        @NonNull UUID id,
        @NonNull String title,
        @NonNull String description,
        @NonNull User user,
        @NonNull Location location,
        LocalDateTime createdAt,
        LocalDateTime expirationTime,
        List<Comment> comments,
        int likes,
        int dislikes,
        int confirms,
        int denies,
        int consecutiveDenies
) implements Happening{

    /**
     * Constructor cu validări interne pentru titlu, descriere, reacții și timp.
     */
    public Incident {
        if (title.length() < 10)
            throw new IllegalArgumentException("Title too short.");

        if (description.length() < 20)
            throw new IllegalArgumentException("Description too short.");

        if (likes < 0 || dislikes < 0 || confirms < 0 || denies < 0 || consecutiveDenies < 0) {
            throw new IllegalArgumentException("Negative values not allowed.");
        }

        if (createdAt == null)
            createdAt = LocalDateTime.now();

        // Setăm timp de expirare implicit dacă nu este specificat
        if (expirationTime == null)
            expirationTime = createdAt.plusMinutes(30);

        if (expirationTime.isBefore(createdAt)) {
            throw new IllegalArgumentException("Expiration time cannot be before createdAt.");
        }
        if (expirationTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiration time cannot be before now.");
        }

        comments = comments == null ? List.of() : new ArrayList<>(comments);

    }

    /**
     * Verifică dacă incidentul trebuie șters.
     * Se consideră valid pentru ștergere dacă a expirat sau are 3 sau mai multe deny-uri consecutive.
     *
     * @return true dacă trebuie șters, altfel false
     */
    public boolean shouldBeDeleted() {
            return consecutiveDenies >=3 || isExpired();
        }

    /**
     * Verifică dacă incidentul ar trebui șters la un moment dat specific.
     *
     * @param now momentul curent
     * @return true dacă are 3 sau mai multe deny-uri consecutive sau a expirat până la acel moment
     */
    public boolean shouldBeDeleted(LocalDateTime now) {
        return consecutiveDenies >= 3 || isExpired(now);
    }

    /**
     * Adaugă o confirmare și extinde timpul de expirare cu 5 minute. Resetează deny-urile consecutive.
     *
     * @return noul obiect Incident actualizat
     */
    public Incident addConfirm() {
        return this.toBuilder()
                .confirms(confirms + 1)
                .expirationTime(expirationTime.plusMinutes(5))
                .consecutiveDenies(0)
                .build();
    }

    /**
     * Adaugă un deny și crește contorul de deny-uri consecutive.
     *
     * @return noul obiect Incident actualizat
     */
    public Incident addDeny() {
        return this.toBuilder()
                .denies(denies + 1)
                .consecutiveDenies(consecutiveDenies + 1)
                .build();
    }

    /**
     * Verifică dacă incidentul a expirat în raport cu timpul actual.
     *
     * @return true dacă timpul curent este după expirationTime
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    /**
     * Verifică dacă incidentul a expirat în raport cu un timp dat.
     *
     * @param now timpul de comparație
     * @return true dacă now este după expirationTime
     */
    public boolean isExpired(LocalDateTime now) {
        return now.isAfter(expirationTime);
    }

    /**
     * Creează o copie a incidentului cu un număr actualizat de like-uri.
     *
     * @param likes noul număr de like-uri
     * @return noul obiect Incident
     */
    @Override
    public Happening withLikes(int likes) {
        return this.toBuilder()
                .likes(likes)
                .build();
    }

    /**
     * Creează o copie a incidentului cu un număr actualizat de dislike-uri.
     *
     * @param dislikes noul număr de dislike-uri
     * @return noul obiect Incident
     */
    @Override
    public Happening withDislikes(int dislikes) {
        return this.toBuilder()
                .dislikes(dislikes)
                .build();
    }

    /**
     * Creează o copie a incidentului cu lista de comentarii actualizată.
     *
     * @param comments lista nouă de comentarii
     * @return noul obiect Incident
     */
    @Override
    public Happening withComments(List<Comment> comments) {
        return  this.toBuilder()
                .comments(comments)
                .build();
    }
}
