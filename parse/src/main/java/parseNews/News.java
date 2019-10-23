package parseNews;

public class News {
    private String title = null;
    private String previewText = null;
    private String linkToNews = null;

    public News(){}

    public News(String title, String previewText, String linkToNews) {
        this.title = title;
        this.previewText = previewText;
        this.linkToNews = linkToNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getLinkToNews() {
        return linkToNews;
    }

    public void setLinkToNews(String linkToNews) {
        this.linkToNews = linkToNews;
    }
}
