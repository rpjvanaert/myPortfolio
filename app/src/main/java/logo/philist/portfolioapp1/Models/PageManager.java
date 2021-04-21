package logo.philist.portfolioapp1.Models;

public class PageManager {
    private int pageIndex;

    public PageManager(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
