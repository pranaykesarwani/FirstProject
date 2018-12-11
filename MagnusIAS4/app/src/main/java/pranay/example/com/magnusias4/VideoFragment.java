package pranay.example.com.magnusias4;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import pl.droidsonroids.gif.GifImageView;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

//import com.vimeo.networking.Vimeo;

public class VideoFragment extends Fragment {
    String android_id,decrypted_filename,pdfPath;
    WebView webView;
    VideoView videoView;
    Button removeVideo, playVideo;
    Button saveVideoOffline;
    String second_level_decrypted_filepath;
    static int video_found_flag = 1;
    String videoID,videoTitle;
    GifImageView gifImageView;
    DatabaseHelper databaseHelper;
    PDFView pdfView;
    Handler handler = new Handler();
    LinearLayout offline_video_layout,online_Video_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        Bundle bundle = getArguments();
        String chpater_content_id = bundle.getString("chpater_content_id");
        String chapter_id = bundle.getString("chapter_id");
        String url = bundle.getString("url");
        pdfPath = bundle.getString("pdfPath");
        offline_video_layout = view.findViewById(R.id.offline_video_layout);
        online_Video_layout = view.findViewById(R.id.webViewLayout);
        //playVideo = view.findViewById(R.id.playOfflineVideo);
        //removeVideo = view.findViewById(R.id.deleteOfflineVideo);
        videoView = view.findViewById(R.id.videoView_save_offline_fragment);
        webView = (WebView)view.findViewById(R.id.webview);
        saveVideoOffline = view.findViewById(R.id.saveVideo_fragment);
        //gifImageView = view.findViewById(R.id.gifImageView);
//        gifImageView.setImageResource(R.drawable.wait);
        videoID = bundle.getString("videoID");
        videoTitle = bundle.getString("videoTitle");
        databaseHelper = new DatabaseHelper(getActivity());
        String offline_video_path = null;
        String offline_pdf_path;
        try{}catch(Exception e){Log.i("Crash Report",e.toString());}
        Cursor cursor  = databaseHelper.getOVTData(videoID);
        pdfView = view.findViewById(R.id.pdfNotes_0);
        pdfView.setVisibility(View.VISIBLE);
//        Log.i("cursor.getCount()",""+cursor.getCount());
        if ((cursor != null) && (cursor.getCount()>=1))
        {
            Toast.makeText(getActivity(), "Videoview...", Toast.LENGTH_SHORT).show();
            online_Video_layout.setVisibility(View.GONE);
            offline_video_layout.setVisibility(View.VISIBLE);
           // webView.setVisibility(View.GONE);
          //
            //videoView.setVisibility(View.VISIBLE);
           // removeVideo.setVisibility(View.VISIBLE);
          //  playVideo.setVisibility(View.VISIBLE);
            cursor.moveToFirst();

            offline_video_path = cursor.getString(cursor.getColumnIndex("offline_video_path"));
            offline_pdf_path = cursor.getString(cursor.getColumnIndex("offline_pdf_path"));
            //Toast.makeText(getActivity(), "Test "+cursor.getColumnCount(), Toast.LENGTH_SHORT).show();
            String videoPath = decryption_second_step_process(offline_video_path);
          //  Log.i("offline_pdf_path",""+offline_pdf_path);

            videoView.setVideoPath(videoPath);
            videoView.setMediaController(new MediaController(getActivity()));
            videoView.start();
            saveVideoOffline.setVisibility(View.GONE);
            pdfView.fromUri(Uri.fromFile(new File(offline_pdf_path))).load();


           // Toast.makeText(getActivity(), offline_video_path, Toast.LENGTH_SHORT).show();
        }
      //  playVideo = view.findViewById(R.id.playOfflineVideo);

        //final String finalOffline_video_path = offline_video_path;
        /*playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String videoPath = decryption_second_step_process(finalOffline_video_path);
                //Toast.makeText(getActivity(), videoPath, Toast.LENGTH_SHORT).show();



            }
        });*/





        /***********************************************************************************************************************************/



        ContextWrapper wrapper = new ContextWrapper(getActivity());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // URL ="http://magnusias.com/218313037.mp4";
        StrictMode.setThreadPolicy(policy);// To remomove the exception

        try {
            String videoURL = "https://magnusias.com/magnus-video/"+videoID+".mp4" ;

            int file_length = 0;
            java.net.URL url1 = new URL(videoURL);
         //   Log.i("File Availability Check","1");
            URLConnection urlConnection = url1.openConnection();
            urlConnection.connect();
         ///   Log.i("File Availability Check","2");
            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


         //   Log.i("File Size",""+file_length);


           //     Log.i("File Availability Check","2.1");
                InputStream inputStream = new BufferedInputStream(url1.openStream(),1024);
            File file = wrapper.getDir("Downloaded Video", MODE_PRIVATE);
            file = new File(file, "temp");

         //   Log.i("File Availability Check","3");
                byte[] data = new byte[1024];
                int total=0;
                int count = 0;
                OutputStream stream = null;
                stream = new FileOutputStream(file);
              //  Log.i("File Availability Check","4");
                while ((count = inputStream.read(data))!=-1)
                {    //Log.i("File Availability Check","5");
                    break;

                    //total+=count;
                    //stream.write(data,0,count);
                    //int progress = (int)total * 100 / file_length;

                }
                inputStream.close();
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
                //Toast.makeText(this, "Download Succesful", Toast.LENGTH_SHORT).show();
          //  Log.i("File Availability","File Available");
            } catch (Exception e) // Catch the exception
            {
                // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                video_found_flag = 0;
                saveVideoOffline.setVisibility(View.GONE);
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
              //  int width = display.getWidth();
            //    int height = display.getHeight();
                display.getSize(size);
                int display_height = size.y;

                LinearLayout linearLeft = (LinearLayout) view.findViewById(R.id.webViewLayout);
                ViewGroup.LayoutParams params = linearLeft .getLayoutParams();
                params.height = display_height;
                e.printStackTrace();
                Log.i("File Availability","File Not Available");

            }

      /*      if (video_found_flag ==0)
            {

            }*/


            /*******************************************************************************************************************************/

     //   Log.i("File Availability Check", "3");

              /*  removeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.removeVideo(videoID);
              // delete_offline_video_operation();

                File file = new File( second_level_decrypted_filepath);
                file.delete();
                Toast.makeText(getActivity(), "Video Removed!", Toast.LENGTH_SHORT).show();
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
                removeVideo.setVisibility(View.GONE);
                saveVideoOffline.setEnabled(true);
                saveVideoOffline.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
            }
        });
*/

       // Toast.makeText(getActivity(), chpater_content_id+" "+chapter_id , Toast.LENGTH_SHORT).show();
    //    Log.i("IDs",chpater_content_id+" "+chapter_id);
     //   Log.i("Video URL",videoID);




        saveVideoOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setMessage("Downloading Video...");
                pd.show();
              /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder .setTitle("Offline Video Streaming")
                        .setMessage("Downloading video for offline streaming. Please hang on!")
                        .show();*/


                final Runnable r = new Runnable() {
                    public void run() {

                   //saveVideoOffline.setEnabled(false);
                   String videoURL = "https://magnusias.com/magnus-video/" + videoID + ".mp4";
                   Toast.makeText(getContext(), "Please wait. Your video will be downloaded shortly!", Toast.LENGTH_LONG).show();
                   ///DownloadVideo downloadVideo = new DownloadVideo();

                   String encrypted_file = fileDownload(videoURL, "MP4","");
                   String first_step_decrypted_filepath = decryption_first_step_process(encrypted_file);
                   String second_level_encrypted_filepath = encryption_second_step_process(first_step_decrypted_filepath);
                   second_level_decrypted_filepath = decryption_second_step_process(second_level_encrypted_filepath);
                    String offline_pdf_path = fileDownload(pdfPath,"PDF",videoID);
               //     Log.i("offline_pdf_path",offline_pdf_path);
                 //  videoView.setVisibility(View.GONE);
                        online_Video_layout.setVisibility(View.GONE);
                        offline_video_layout.setVisibility(View.VISIBLE);

                        //   MediaController mediaController = new MediaController(getActivity());
                   // mediaController.setAnchorView(videoView);
//                   Log.i("Video File Path", second_level_decrypted_filepath);
                   videoView.setVideoPath(second_level_decrypted_filepath);

                   videoView.setMediaController(new MediaController(getActivity()));
                   videoView.start();

                   //    removeVideo.setVisibility(View.VISIBLE);
                   saveVideoOffline.setVisibility(View.GONE);
                   webView.setVisibility(View.GONE);
                        pdfView.fromUri(Uri.fromFile(new File(offline_pdf_path))).load();
                   databaseHelper.insertOVT(videoID, "Yes", second_level_encrypted_filepath, videoTitle,offline_pdf_path);

                /* // Drawable drawable = getResources().getDrawable(R.drawable.cover);

               // Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ContextWrapper wrapper = new ContextWrapper(getActivity());
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


                StrictMode.setThreadPolicy(policy);

                try {
                    int file_length = 0;
                    java.net.URL url = new URL("http://magnusias.com/218313037.mp4");

                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();

                    file_length = urlConnection.getContentLength();
                    //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


                    Log.i("Image Size",""+file_length);
                    File file = wrapper.getDir("Magnus_Encypted_Videos", MODE_PRIVATE);
                    file = new File(file, "http://magnusias.com/218313037.mp4");

                    try {

                        InputStream inputStream = new BufferedInputStream(url.openStream(),1024);
                        byte[] data = new byte[1024];
                        int total=0;
                        int count = 0;
                        OutputStream stream = null;
                        stream = new FileOutputStream(file);
                        while ((count = inputStream.read(data))!=-1)
                        {

                            total+=count;
                            stream.write(data,0,count);
                            //int progress = (int)total * 100 / file_length;

                        }
                        inputStream.close();

                        stream.flush();
                        stream.close();

                    } catch (IOException e) // Catch the exception
                    {
                        Log.i("File Download Exception","1 "+e.toString());
                        e.printStackTrace();
                    }

                    // Parse the gallery image url to uri
                    Uri savedImageURI = Uri.parse(file.getAbsolutePath());
                 //   return  savedImageURI.toString();
                }
                catch (Exception e)
                {
                    Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("File Download Exception","2 "+e.toString());
                }
*/
                    pd.dismiss();
                    }
                };

                handler.postDelayed(r, 1000);
            }
        });







       /* saveVideoOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable = getResources().getDrawable(R.drawable.cover);

                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ContextWrapper wrapper = new ContextWrapper(getActivity());
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);// To remomove the exception

                try {
                    int file_length = 0;
                    java.net.URL url = new URL(videoID);

                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();

                    file_length = urlConnection.getContentLength();
                    //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


                    Log.i("Video Size", "" + file_length);
                    File file = wrapper.getDir("Magnus_Encrypted", MODE_PRIVATE);
                    file = new File(file, videoID);

                    try {
*//*

                        InputStream inputStream = new BufferedInputStream(url.openStream(), 1024);
                        byte[] data = new byte[1024];
                        int total = 0;
                        int count = 0;
                        OutputStream stream = null;
                        stream = new FileOutputStream(file);
                        while ((count = inputStream.read(data)) != -1) {

                            total += count;
                            stream.write(data, 0, count);
                            //int progress = (int)total * 100 / file_length;

                        }
                        inputStream.close();
                        // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        stream.flush();
                        stream.close();

                        Uri fileUri = Uri.parse(file.getAbsolutePath());
                        Toast.makeText(wrapper, "File Downloaded Successfully at "+fileUri.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("Encrypted File Location",fileUri.toString());
*//*

                    } catch (IOException e) // Catch the exception
                    {
                        Log.i("File Download Exception", e.toString());
                        e.printStackTrace();
                    }

                    // Parse the gallery image url to uri
                    Uri savedImageURI = Uri.parse(file.getAbsolutePath());
                    Toast.makeText(wrapper, file.getPath(), Toast.LENGTH_SHORT).show();
               //     decryption()
                 //   encryption(videoID);
                    String video_filepath =decryption(file,file.getPath());
                    videoView.setVideoPath(video_filepath);
                    videoView.start();
                    //   return  savedImageURI.toString();
                } catch (Exception e) {
                   // Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("File Download Exception", e.toString());
                }

            }
        });*/
        //WebSettings settings = webView.getSettings();
        //settings.setDomStorageEnabled(true);
        //url="https://drive.google.com/file/d/1G3lyBnynZBw7rYMoKXluNr-zpnWrrUQb/view";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        if (url==null)
        {
            webView.loadUrl("https://magnusias.com/app-api/play-video.php?single="+chpater_content_id+"&multi="+chapter_id);
          //  Log.i("Video URL  Stage 5.1","https://magnusias.com/app-api/play-video.php?single="+chpater_content_id+"&multi="+chapter_id);
        }
        else
        {
            webView.loadUrl(url);
            //loadPage(webView);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
      //  webView.setWebViewClient(new MyBrowser());
       /* webView.setDownloadListener(new DownloadListener() {
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
        });*/






        return  view;

      }

    private void delete_offline_video_operation() {




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



    private String decryption(File file1, String path) {

        try {
            Log.i("Decryption","Decryption Started");

            byte[] data = new byte[1024];
            int total=0;
            int count = 0;
            //  TextView decryption = (TextView)findViewById(R.id.txtdecrypt);
            //  decryption.setText("Please wait....");
            ContextWrapper wrapper = new ContextWrapper(getActivity());
            File file = wrapper.getDir("Videos_Encrypted", MODE_PRIVATE);
            file = new File(file, "Decrypted Video" + ".mp4");
            Log.i("Decryption","1");
            //Toast.makeText(wrapper, filename, Toast.LENGTH_SHORT).show();
            //InputStream instream = openFileInput(filename);

            FileInputStream fileInputStream = new FileInputStream(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String s = "PranayKesarwani@"+android_id;
            byte k[] = s.getBytes();//android_id.getBytes();
            SecretKeySpec key = new SecretKeySpec(k,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher);
            Log.i("Decryption","2");
            while ((count = fileInputStream.read(data))!=-1)
            {
                total+=count;
                cipherOutputStream.write(data,0,count);
                //int progress = (int)total * 100 / file_length;
            }
            Log.i("Decryption","3");





            fileInputStream.close();
            // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            fileOutputStream.flush();
            fileOutputStream.close();
//                    cipherOutputStream.close();

            Uri savedImageURI = Uri.parse(file.getAbsolutePath());
            decrypted_filename = savedImageURI.toString();
            //Toast.makeText(wrapper, savedImageURI.toString(), Toast.LENGTH_SHORT).show();

            Log.i("Decryption","Decryption Completed");
            //Log.i("Decrypted file path",savedImageURI.toString());


            //decryption.setText("Success");
            // FileInputStream file =  new FileInputStream("/data/user/0/pranay.example.com.webviewapp2/app_Videos_PRK/Video.mp4");
            //  OutputStream stream = null;
            //stream = new FileOutputStream(file);

        } catch (Exception e) {
            e.printStackTrace();
          //  Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            //TextView decryption = (TextView)findViewById(R.id.txtencrypt);
            //decryption.setText("Failed");

        }
        return decrypted_filename;


    }


    private void encryption(String VideoURL) {

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_PHONE_STATE},1);

            return;
        }
       String imei = telephonyManager.getDeviceId();
        ContextWrapper wrapper = new ContextWrapper(getActivity());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);// To remomove the exception


        try {
            Log.i("Encryption","Encryption Started");
            int file_length = 0;
            java.net.URL url = new URL(VideoURL);

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();
            // TextView encryption = (TextView)findViewById(R.id.txtencrypt);
            ///   encryption.setText("Please wait...");

            //    filename = "/data/data/pranay.example.com.webviewapp2/app_Videos_Encrypted/Android Studio Tutorial - Real Time Location Tracking Part 2 (Tracking) - YouTube.MKV";
            Log.i("Video Size",""+file_length);
            File file = wrapper.getDir("Videos_PRK", MODE_PRIVATE);
            file = new File(file,VideoURL );

            try {
                //FileInputStream inputStream = new FileInputStream(filename);
                InputStream inputStream = new BufferedInputStream(url.openStream(),1024);
                byte[] data = new byte[1024];
                int total=0;
                int count = 0;
                OutputStream stream = null;
                stream = new FileOutputStream(file);
                byte[] imeiBytes = imei.getBytes();
                String s = "PranayKesarwani@"+android_id;
                byte keyBytes[] = s.getBytes();//android_id.getBytes();

                byte[] combinedBytes = new byte[imeiBytes.length + keyBytes.length];

                System.arraycopy(imeiBytes,0,combinedBytes,0         ,imeiBytes.length);
                System.arraycopy(keyBytes,0,combinedBytes,imeiBytes.length,keyBytes.length);

                SecretKeySpec key = new SecretKeySpec(keyBytes,"AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE,key);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(stream,cipher);

                while ((count = inputStream.read(data))!=-1)
                {

                    total+=count;
                    cipherOutputStream.write(data,0,count);
                    //int progress = (int)total * 100 / file_length;

                }






                inputStream.close();
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
                cipherOutputStream.close();
                Log.i("Encryption","Encryption Completed");
                //   encryption.setText("Success");

            } catch (IOException e) // Catch the exception
            {
                Log.i("File Download Exception",e.toString());
                e.printStackTrace();
                // TextView encryption = (TextView)findViewById(R.id.txtencrypt);
                // encryption.setText("Failed");
            }

            // Parse the gallery image url to uri
            // savedImageURI = Uri.parse(file.getAbsolutePath());
            //filename = savedImageURI.toString();
            //Toast.makeText(wrapper, savedImageURI.toString(), Toast.LENGTH_SHORT).show();
            //decrypt.setEnabled(true);
            //Log.i("Filename",filename);
            //  return  savedImageURI.toString();



        }
        catch (Exception e)
        {
           // Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("File Download Exception",e.toString());
        }




    }


    String fileDownload(String URL,String filetype,String pdfFilename){
        //    Drawable drawable = getResources().getDrawable(R.drawable.cover);

        //  Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ContextWrapper wrapper = new ContextWrapper(getActivity());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       // URL ="http://magnusias.com/218313037.mp4";
        StrictMode.setThreadPolicy(policy);// To remomove the exception

        try {
           // Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_SHORT).show();
            int file_length = 0;
            java.net.URL url = new URL(URL);
          //  Log.i("File Availability Check","1");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            //Log.i("File Availability Check","2");
            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


           // Log.i("File Size",""+file_length);
            File file = wrapper.getDir("Downloaded Video", MODE_PRIVATE);
            if (filetype.equals("PDF"))
            {
                file = new File(file, pdfFilename+".pdf");
            }
            if (filetype.equals("MP4")) {
                file = new File(file, "218313037.mp4");
            }
            try {
                //Log.i("File Availability Check","2.1");
                InputStream inputStream = new BufferedInputStream(url.openStream(),1024);
                //Log.i("File Availability Check","3");
                byte[] data = new byte[1024];
                int total=0;
                int count = 0;
                OutputStream stream = null;
                stream = new FileOutputStream(file);
               // Log.i("File Availability Check","4");
                while ((count = inputStream.read(data))!=-1)
                {
                    //Log.i("File Availability Check","5");
                    total+=count;
                    stream.write(data,0,count);
                    //int progress = (int)total * 100 / file_length;

                }
                inputStream.close();
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
                Toast.makeText(getActivity(), "Video downloaded successfully!", Toast.LENGTH_SHORT).show();
              //  Log.i("Download Status","Download Successful");
            } catch (IOException e) // Catch the exception
            {
               // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            //    video_found_flag = 0;
                Log.i("File Download Exception",e.toString());
                e.printStackTrace();
                Log.i("Download Status","Download Failed!");

            }

            // Parse the gallery image url to uri
            Uri savedImageURI = Uri.parse(file.getAbsolutePath());
            return  savedImageURI.toString();
        }
        catch (Exception e)
        {

           // Log.i("video_found_flag",video_found_flag+" video_found_flag value");
           // Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("File Download Exception",e.toString());
        }

        return null;
    }

    private String decryption_first_step_process(String encrypted_file) {
        try{
            int total = 0;
            int count = 0;
            byte[] data = new byte[1024];

            ContextWrapper wrapper = new ContextWrapper(getActivity());
            File file = wrapper.getDir("Videos_Decrypted", MODE_PRIVATE);
            file = new File(file, "Decrypted 001" + ".mp4");
            Log.i("Decryption","1");
            //Toast.makeText(wrapper, filename, Toast.LENGTH_SHORT).show();
            //InputStream instream = openFileInput(filename);

            FileInputStream fileInputStream = new FileInputStream(encrypted_file);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
        //    String s = "PranayKesarwani@"+android_id;
         //   Log.i("1st Decryption","1");
            String s = "Mania@group_tech";
            byte k[] = s.getBytes();//android_id.getBytes();
           // Log.i("1st Decryption","2");
            SecretKeySpec key = new SecretKeySpec(k,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            //Log.i("1st Decryption","3");
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher);
            //   Log.i("Decryption","2");
            while ((count = fileInputStream.read(data))!=-1)
            {

                total+=count;
                cipherOutputStream.write(data,0,count);
                //int progress = (int)total * 100 / file_length;

            }
            // Log.i("Decryption","3");





            fileInputStream.close();
            // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            fileOutputStream.flush();
            fileOutputStream.close();
//                    cipherOutputStream.close();

          Uri  savedImageURI = Uri.parse(file.getAbsolutePath());
            String filename = savedImageURI.toString();
            Log.i("Decrypted File Location",filename);

            return filename;

        }catch (Exception e){e.printStackTrace();}

        Log.i("Decryption Level 1 ", "Success");
        return  null;
    }

    private String encryption_second_step_process(String firt_step_decrypted_filepath) {

        ContextWrapper wrapper = new ContextWrapper(getActivity());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);// To remomove the exception


        try {
            Log.i("Encryption","Encryption Started");
            int file_length = 0;
    /*        java.net.URL url = new URL("http://magnusias.com/218313037.mp4");

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();

            encryption.setText("Please wait...");
*/
            //   filename = "/data/data/pranay.example.com.webviewapp2/app_Videos_Encrypted/Android Studio Tutorial - Real Time Location Tracking Part 2 (Tracking) - YouTube.MKV";
         //   Log.i("Image Size",""+file_length);
            File file = wrapper.getDir("Videos_Decrypted", MODE_PRIVATE);
            file = new File(file, videoID + ".mp4");

            try {
                FileInputStream inputStream = new FileInputStream(firt_step_decrypted_filepath);
                // InputStream inputStream = new BufferedInputStream(url.openStream(),1024);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                OutputStream stream = null;
                stream = new FileOutputStream(file);
                String imei = "123456789123456";
                byte[] b = imei.getBytes();
                android_id = android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                Log.i("android_id", android_id);
                String s = "PranayKesarwani@" + android_id;
                if (s.length() < 32)
                {
                    while(s.length()<32)
                    {
                        s="!"+s;
                    }


                }
                Log.i("s",""+s.length());
                byte k[] = s.getBytes();//android_id.getBytes();

                byte[] combined = new byte[b.length + k.length];

                System.arraycopy(b,0,combined,0         ,b.length);
                System.arraycopy(k,0,combined,b.length,k.length);

                SecretKeySpec key = new SecretKeySpec(k,"AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE,key);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(stream,cipher);

                while ((count = inputStream.read(data))!=-1)
                {

                    total+=count;
                    cipherOutputStream.write(data,0,count);
                    //int progress = (int)total * 100 / file_length;

                }
                inputStream.close();
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
                cipherOutputStream.close();
                Log.i("Encryption","Encryption Completed");
                //encryption.setText("Success");

            } catch (IOException e) // Catch the exception
            {
                Log.i("File Download Exception",e.toString());
                e.printStackTrace();
                // TextView encryption = (TextView)findViewById(R.id.txtencrypt);
                //encryption.setText("Failed");
            }

            // Parse the gallery image url to uri
     /*       savedImageURI = Uri.parse(file.getAbsolutePath());
            filename = savedImageURI.toString();
            Toast.makeText(wrapper, savedImageURI.toString(), Toast.LENGTH_SHORT).show();
     */       // decrypt.setEnabled(true);
            //  return  savedImageURI.toString();

          Uri  savedImageURI = Uri.parse(file.getAbsolutePath());
            String filename = savedImageURI.toString();

            //Log.i("Filename",filename);
        return filename;
        }
        catch (Exception e)
        {
            Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("File Download Exception",e.toString());
        }

        return  null;
    }

     String decryption_second_step_process(String second_level_encrypted_filepath) {

        try {
            // Log.i("Decryption","Decryption Started");


//                    filename = "/data/data/pranay.example.com.webviewapp2/app_Videos_Encrypted/001- Ethics -syllabus -overview.mp4";
          String  filename = second_level_encrypted_filepath;
           // Toast.makeText(getActivity(), filename, Toast.LENGTH_LONG).show();
            byte[] data = new byte[1024];
            int total=0;
            int count = 0;
            //      TextView decryption = (TextView)findViewById(R.id.txtdecrypt);
            //    decryption.setText("Please wait....");
            ContextWrapper wrapper = new ContextWrapper(getActivity());
            File file = wrapper.getDir("Videos_Decrypted", MODE_PRIVATE);
            file = new File(file, "Decrypted 002" + ".mp4");
            //Log.i("Decryption","1");
            //Toast.makeText(wrapper, filename, Toast.LENGTH_SHORT).show();
            //InputStream instream = openFileInput(filename);

            FileInputStream fileInputStream = new FileInputStream(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            //Log.i("android_id",android_id);
            String s = "PranayKesarwani@"+android_id;

            if (s.length() < 32)
            {
                Log.i("Test","Not OK");
                while(s.length()<32)
                {
                    s="!"+s;
                }


            }
            if (s.length()==32)
            {
                Log.i("Test"," OK");
            }


//            String s = "Mania@group_tech";
            byte k[] = s.getBytes();//android_id.getBytes();
            SecretKeySpec key = new SecretKeySpec(k,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher);
            Log.i("Decryption","2");
            while ((count = fileInputStream.read(data))!=-1)
            {

                total+=count;
                cipherOutputStream.write(data,0,count);
                //int progress = (int)total * 100 / file_length;

            }
            Log.i("Decryption","3");





            fileInputStream.close();
            // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            fileOutputStream.flush();
            fileOutputStream.close();
//                    cipherOutputStream.close();

            Uri savedImageURI = Uri.parse(file.getAbsolutePath());
            filename = savedImageURI.toString();
            //Toast.makeText(wrapper, savedImageURI.toString(), Toast.LENGTH_SHORT).show();

            Log.i("Decryption","Decryption Completed");
            Log.i("Decrypted file path",savedImageURI.toString());


            //decryption.setText("Success");

            // FileInputStream file =  new FileInputStream("/data/user/0/pranay.example.com.webviewapp2/app_Videos_PRK/Video.mp4");
            //  OutputStream stream = null;
            //stream = new FileOutputStream(file);

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(Main4Activity.this, e.toString(), Toast.LENGTH_SHORT).show();
            //TextView decryption = (TextView)findViewById(R.id.txtencrypt);
            //decryption.setText("Failed");

        }
        return null;
    }

/*
    class DownloadVideo extends AsyncTask<Void,Integer,Void>
    {
        ProgressDialog progressDialog;
        @Override
        protected Void doInBackground(Void... voids) {
                int i=0;
                synchronized (this){
                    while (i<10)
                    {
                        try {
                            wait(100);

                            publishProgress(++i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            publishProgress();
            return null;

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Downloading...");
            progressDialog.setMax(10);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            progressDialog.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }*/






}
