package logo.philist.portfolioapp1.Models.ArticleData;

public class ArticleContent {

    private String type;
    private String content;
    private String style;

    public ArticleContent(String type, String content, String style) {
        this.type = type;
        this.content = content;
        this.style = style;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getStyle() {
        return style;
    }
}
