package com.backend.shared;

import lombok.Builder;

/**
 * Represents basic sentiment engagement metrics for a {@link Happening},
 * including the number of likes and dislikes submitted by users.
 */
@Builder(toBuilder = true)
public record SentimentEngagement(int likes, int dislikes) {

    public SentimentEngagement {
        if (likes < 0 || dislikes < 0)
            throw new IllegalArgumentException("Likes and dislikes cannot be negative.");
    }

    /**
     * Increments the like count.
     *
     * @return a new {@code Event} instance with one more like.
     */
    public SentimentEngagement addLike() {
        return toBuilder()
                .likes(likes + 1)
                .build();
    }

    /**
     * Increments the dislike count.
     *
     * @return a new {@code Event} instance with one more dislike.
     */
    public SentimentEngagement addDislike() {
        return toBuilder()
                .dislikes(dislikes + 1)
                .build();
    }

    /**
     * Decrements the like count.
     *
     * @return a new {@code Event} instance with one less like.
     */
    public SentimentEngagement removeLike() {
        return toBuilder()
                .likes(likes - 1)
                .build();
    }

    /**
     * Decrements the dislike count.
     *
     * @return a new {@code Event} instance with one less dislike.
     */
    public SentimentEngagement removeDislike() {
        return toBuilder()
                .dislikes(dislikes - 1)
                .build();
    }
}
