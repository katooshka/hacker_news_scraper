package data;

import java.util.List;

public class PostsAndErrorMessagesContainer {
    private List<Post> posts;
    private List<String> errorMessages;

    public PostsAndErrorMessagesContainer(List<Post> posts, List<String> errorMessages) {
        this.posts = posts;
        this.errorMessages = errorMessages;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostsAndErrorMessagesContainer)) return false;

        PostsAndErrorMessagesContainer container = (PostsAndErrorMessagesContainer) o;

        if (posts != null ? !posts.equals(container.posts) : container.posts != null) return false;
        return errorMessages != null ? errorMessages.equals(container.errorMessages) : container.errorMessages == null;
    }

    @Override
    public int hashCode() {
        int result = posts != null ? posts.hashCode() : 0;
        result = 31 * result + (errorMessages != null ? errorMessages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PostsAndErrorMessagesContainer{" +
                "posts=" + posts +
                ", errorMessages=" + errorMessages +
                '}';
    }
}
