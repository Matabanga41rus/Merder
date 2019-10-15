package parseNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public final class CopyHitechFmRu implements CopyParseNews {
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

    private String tags;

    public CopyHitechFmRu(){
        parseArchive();
    }

    public CopyHitechFmRu(String tags){
        parseArchive();
        this.tags = tags;
    }


    public void parseArchive() {

        try {
            pageArchive = Jsoup.parse(new URL(urlArchive), TIMEOUT);
            titlePublishedNews = selectTitleFirstNews();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePageNews() {
        try {
            pageNews = Jsoup.parse(new URL(linkToNews), TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean searchNews() {
        String titleAddedNews = selectTitleFirstNews();

        if(!titleAddedNews.equals(titlePublishedNews)) {
            titlePublishedNews = titleAddedNews;

            linkToNews = selectLinkToNews();

            parsePageNews();

            title = selectTitle();
            previewText = selectPreviewText();

            if(checkAvaililabilityTags()){
                if(searchTagsInArticle()) {
                    return true;
                } else { return false;}

            } else{ return false;}

        } else{ return false; }
    }

    public boolean copySearchNews(){
        String titleAddedNews = selectTitleFirstNews();

        if(titleAddedNews.equals(titlePublishedNews)){
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAvaililabilityTags(){
        if(tags.length() > 1 ){
            return true;
        }
        return false;
    }

    private boolean searchTagsInArticle(){
        for(String tag:tags.split("\\s")){
            if(title.contains(tag) || previewText.contains(tag)){
                return true;
            }
        }

        return false;
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

