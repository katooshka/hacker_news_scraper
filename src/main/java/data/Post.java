package data;

public class Post {
    private String title;
    private String uri;
    private String author;
    private int points;
    private int comments;
    private int rank;

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
