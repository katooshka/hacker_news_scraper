package scraper;

import java.net.URI;

/**
 * Checks if given URI is valid.
 */
public class URIValidator {
    public static boolean isValid(String uri) {
        final URI u;
        try {
            u = new URI(uri);
        } catch (Exception e) {
            return false;
        }
        return "http".equals(u.getScheme()) || "https".equals(u.getScheme());
    }
}
