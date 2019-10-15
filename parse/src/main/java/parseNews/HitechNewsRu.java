package parseNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public class HitechNewsRu {
    private final String url = "https://hitech.newsru.com";

    private final String NEWS_CSS_QUERY = "div.index-news-item";
    private final String TITLE_CSS_QUERY = "a.index-news-title";
    private final String PREVIEW_TEXT_CSS_QUERY = "a.index-news-text";
    private final String LINK_TO_IMAGE_CSS_QUERY = "div.index-news-image img";


    public void parsePage() {

    }

    public boolean searchNews() {
        return false;
    }

    public String getNews() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public String getPreviewText() {
        return null;
    }

    public String getLinkToNews() {
        return null;
    }

    public String getUrl() {
        return null;
    }

    public String getUrlArchive() {
        return null;
    }
}
