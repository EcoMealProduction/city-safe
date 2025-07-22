package com.backend.happening;

import com.backend.happening.metadata.Metadata;
import com.backend.shared.SentimentEngagement;
import com.backend.user.Comment;

import java.util.List;

/**
 * Represents a generic reported occurrence within the application (e.g., an {@link Incident} or an {@link Event}).
 * This abstraction defines the common structure and behaviors for all reportable happenings in the system.
 */
public sealed interface Happening permits Event, Incident {

    /**
     * @return The title of the happening. Should be concise but descriptive.
     */
    String title();

    /**
     * @return A detailed textual description of the happening.
     */
    String description();

    /**
     * @return A list of user-submitted comments associated with this happening.
     */
    List<Comment> comments();

    /**
     * @return An Object of metadata associated with this happening.
     */
    Metadata metadata();

    /**
     * @return The number of positive reactions (likes) associated with this happening.
     */
    SentimentEngagement sentimentEngagement();

    /**
     * Adds a user comment to the current happening.
     *
     * @param comment The comment to be added.
     * @return A new instance of the happening with the comment appended.
     */
    Happening addComment(Comment comment);

    /**
     * Creates a builder that is pre-populated with the current object's field value.
     */
    Builder<?> toBuilder();

    /**
     * Abstract builder class for constructing immutable {@link Happening} instances.
     * Utilizes a self-referential generic type to support fluent method chaining in subclasses.
     *
     * @param <T> The type of the builder itself.
     */
    public abstract static class Builder<T extends Builder<T>> {

        protected String title;
        protected String description;
        protected List<Comment> comments;
        protected Metadata metadata;
        protected SentimentEngagement sentimentEngagement;

        /**
         * Should be implemented by subclasses to return 'this' correctly typed.
         * Enables fluent-style method chaining.
         *
         * @return The current builder instance.
         */
        abstract T self();

        /**
         * Sets the title field.
         *
         * @param aTitle The title of the happening.
         * @return The current builder instance.
         */
        public T title(String aTitle) {
            this.title = aTitle;
            return self();
        }

        /**
         * Sets the description field.
         *
         * @param aDescription The detailed description.
         * @return The current builder instance.
         */
        public T description(String aDescription) {
            this.description = aDescription;
            return self();
        }

        /**
         * Sets the comments list.
         *
         * @param aComments The list of user comments.
         * @return The current builder instance.
         */
        public T comments(List<Comment> aComments) {
            this.comments = aComments;
            return self();
        }

        /**
         * Sets the metadata.
         *
         * @param aMetadata Additional information related to the happening.
         * @return The current builder instance.
         */
        public T metadata(Metadata aMetadata) {
            this.metadata = aMetadata;
            return self();
        }

        /**
         * Sets the sentiment engagement.
         *
         * @param aSentimentEngagement User engagement metrics.
         * @return The current builder instance.
         */
        public T sentimentEngagement(SentimentEngagement aSentimentEngagement) {
            this.sentimentEngagement = aSentimentEngagement;
            return self();
        }

        /**
         * Builds the final immutable {@link Happening} object.
         * Subclasses must implement the build logic.
         *
         * @return A fully constructed Happening instance.
         */
        public abstract Happening build();
    }
}
