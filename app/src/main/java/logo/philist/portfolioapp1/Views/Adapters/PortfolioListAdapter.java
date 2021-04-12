package logo.philist.portfolioapp1.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import logo.philist.portfolioapp1.Views.Adapters.OnItemClickListener;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import logo.philist.portfolioapp1.Models.ArticleItem;
import logo.philist.portfolioapp1.R;

public class PortfolioListAdapter extends RecyclerView.Adapter<PortfolioListAdapter.ViewHolder> {

    private List<ArticleItem> articleItems;
    private Context context;
    private OnItemClickListener clickListener;

    public PortfolioListAdapter(List<ArticleItem> articleTumbnails, Context context, OnItemClickListener clickListener){
        this.articleItems = articleTumbnails;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.articleItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MaterialTextView textViewTitle;
        private MaterialTextView textViewDescription;
        private ImageView imageViewPreview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            textViewTitle = itemView.findViewById(R.id.textView_previewArticleTitle);
            textViewDescription = itemView.findViewById(R.id.textView_previewArticleDescription);
            imageViewPreview = itemView.findViewById(R.id.imageView_previewArticle);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            clickListener.onItemClick(clickedPosition);
        }
    }
}
