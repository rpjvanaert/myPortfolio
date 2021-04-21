package logo.philist.portfolioapp1.Models.ArticleData;

import java.util.ArrayList;
import java.util.List;

import logo.philist.portfolioapp1.Models.ViewData.ViewData;

public class Article {

    private String title;
    private List<ViewData> viewData;

    public Article(String title) {
        this.title = title;
        this.viewData = new ArrayList<>();
    }

    public void addViewData(ViewData viewData) {
        this.viewData.add(viewData);
    }

    public String getTitle() {
        return title;
    }

    public List<ViewData> getViewData() {
        return viewData;
    }
}
