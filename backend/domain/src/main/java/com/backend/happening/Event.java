package com.backend.happening;

import com.backend.happening.metadata.EventMetadata;
import com.backend.shared.SentimentEngagement;
import com.backend.user.Comment;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a public event reported by a user.
 * Includes core details such as title, description, location, time range, user reactions, and comments.
 */
public record Event(
        @NonNull String title,
        @NonNull String description,
        List<Comment> comments,
        SentimentEngagement sentimentEngagement,
        EventMetadata metadata) implements Happening {

    /**
     * Constructs an {@code Event} with validation logic for input values.
     *
     * @throws IllegalArgumentException if:
     *   @param title is shorter than 10 characters
     *   @param description is shorter than 10 characters
     */
    public Event {
        if (title.length() < 10)
            throw new IllegalArgumentException("Title too short.");

        if (description.length() < 10)
            throw new IllegalArgumentException("Description too short.");

        comments = comments == null ? List.of() : new ArrayList<>(comments);
    }

    /**
     * Creates a builder initialized with this {@code Event}'s data.
     * Useful for creating modified copies.
     *
     * @return A new {@code Builder} instance with pre-filled values.
     */
    @Override
    public Builder toBuilder() {
        return new Builder()
                .title(title)
                .description(description)
                .comments(comments)
                .metadata(metadata)
                .sentimentEngagement(sentimentEngagement);
    }

    /**
     * Builder class for constructing {@link Event} instances.
     * Inherits common fields and methods from {@link Happening.Builder}.
     */
    public static class Builder extends Happening.Builder<Builder> {

        /**
         * Returns the current builder instance.
         * Required for fluent API and proper type inference.
         *
         * @return this builder.
         */
        @Override
        Builder self() {
            return (Builder) this;
        }

        /**
         * Sets the event title.
         *
         * @param aTitle the title to set.
         * @return this builder.
         */
        @Override
        public Builder title(String aTitle) {
            this.title = aTitle;
            return self();
        }

        /**
         * Sets the event description.
         *
         * @param aDescription the description to set.
         * @return this builder.
         */
        @Override
        public Builder description(String aDescription) {
            this.description = aDescription;
            return self();
        }

        /**
         * Sets the event comments.
         *
         * @param aComments the list of comments to set.
         * @return this builder.
         */
        @Override
        public Builder comments(List<Comment> aComments) {
            this.comments = aComments;
            return self();
        }

        /**
         * Sets the metadata specific to this {@code Event}.
         *
         * @param aEventMetadata the metadata to set.
         * @return this builder.
         */
        public Builder metadata(EventMetadata aEventMetadata) {
            this.metadata = aEventMetadata;
            return self();
        }

        /**
         * Sets sentiment engagement information.
         *
         * @param aSentimentEngagement the sentiment engagement to set.
         * @return this builder.
         */
        @Override
        public Builder sentimentEngagement(SentimentEngagement aSentimentEngagement) {
            this.sentimentEngagement = aSentimentEngagement;
            return self();
        }

        /**
         * Constructs a new {@link Event} using the current builder state.
         *
         * @return a new {@code Event} instance.
         */
        @Override
        public Event build() {
            return new Event(
                    title,
                    description,
                    comments,
                    sentimentEngagement,
                    (EventMetadata) metadata);
        }
    }

    /**
     * Adds a new comment to the event.
     *
     * @param comment the {@code Comment} to be added.
     * @return a new {@code Event} instance with the comment appended.
     */
    @Override
    public Event addComment(Comment comment) {
        List<Comment> commentsCopy = new ArrayList<>(comments());
        commentsCopy.add(comment);
        return toBuilder()
                .comments(commentsCopy)
                .build();
    }
}
