package pranay.example.com.magnusias4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockTestMains extends Fragment {

    PDFView pdfView1;
    PDFView pdfView2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mock_test_mains, container, false);;
        try {
             //view = inflater.inflate(R.layout.mock_test_mains, container, false);
            //View view = inflater.inflate(R.layout.content_main, container, false);

            pdfView1 = (PDFView) view.findViewById(R.id.pdf_question);
          //  pdfView2 = (PDFView) view.findViewById(R.id.pdf_answer);
            new RetrievePDF1().execute("http://magnusias.com//upload//creatTest//Pdf//88abfe4494bafcc539c8a751f716a920.pdf");
            //new RetrievePDF2().execute("http://magnusias.com//upload//creatTest//Pdf//ec589d2c45da8b27658be83d16960db1.pdf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    class RetrievePDF1 extends AsyncTask<String,Void,InputStream>
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
            pdfView1.fromStream(inputStream).load();
            // pdfView.setMinZoom(5);

        }
    }


    class RetrievePDF2 extends AsyncTask<String,Void,InputStream>
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
            pdfView1.fromStream(inputStream).load();
            // pdfView.setMinZoom(5);

        }
    }


}
