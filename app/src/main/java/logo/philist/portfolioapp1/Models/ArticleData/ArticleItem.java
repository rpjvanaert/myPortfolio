package logo.philist.portfolioapp1.Models.ArticleData;

public class ArticleItem {

    private String title;
    private String description;
    private String picture;
    private String article_url;

    public ArticleItem(String title, String description, String picture, String article_url) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.article_url = article_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public String getArticle_url() {
        return article_url;
    }
}
