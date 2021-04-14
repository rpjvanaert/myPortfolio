package logo.philist.portfolioapp1.Models.ArticleData;

import java.util.ArrayList;
import java.util.List;

public class Article {

    private String title;
    private List<ArticleContent> articleContent;

    public Article(String title) {
        this.title = title;
        this.articleContent = new ArrayList<>();
    }

    public void addArticleContent(ArticleContent articleContent) {
        this.articleContent.add(articleContent);
    }

    public String getTitle() {
        return title;
    }

    public List<ArticleContent> getArticleContent() {
        return articleContent;
    }
}
