package pranay.example.com.magnusias4;

import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class OfflinePlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_player);
        Intent intent = getIntent();
        String videoPath = decryption_second_step_process(intent.getStringExtra("videoPath"));
       // Toast.makeText(this, videoPath, Toast.LENGTH_SHORT).show();
        VideoView videoView = findViewById(R.id.offline_video_player);

        videoView.setVideoPath(videoPath);

        videoView.setMediaController(new MediaController(this));
        videoView.start();

        PDFView pdfView  = findViewById(R.id.pdfNotes);
        pdfView.fromUri(Uri.fromFile(new File(intent.getStringExtra("link")))).load();




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
            ContextWrapper wrapper = new ContextWrapper(this);
            File file = wrapper.getDir("Videos_Decrypted", MODE_PRIVATE);
            file = new File(file, "Decrypted 002" + ".mp4");
      //      Log.i("Decryption","1");
            //Toast.makeText(wrapper, filename, Toast.LENGTH_SHORT).show();
            //InputStream instream = openFileInput(filename);

            FileInputStream fileInputStream = new FileInputStream(filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.i("android_id",android_id);
            String s = "PranayKesarwani@"+android_id;
            if (s.length() < 32)
            {
                while(s.length()<32)
                {
                    s="!"+s;
                }


            }


//            String s = "Mania@group_tech";
            byte k[] = s.getBytes();//android_id.getBytes();
            SecretKeySpec key = new SecretKeySpec(k,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,key);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher);
    //        Log.i("Decryption","2");
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

  //          Log.i("Decryption","Decryption Completed");
//            Log.i("Decrypted file path",savedImageURI.toString());


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
}
