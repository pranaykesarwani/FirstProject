package pranay.example.com.magnusias4;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class VideoAcitvity extends AppCompatActivity {
    WebView webView;
    String android_id,decrypted_filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_acitvity);

    android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Intent intent = getIntent();
        String chpater_content_id = intent.getStringExtra("chpater_content_id");
        String chapter_id = intent.getStringExtra("chapter_id");
        String url = "http://magnusias.com/app-api/play-video.php?single=" + chpater_content_id + "&multi=" + chapter_id;
        Button saveVideo = findViewById(R.id.saveVideo);
        final VideoView videoView = findViewById(R.id.videoView_save_offline);
        saveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Drawable drawable = getResources().getDrawable(R.drawable.cover);

                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ContextWrapper wrapper = new ContextWrapper(VideoAcitvity.this);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);// To remomove the exception

                try {
                    int file_length = 0;
                    java.net.URL url = new URL("http://maniagot.com/101-Introduction-to-Indian-Geography.mp4");

                    URLConnection urlConnection = url.openConnection();
                    urlConnection.connect();

                    file_length = urlConnection.getContentLength();
                    //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


                    Log.i("Video Size", "" + file_length);
                    File file = wrapper.getDir("Videos_PRK", MODE_PRIVATE);
                    file = new File(file, "Video1.mp4");

                    try {

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

                    } catch (IOException e) // Catch the exception
                    {
                        Log.i("File Download Exception", e.toString());
                        e.printStackTrace();
                    }

                    // Parse the gallery image url to uri
                    Uri savedImageURI = Uri.parse(file.getAbsolutePath());
                    Toast.makeText(wrapper, file.getPath(), Toast.LENGTH_SHORT).show();
                    encryption(file.getPath());
                   String video_filepath =decryption(file,file.getPath());
                    videoView.setVideoPath(video_filepath);
                    videoView.start();
                    //   return  savedImageURI.toString();
                } catch (Exception e) {
                    Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("File Download Exception", e.toString());
                }


            }
        });
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);



    }

    private String decryption(File file1, String path) {

        try {
            Log.i("Decryption","Decryption Started");

            byte[] data = new byte[1024];
            int total=0;
            int count = 0;
          //  TextView decryption = (TextView)findViewById(R.id.txtdecrypt);
          //  decryption.setText("Please wait....");
            ContextWrapper wrapper = new ContextWrapper(this);
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
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            //TextView decryption = (TextView)findViewById(R.id.txtencrypt);
            //decryption.setText("Failed");

        }
        return decrypted_filename;


    }


    private void encryption(String filepath) {

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);

            return;
        }
        String imei = telephonyManager.getDeviceId();
        ContextWrapper wrapper = new ContextWrapper(VideoAcitvity.this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);// To remomove the exception


        try {
            Log.i("Encryption","Encryption Started");
            int file_length = 0;
            java.net.URL url = new URL("http://maniagot.com/101-Introduction-to-Indian-Geography.mp4");

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();
           // TextView encryption = (TextView)findViewById(R.id.txtencrypt);
         ///   encryption.setText("Please wait...");

        //    filename = "/data/data/pranay.example.com.webviewapp2/app_Videos_Encrypted/Android Studio Tutorial - Real Time Location Tracking Part 2 (Tracking) - YouTube.MKV";
            Log.i("Image Size",""+file_length);
            File file = wrapper.getDir("Videos_PRK", MODE_PRIVATE);
            file = new File(file, "Video1" + ".mp4");

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
            Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("File Download Exception",e.toString());
        }




    }

}
