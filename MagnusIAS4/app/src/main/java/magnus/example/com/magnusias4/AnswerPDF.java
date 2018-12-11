package magnus.example.com.magnusias4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pranay.example.com.magnusias4.R;

public class AnswerPDF extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_pdf);

        pdfView= findViewById(R.id.answerPDF);
        Intent intent = getIntent();
        final String link = intent.getStringExtra("link");
        final String linkType = intent.getStringExtra("linkType");
//       Log.i(" PDF Link",link);
      //  Toast.makeText(this, link, Toast.LENGTH_SHORT).show();
        if (linkType.equals("offline"))
        {
            pdfView.fromUri(Uri.fromFile(new File(link))).load();
        }
        if (linkType.equals("online")) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Downloading Answer sheet...");
            pd.show();
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                public void run() {

                    new RetrievePDF().execute(link);

                  //  Toast.makeText(AnswerPDF.this, "Loading Answers...", Toast.LENGTH_SHORT).show();

                    pd.dismiss();
                }

            };
            handler.postDelayed(r, 10000);
        }
    }

    class RetrievePDF extends AsyncTask<String,Void,InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                if (httpURLConnection.getResponseCode()==200)
                {
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }


            }
            catch (Exception e){
                Log.i("PDF Async Exception",e.toString());
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            pdfView.fromStream(inputStream).load();
            //pdfView.fitToWidth();
        //    Toast.makeText(AnswerPDF.this, "Check", Toast.LENGTH_SHORT).show();
            //pdfView.setMinZoom(5);

        }
    }

}
