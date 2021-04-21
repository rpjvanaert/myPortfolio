package logo.philist.portfolioapp1.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import logo.philist.portfolioapp1.Models.ArticleData.Article;
import logo.philist.portfolioapp1.Models.AssetManager;
import logo.philist.portfolioapp1.R;
import logo.philist.portfolioapp1.Views.Adapters.ProjectListingAdapter;

public class ProjectActivity extends AppCompatActivity {

    public static final String PROJECT_FILE_TAG = "PROJECT_FILE_PATH";

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        Intent intent = getIntent();

        article = AssetManager.getArticle(this, intent.getStringExtra(PROJECT_FILE_TAG));
        ProjectListingAdapter adapter = new ProjectListingAdapter(this, article.getViewData());
        RecyclerView recyclerView = findViewById(R.id.recyclerView_project);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}