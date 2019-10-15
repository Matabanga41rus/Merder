
package parseNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public final class HitechFmRu implements ParseNews {
    private final String urlArchive = "https://hightech.fm/archive";
    private final String url = "https://hightech.fm";
    private final int TIMEOUT = 10*1000;

    private final String FIRST_NEWS_CSS_QUERY = "div.archive--wrap ul.data-archive li a";

    private final String TITLE_CSS_QUERY = "div.hero-main__info h1";
    private final String PREVIEW_TEXT_CSS_QUERY = "p[class^='lead page-content__lead']";
    private final String LINK_TO_IMAGE_CSS_QUERY = "div.hero-main__item imj [src]";

    private Document pageArchive = null;
    private Document pageNews = null;

    private String titlePublishedNews = null;

    private String title = null;
    private String previewText = null;
    private String linkToNews = null;

    public HitechFmRu(){
        try {
            parsePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parsePage() throws IOException {
        pageArchive = Jsoup.parse(new URL(urlArchive), TIMEOUT);
        titlePublishedNews = selectTitleFirstNews();
    }

    private void parsePageNews() throws IOException{
        pageNews = Jsoup.parse(new URL(linkToNews), TIMEOUT);
    }


    public boolean searchNews(String tags) {
        String titleAddedNews = selectTitleFirstNews();

        if(!titleAddedNews.equals(titlePublishedNews)) {
            titlePublishedNews = titleAddedNews;

            linkToNews = selectLinkToNews();
            try {
                parsePageNews();
            } catch (IOException e) {
                e.printStackTrace();
            }

            title = selectTitle();
            previewText = selectPreviewText();

            if(tags.length() < 2 ){
                return true;
            } else{
                for(String tag:tags.split("\\s")){
                    if(title.contains(tag) || previewText.contains(tag)){
                        return true;
                    }
                }
                return false;
            }
        } else { return false; }


    }



    private String selectTitleFirstNews(){
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



    public String getNews() {
        return title + " \n\n" + previewText  + "\n" + linkToNews;
    }

    public String getTitle() {
        return title;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getLinkToNews() {
        return linkToNews;
    }

    public String getUrl() {
        return url;
    }
}