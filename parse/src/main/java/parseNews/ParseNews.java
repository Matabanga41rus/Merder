package parseNews;

import java.io.IOException;

public interface ParseNews {
    void parsePage() throws IOException;
    boolean searchNews(String tags);
    String getNews();
    String getTitle();
    String getPreviewText();
    String getLinkToNews();
    String getUrl();
}
