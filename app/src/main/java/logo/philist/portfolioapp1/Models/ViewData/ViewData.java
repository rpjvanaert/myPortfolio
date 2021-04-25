package logo.philist.portfolioapp1.Models.ViewData;

public class ViewData {

    public static final int VIEWTYPE_HEADER = 0;
    public static final int VIEWTYPE_SUB_HEADER = 1;
    public static final int VIEWTYPE_PARAGRAPH = 2;
    public static final int VIEWTYPE_PDF = 3;
    public static final int VIEWTYPE_IMAGE = 4;
    public static final int VIEWTYPE_VIDEO = 5;
    public static final int VIEWTYPE_LINK = 6;

    private int viewType;
    private String content;

    public ViewData(int viewType, String data){
        this.viewType = viewType;
        this.content = data;
    }

    public int getViewType() {
        return viewType;
    }

    public String getContent() {
        return content;
    }

    public static int getStringViewType(String viewType){
        switch (viewType){
            case "header":
                return VIEWTYPE_HEADER;
            case "sub_header":
                return VIEWTYPE_SUB_HEADER;
            case "paragraph":
                return VIEWTYPE_PARAGRAPH;
            case "pdf":
                return VIEWTYPE_PDF;
            case "image":
                return VIEWTYPE_IMAGE;
            case "video":
                return VIEWTYPE_VIDEO;
            case "link":
                return VIEWTYPE_LINK;
        }
        return VIEWTYPE_PARAGRAPH;
    }
}
