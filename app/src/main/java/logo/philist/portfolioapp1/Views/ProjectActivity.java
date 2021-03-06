package logo.philist.portfolioapp1.Views;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;

import logo.philist.portfolioapp1.Models.ArticleData.Article;
import logo.philist.portfolioapp1.Models.ResourceManager;
import logo.philist.portfolioapp1.R;
import logo.philist.portfolioapp1.Views.Adapters.ArticleItemsAdapter;

public class ProjectActivity extends YouTubeBaseActivity {

    public static final String PROJECT_FILE_TAG = "PROJECT_FILE_PATH";

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Intent intent = getIntent();

        article = ResourceManager.getArticle(this, intent.getStringExtra(PROJECT_FILE_TAG));
        ArticleItemsAdapter adapter = new ArticleItemsAdapter(this, article.getViewData());
        RecyclerView recyclerView = findViewById(R.id.recyclerView_project);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}