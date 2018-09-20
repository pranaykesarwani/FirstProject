package pranay.example.com.magnusias4;

import android.Manifest;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.content.Context.DOWNLOAD_SERVICE;

//import com.vimeo.networking.Vimeo;

public class VideoFragment extends Fragment {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    WebView webView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        Bundle bundle = getArguments();
        String chpater_content_id = bundle.getString("chpater_content_id");
        String chapter_id = bundle.getString("chapter_id");
        String url = bundle.getString("url");

       // Toast.makeText(getActivity(), chpater_content_id+" "+chapter_id , Toast.LENGTH_SHORT).show();
        Log.i("IDs",chpater_content_id+" "+chapter_id);
        webView = (WebView)view.findViewById(R.id.webview);
        //WebSettings settings = webView.getSettings();
        //settings.setDomStorageEnabled(true);
        //url="https://drive.google.com/file/d/1G3lyBnynZBw7rYMoKXluNr-zpnWrrUQb/view";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        if (url==null)
        {
            webView.loadUrl("http://magnusias.com/app-api/play-video.php?single="+chpater_content_id+"&multi="+chapter_id);

        }
        else
        {
            webView.loadUrl(url);
            //loadPage(webView);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
      //  webView.setWebViewClient(new MyBrowser());
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
                    DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getActivity(), "Downloading File",
                            Toast.LENGTH_LONG).show();

                }
                catch (Exception e)
                {
                    Log.i("Download Exception",e.toString());

                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                }


            }
        });






        return  view;

      }



    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.loadUrl("file:///android_asset/error.html");
        }
    }
}
