package parseNews;

import com.sun.istack.internal.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

import static com.sun.jmx.snmp.ThreadContext.contains;

public final class HitechFmRu {
    private final String urlArchive = "https://hightech.fm/archive";
    private final int TIMEOUT = 10*1000;

    private final String FIRST_NEWS_CSS_QUERY = "div.archive--wrap ul.data-archive li a";

    private final String TITLE_CSS_QUERY = "div.hero-main__info h1";
    private final String PREVIEW_TEXT_CSS_QUERY = "p[class^='lead page-content__lead']";
    private final String LINK_TO_IMAGE_CSS_QUERY = "div.hero-main__item imj [src]";

    private Document pageArchive = null;
    private Document pageNews = null;

    private String titlePublishedNews = null;


    public HitechFmRu() throws IOException {
        parseArchive();
        titlePublishedNews = selectTitleFirstNewsInArchive();
    }

    public void parseArchive() throws IOException {
        pageArchive = Jsoup.parse(new URL(urlArchive), TIMEOUT);
    }

    public void parsePageNews() throws IOException {
        pageNews = Jsoup.parse(new URL(selectLinkToNews()), TIMEOUT);
    }


    public boolean searchNews() {
        String titleNews = selectTitleFirstNewsInArchive();

        if (titleNews.equals(titlePublishedNews)) {
            return false;
        }

        titlePublishedNews = titleNews;

        return true;
    }

    public boolean searchNews(@NotNull String tags) {
        String titleNews = selectTitleFirstNewsInArchive();

        if (titleNews.equals(titlePublishedNews) | !searchTagsInNews(tags)) {
            return false;
        }

        titlePublishedNews = titleNews;

        return true;
    }

    public News getNews() throws IOException {
        return new News(selectTitle(), selectPreviewText(), selectLinkToNews());
    }


    private boolean searchTagsInNews(String tags) {
        String title = selectTitle();
        String text = selectPreviewText();

        for (String tag : tags.split("\\s")) {
            if (title.contains(tag) || text.contains(tag)) {
                return true;
            }
        }

        return false;
    }

    private String selectTitleFirstNewsInArchive() {
        return pageArchive.selectFirst(FIRST_NEWS_CSS_QUERY).text();
    }

    private String selectTitle(){
        return pageNews.select(TITLE_CSS_QUERY).text();
    }
    private String selectPreviewText(){
        return pageNews.select(PREVIEW_TEXT_CSS_QUERY).text();
    }
    private String selectLinkToNews(){
        return pageArchive.selectFirst(FIRST_NEWS_CSS_QUERY).attr("href");
    }
    private String selectLinkToImage(){
        return pageNews.select(LINK_TO_IMAGE_CSS_QUERY).text();
    }
}