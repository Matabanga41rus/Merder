package parseNews;

import java.io.IOException;

public interface CopyParseNews {
    void parseArchive();
    boolean searchNews();
    String getNews();
    String getTitle();
    String getPreviewText();
    String getLinkToNews();
    String getUrl();
}
