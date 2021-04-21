package logo.philist.portfolioapp1.Views.Adapters;

import android.util.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class YoutubeInitializedListener implements YouTubePlayer.OnInitializedListener{

    public static final String TAG = YoutubeInitializedListener.class.getSimpleName();

    private final String videoId;

    public YoutubeInitializedListener(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo((videoId));
        Log.i(TAG, "onInitializationSuccess");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.e(TAG, "onInitializationFailure");
        Log.e(TAG, youTubeInitializationResult.toString() );
    }
}
