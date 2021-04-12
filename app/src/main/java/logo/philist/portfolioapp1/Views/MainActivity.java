package logo.philist.portfolioapp1.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import logo.philist.portfolioapp1.Models.AssetManager;
import logo.philist.portfolioapp1.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageViewProfilePicture = findViewById(R.id.imageView_profilePicture);
        this.imageViewProfilePicture.setImageDrawable(AssetManager.getProfilePicture(this));

    }
}