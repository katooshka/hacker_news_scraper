package scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebPageDownloader {

    /**
     * Downloads content of given URL and returns it in String format.
     *
     * @param url web address
     * @throws RuntimeException if page cannot be reached
     * @return downloaded web page content in String form
     */
    public String downloadWebPage(String url) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            for (String s = br.readLine(); s != null; s = br.readLine()) {
                sb.append(s);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while downloading web page: " + url);
        }
        return sb.toString();
    }
}
