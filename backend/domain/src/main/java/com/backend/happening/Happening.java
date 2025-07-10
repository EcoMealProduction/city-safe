package com.backend.happening;

import com.backend.shared.Location;
import com.backend.user.Comment;
import com.backend.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Reprezintă o entitate generală care descrie o întâmplare (incident sau eveniment) raportată de un utilizator.
 * Poate fi extinsă de clase precum {@link Incident} sau {@link Event}.
 */
public interface Happening {

    /**
     * @return ID-ul unic al întâmplării.
     */
    UUID id();

    /**
     * @return Titlul întâmplării.
     */
    String title();

    /**
     * @return Descrierea detaliată a întâmplării.
     */
    String description();

    /**
     * @return Utilizatorul care a raportat întâmplarea.
     */
    User user();

    /**
     * @return Locația unde s-a produs întâmplarea.
     */
    Location location();

    /**
     * @return Lista de comentarii asociate întâmplării.
     */
    List<Comment> comments();

    /**
     * @return Numărul de aprecieri pozitive.
     */
    int likes();

    /**
     * @return Numărul de aprecieri negative.
     */
    int dislikes();

    /**
     * Creează o nouă instanță a întâmplării cu un nou număr de like-uri.
     *
     * @param likes noua valoare a like-urilor
     * @return instanța actualizată
     */
    Happening withLikes(int likes);

    /**
     * Creează o nouă instanță a întâmplării cu un nou număr de dislike-uri.
     *
     * @param dislikes noua valoare a dislike-urilor
     * @return instanța actualizată
     */
    Happening withDislikes(int dislikes);

    /**
     * Creează o nouă instanță a întâmplării cu o listă nouă de comentarii.
     *
     * @param comments lista actualizată de comentarii
     * @return instanța actualizată
     */
    Happening withComments(List<Comment> comments);

    /**
     * Adaugă un like la întâmplare.
     *
     * @return instanța actualizată cu un like în plus
     */
    default Happening addLike() {
        return withLikes(likes() + 1);
    }

    /**
     * Adaugă un dislike la întâmplare.
     *
     * @return instanța actualizată cu un dislike în plus
     */
    default Happening addDislike() {
        return withDislikes(dislikes() + 1);
    }

    /**
     * Elimină un like de la întâmplare.
     *
     * @return instanța actualizată cu un like în minus
     */
    default Happening removeLike() {
        return withLikes(likes() - 1);
    }

    /**
     * Elimină un dislike de la întâmplare.
     *
     * @return instanța actualizată cu un dislike în minus
     */
    default Happening removeDislike() {
        return withDislikes(dislikes() - 1);
    }

    /**
     * Adaugă un comentariu nou la întâmplare.
     *
     * @param comment comentariul de adăugat
     * @return instanța actualizată cu comentariul adăugat
     */
    default Happening addComment(Comment comment) {
        List<Comment> commentsCopy = new ArrayList<>(comments());
        commentsCopy.add(comment);
        return withComments(commentsCopy);
    }
}
