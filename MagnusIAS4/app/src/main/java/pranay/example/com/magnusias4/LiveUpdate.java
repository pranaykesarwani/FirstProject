package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class LiveUpdate extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView,youTubePlayerView1,youTubePlayerView2;
    YouTubePlayer.OnInitializedListener onInitializedListener,onInitializedListener1,onInitializedListener2;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_updates);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Live Updates");
       // setSupportActionBar(toolbar);
            youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeView1);
            youTubePlayerView1 = (YouTubePlayerView)findViewById(R.id.youtubeView2);
            youTubePlayerView2 = (YouTubePlayerView)findViewById(R.id.youtubeView3);
            onInitializedListener1 = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo("sxsFOnuU8eU");



                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(LiveUpdate.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
                }

            };
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo("u2HByEX6pNE");


                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(LiveUpdate.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
                }
            };

            onInitializedListener2 = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo("EVybJHJGHYU");


                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(LiveUpdate.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
                }
            };

            youTubePlayerView.initialize(YoutuberConfig.getApiKey(),onInitializedListener);
            youTubePlayerView1.initialize(YoutuberConfig.getApiKey(),onInitializedListener1);
            youTubePlayerView2.initialize(YoutuberConfig.getApiKey(),onInitializedListener2);




        }

}
