package pranay.example.com.magnusias4;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class VideoAcitvity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_acitvity);
        Intent intent = getIntent();
        String chpater_content_id = intent.getStringExtra("chpater_content_id");
        String chapter_id = intent.getStringExtra("chapter_id");
        String url ="http://magnusias.com/app-api/play-video.php?single="+chpater_content_id+"&multi="+chapter_id;
        webView = (WebView)findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimeType,
                                        long contentLength) {




                try {

                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));


                    request.setMimeType(mimeType);


                    String cookies = CookieManager.getInstance().getCookie(url);


                    request.addRequestHeader("cookie", cookies);


                    request.addRequestHeader("User-Agent", userAgent);


                    request.setDescription("Downloading file...");


                    request.setTitle(URLUtil.guessFileName(url, contentDisposition,
                            mimeType));


                    request.allowScanningByMediaScanner();


                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(
                                    url, contentDisposition, mimeType));
                    DownloadManager dm = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File",
                            Toast.LENGTH_LONG).show();

                }
                catch (Exception e)
                {
                    Log.i("Download Exception",e.toString());

                    ActivityCompat.requestPermissions(VideoAcitvity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }


            }
        });


    }
}
