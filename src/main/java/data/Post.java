package data;

public class Post {
    private String title;
    private String uri;
    private String author;
    private int points;
    private int comments;
    private int rank;

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public String getAuthor() {
        return author;
    }

    public int getPoints() {
        return points;
    }

    public int getComments() {
        return comments;
    }

    public int getRank() {
        return rank;
    }

    public static class Builder {
        private String title;
        private String uri;
        private String author;
        private int points;
        private int comments;
        private int rank;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setPoints(int points) {
            this.points = points;
            return this;
        }

        public Builder setComments(int comments) {
            this.comments = comments;
            return this;
        }

        public Builder setRank(int rank) {
            this.rank = rank;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

    private Post(Builder builder) {
        this.title = builder.title;
        this.uri = builder.uri;
        this.author = builder.author;
        this.points = builder.points;
        this.comments = builder.comments;
        this.rank = builder.rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;

        Post post = (Post) o;

        if (points != post.points) return false;
        if (comments != post.comments) return false;
        if (rank != post.rank) return false;
        if (!title.equals(post.title)) return false;
        if (!uri.equals(post.uri)) return false;
        return author.equals(post.author);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + uri.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + points;
        result = 31 * result + comments;
        result = 31 * result + rank;
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", uri='" + uri + '\'' +
                ", author='" + author + '\'' +
                ", points=" + points +
                ", comments=" + comments +
                ", rank=" + rank +
                '}';
    }
}
