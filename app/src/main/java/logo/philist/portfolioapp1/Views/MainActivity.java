package logo.philist.portfolioapp1.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.List;

import logo.philist.portfolioapp1.Models.ArticleData.ArticleItem;
import logo.philist.portfolioapp1.Models.AssetManager;
import logo.philist.portfolioapp1.R;
import logo.philist.portfolioapp1.Views.Adapters.OnItemClickListener;
import logo.philist.portfolioapp1.Views.Adapters.PortfolioListAdapter;

import static logo.philist.portfolioapp1.Models.AssetManager.PROFILE_PICTURE_FILE;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private ImageView imageViewProfilePicture;
    private RecyclerView recyclerViewArticles;
    private List<ArticleItem> articleItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageViewProfilePicture = findViewById(R.id.imageView_profilePicture);
        this.imageViewProfilePicture.setImageDrawable(AssetManager.getAssetsImageDrawable(PROFILE_PICTURE_FILE,this));

        this.recyclerViewArticles = findViewById(R.id.recyclerView_articles);
        this.recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));
        this.articleItems = AssetManager.getArticleItems(this);
        PortfolioListAdapter portfolioListAdapter = new PortfolioListAdapter(articleItems, this, this);
        this.recyclerViewArticles.setAdapter(portfolioListAdapter);
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
        intent.putExtra(ProjectActivity.PROJECT_FILE_TAG, articleItems.get(clickedPosition).getArticle_url());
        startActivity(intent);
    }
}