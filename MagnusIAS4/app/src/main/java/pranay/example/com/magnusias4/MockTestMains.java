package pranay.example.com.magnusias4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MockTestMains extends Fragment {
    String answer_pdf="";
    Button showAnswer;
    PDFView pdfView1;
    PDFView pdfView2;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mock_test_mains, container, false);;
        try {
             //view = inflater.inflate(R.layout.mock_test_mains, container, false);
            //View view = inflater.inflate(R.layout.content_main, container, false);

            Bundle bundle = getArguments();
            String url = bundle.getString("url");
          //  Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();

            Display display = getActivity().getWindowManager(). getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);

            float density  = getResources().getDisplayMetrics().density;
            float dpHeight = outMetrics.heightPixels / density;
            float dpWidth  = outMetrics.widthPixels / density;

            final LinearLayout linearLayout1 = view.findViewById(R.id.LinearLayoutQuestion);
            final LinearLayout linearLayout2 = view.findViewById(R.id.LinearLayoutAnswer);





            linearLayout1.getLayoutParams().height= (int)outMetrics.heightPixels/2;
            //linearLayout1.getLayoutParams().width= (int)outMetrics.widthPixels/2;
            linearLayout2.getLayoutParams().height= (int)outMetrics.heightPixels/2;
            // Toast.makeText(getActivity(), outMetrics.toString(), Toast.LENGTH_SHORT).show();
            Log.i("outMetrics",outMetrics.toString());
            pdfView2 = (PDFView) view.findViewById(R.id.pdf_answer_paper);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("mock_test_pt");

                        for(int i=0;i<dataArray.length();i++)
                        {
                            JSONObject buffer = dataArray.getJSONObject(i);
                            String ques_pdf = buffer.getString("ques_pdf");
                            answer_pdf = buffer.getString("answer_pdf");

                            new RetrievePDF1().execute(ques_pdf);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);



            textView = view.findViewById(R.id.LabelAnswer);



            pdfView1 = (PDFView) view.findViewById(R.id.pdf_question_paper);
            showAnswer = view.findViewById(R.id.showAnswer);
            showAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //linearLayout2.setVisibility(View.VISIBLE);
                    //linearLayout1.setVisibility(View.GONE);
                    new RetrievePDF2().execute(answer_pdf);
                    showAnswer.setVisibility(View.GONE);

                    textView.setVisibility(View.VISIBLE);


                }
            });


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
            pdfView2.fromStream(inputStream).load();
            // pdfView.setMinZoom(5);

        }
    }


}
