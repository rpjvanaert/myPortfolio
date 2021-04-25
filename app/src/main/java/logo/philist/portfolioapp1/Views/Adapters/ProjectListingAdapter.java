package logo.philist.portfolioapp1.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import logo.philist.portfolioapp1.Models.ResourceManager;
import logo.philist.portfolioapp1.Models.RestrictedData;
import logo.philist.portfolioapp1.Models.ViewData.ViewData;
import logo.philist.portfolioapp1.R;

import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_HEADER;
import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_IMAGE;
import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_LINK;
import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_PARAGRAPH;
import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_PDF;
import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_SUB_HEADER;
import static logo.philist.portfolioapp1.Models.ViewData.ViewData.VIEWTYPE_VIDEO;

public class ProjectListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = ProjectListingAdapter.class.getSimpleName();

    private Context context;
    private List<ViewData> viewData;

    public ProjectListingAdapter(Context context, List<ViewData> viewData){
        this.context = context;
        this.viewData = viewData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case VIEWTYPE_HEADER:
                return new ViewHolderHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_header, parent, false));

            case VIEWTYPE_SUB_HEADER:
                return new ViewHolderSubHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_sub_header, parent, false));

            case VIEWTYPE_PARAGRAPH:
                return new ViewHolderParagraph(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_paragraph, parent, false));

            case VIEWTYPE_PDF:
                return new ViewHolderPdf(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_pdf, parent, false));

            case VIEWTYPE_IMAGE:
                return new ViewHolderImage(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_image, parent, false));

            case VIEWTYPE_VIDEO:
                return new ViewHolderVideo(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_video, parent, false));
            case VIEWTYPE_LINK:
                return new ViewHolderLink(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_link, parent, false));

            default:
                throw new IllegalStateException("Unexpected view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewData data = viewData.get(position);
        switch (data.getViewType()){
            case VIEWTYPE_HEADER:
                ((ViewHolderHeader)holder).textViewHeader.setText(data.getContent());
                break;

            case VIEWTYPE_SUB_HEADER:
                ((ViewHolderSubHeader)holder).textViewSubHeader.setText(data.getContent());
                break;

            case VIEWTYPE_PARAGRAPH:
                ((ViewHolderParagraph)holder).textViewParagraph.setText(data.getContent());
                break;

            case VIEWTYPE_PDF:
                ParcelFileDescriptor fileDescriptor = null;
                ViewHolderPdf pdfHolder = ((ViewHolderPdf)holder);
                try {
                    fileDescriptor = ParcelFileDescriptor.open(
                            ResourceManager.getFile(context, data.getContent()),
                            ParcelFileDescriptor.MODE_READ_ONLY
                    );

                    PdfRenderer renderer = new PdfRenderer(fileDescriptor);
                    PdfRenderer.Page page = renderer.openPage(pdfHolder.index);
                    Bitmap bitmap = Bitmap.createBitmap(
                            page.getWidth(),
                            page.getHeight(),
                            Bitmap.Config.ARGB_8888
                    );

                    page.render(bitmap,
                            null,
                            null,
                            PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                            );

                    pdfHolder.imageViewPdf.setImageBitmap(bitmap);
                    pdfHolder.textViewCurrentPage.setText((pdfHolder.index + 1) + "/" + renderer.getPageCount());

                    pdfHolder.buttonNext.setOnClickListener(view -> {
                        pdfHolder.index += 1;
                        if (pdfHolder.index >= renderer.getPageCount()) {
                            pdfHolder.index = 0;
                        } else if (pdfHolder.index < 0) {
                            pdfHolder.index = renderer.getPageCount() - 1;
                        }

                        Log.i(TAG, "pdfOnclick page nr.: " + renderer.getPageCount() + " to page " + pdfHolder.index);

                        PdfRenderer.Page pagePrev = renderer.openPage(pdfHolder.index);
                        pdfHolder.textViewCurrentPage.setText(pdfHolder.index + "/" + renderer.getPageCount());

                        Bitmap bitmapPage = Bitmap.createBitmap(
                                pagePrev.getWidth(),
                                pagePrev.getHeight(),
                                Bitmap.Config.ARGB_8888
                        );

                        pagePrev.render(bitmap,
                                null,
                                null,
                                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                        );

                        pdfHolder.imageViewPdf.setImageBitmap(bitmap);
                        pagePrev.close();
                    });

                    pdfHolder.buttonPrevious.setOnClickListener(view -> {
                        pdfHolder.index += -1;
                        if (pdfHolder.index >= renderer.getPageCount()) {
                            pdfHolder.index = 0;
                        } else if (pdfHolder.index < 0) {
                            pdfHolder.index = renderer.getPageCount() - 1;
                        }

                        Log.i(TAG, "pdfOnclick page nr.: " + renderer.getPageCount() + " to page " + pdfHolder.index);

                        PdfRenderer.Page pagePrev = renderer.openPage(pdfHolder.index);
                        pdfHolder.textViewCurrentPage.setText((pdfHolder.index + 1) + "/" + renderer.getPageCount());

                        Bitmap bitmapPage = Bitmap.createBitmap(
                                pagePrev.getWidth(),
                                pagePrev.getHeight(),
                                Bitmap.Config.ARGB_8888
                        );

                        pagePrev.render(bitmap,
                                null,
                                null,
                                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                        );

                        pdfHolder.imageViewPdf.setImageBitmap(bitmap);
                        pagePrev.close();
                    });
                    page.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case VIEWTYPE_IMAGE:
                ((ViewHolderImage)holder).imageViewImage.setImageDrawable(ResourceManager.getAssetsImageDrawable(data.getContent(),context));
                break;

            case VIEWTYPE_VIDEO:
                ViewHolderVideo viewHolderVideo = (ViewHolderVideo) holder;
                viewHolderVideo.youTubePlayerView.initialize(
                        RestrictedData.youtubeDataApiKey,
                        new YoutubeInitializedListener(viewData.get(position).getContent())
                );
                break;
            case VIEWTYPE_LINK:
                ViewHolderLink viewHolderLink = (ViewHolderLink) holder;
                String[] seperatedString = data.getContent().split("\\|");
                viewHolderLink.linkButton.setText(seperatedString[0]);
                viewHolderLink.linkButton.setOnClickListener(view -> {
                    Intent linkBrowseIntent = new Intent();
                    linkBrowseIntent.setAction(Intent.ACTION_VIEW);
                    linkBrowseIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                    linkBrowseIntent.setData(Uri.parse(seperatedString[1]));
                    context.startActivity(linkBrowseIntent);
                });
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + viewData.get(position).getViewType());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewData.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return viewData.size();
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewHeader;

        public ViewHolderHeader(@NonNull View itemView){
            super(itemView);

            textViewHeader = itemView.findViewById(R.id.textView_project_item_header);
        }
    }

    private class ViewHolderSubHeader extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewSubHeader;

        public ViewHolderSubHeader(@NonNull View itemView){
            super(itemView);

            textViewSubHeader = itemView.findViewById(R.id.textView_project_item_sub_header);
        }
    }

    private class ViewHolderParagraph extends RecyclerView.ViewHolder {

        private final MaterialTextView textViewParagraph;

        public ViewHolderParagraph(@NonNull View itemView){
            super(itemView);

            textViewParagraph = itemView.findViewById(R.id.textView_project_item_paragraph);
        }
    }

    private class ViewHolderPdf extends RecyclerView.ViewHolder {

        private final ImageView imageViewPdf;
        private final TextView textViewCurrentPage;
        private final MaterialButton buttonPrevious;
        private final MaterialButton buttonNext;
        private int index;

        public ViewHolderPdf(@NonNull View itemView){
            super(itemView);

            imageViewPdf = itemView.findViewById(R.id.imageView_project_item_pdfViewer);
            textViewCurrentPage = itemView.findViewById(R.id.textView_currentPagePdf);
            buttonPrevious = itemView.findViewById(R.id.button_project_item_previous);
            buttonNext = itemView.findViewById(R.id.button_project_item_next);
            index = 0;
        }
    }

    private class ViewHolderImage extends RecyclerView.ViewHolder {

        private final ImageView imageViewImage;

        public ViewHolderImage(@NonNull View itemView){
            super(itemView);

            imageViewImage = itemView.findViewById(R.id.imageView_project_item_image);
        }
    }

    private class ViewHolderVideo extends RecyclerView.ViewHolder {

        private final YouTubePlayerView youTubePlayerView;

        public ViewHolderVideo(@NonNull View itemView){
            super(itemView);

            this.youTubePlayerView = itemView.findViewById(R.id.youtubeView_project_item_video);
        }
    }

    private class ViewHolderLink extends RecyclerView.ViewHolder {

        private final MaterialButton linkButton;

        public ViewHolderLink(@NonNull View itemView){
            super(itemView);

            this.linkButton = itemView.findViewById(R.id.button_project_item_link);
        }
    }
}
