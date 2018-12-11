package pranay.example.com.magnusias4;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RandomExamTest extends Fragment {
    TextView random_question,option1,option2,option3,option4,hints,txtDescription,question_serial;
    EditText answer;
    Button verify_answer;
    private List<RandomTest> dataFeed= new ArrayList<RandomTest>();
    ListView listView;

    com.joanzapata.pdfview.PDFView pdfView;
    public static String pdfPath,pdf_path;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
//        Log.i("bundle",url);

        View view = inflater.inflate(R.layout.random_exam_test, container, false);
        /*random_question = (TextView)view.findViewById(R.id.random_question);
        hints = (TextView)view.findViewById(R.id.hints);
        verify_answer = (Button)view.findViewById(R.id.verify_answer);
        */
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        }



        final ArrayAdapter<RandomTest> adapter = new RandomExamTest.Adapter();
        listView = (ListView) view.findViewById(R.id.random_test_list_0);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("Random Test Response",response.toString());
                try {
                    JSONArray temp = response.getJSONArray("mock_test_pt");

                    for(int i = 0;i<temp.length(); i++){
                        JSONObject buffer = temp.getJSONObject(i);
                        String question_num = buffer.getString("question_num");
                        String question = buffer.getString("question");
                        String options = buffer.getString("options");
                        String answers = buffer.getString("answers");
                        String description = buffer.getString("description");
                        String question_type = buffer.getString("question_type");
                        String pdf = buffer.getString("pdf");
                        pdf_path = buffer.getString("pdf_path");
                        String video_url = buffer.getString("video_url");
                        String hints = buffer.getString("hints");
                        Log.i("PDF path",pdf);
                        dataFeed.add(new RandomTest(question_num,question,options,answers,description,question_type,pdf,video_url,hints));
                        adapter.notifyDataSetChanged();



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Random Test Error",error.toString());
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);
        listView.setAdapter(adapter);

        return view;
    }

    private class  Adapter extends  ArrayAdapter<RandomTest> {

        public Adapter() {
            super(getActivity(), R.layout.random_exam_test,dataFeed);
        }

        public  void hideSoftKeyboard(Activity activity) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            try{

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.sample_random_test, null, false);
            }
            final LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.hintLayout);
            final LinearLayout descriptionLayout = (LinearLayout)convertView.findViewById(R.id.descriptionLayout);
            random_question = (TextView)convertView.findViewById(R.id.random_question);
            option1 = (TextView)convertView.findViewById(R.id.option1);
            option2 = (TextView)convertView.findViewById(R.id.option2);
            option3 = (TextView)convertView.findViewById(R.id.option3);
            option4 = (TextView)convertView.findViewById(R.id.option4);
            txtDescription = (TextView)convertView.findViewById(R.id.description);
            question_serial = convertView.findViewById(R.id.question_serial);


            hints = (TextView)convertView.findViewById(R.id.hints);
            verify_answer = (Button)convertView.findViewById(R.id.verify_answer);
            answer = (EditText)convertView.findViewById(R.id.answer);
            final RandomTest currentItem = dataFeed.get(position);
            Log.i("options",currentItem.getOptions());
            question_serial.setText(currentItem.getQuestion_num());
            pdfView = (com.joanzapata.pdfview.PDFView)convertView.findViewById(R.id.pdf_hint);
            final RelativeLayout relativeLayout1 = convertView.findViewById(R.id.hinPDFLayout);
            final WebView videoView = convertView.findViewById(R.id.hint_video_webview);
            final RelativeLayout relativeLayout2 = convertView.findViewById(R.id.relativeLayout_Description);
            final boolean[] showVideo_PDF = new boolean[1];
            verify_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (answer.getText().toString().toUpperCase().trim().equals(currentItem.getAnswers())) {
                        Toast.makeText(getActivity(), answer.getText().toString().toUpperCase().trim(), Toast.LENGTH_SHORT).show();
                        descriptionLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
                        showVideo_PDF[0] = true;

                        if( (!currentItem.getPdf().equals("")))
                        {
                            relativeLayout1.setVisibility(View.VISIBLE);
                            pdfView.setVisibility(View.VISIBLE);
                            new DownloadingTask().execute();

                            pdfPath = currentItem.getPdf();
                            pdfPath = pdf_path+pdfPath;
                          //  Toast.makeText(getContext(), pdfPath, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            pdfView.setVisibility(View.GONE);
                            relativeLayout1.setVisibility(View.GONE);
                        }
// 143070295073

                        if (!(currentItem.getVideo_url().equals("")))
                        {   relativeLayout2.setVisibility(View.VISIBLE);
                            videoView.setVisibility(View.VISIBLE);
                            videoView.setWebChromeClient(new WebChromeClient());
                            videoView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                            videoView.getSettings().setJavaScriptEnabled(true);
                            videoView.getSettings().setAppCacheEnabled(true);
                            videoView.loadUrl(currentItem.getVideo_url()
                            );
                            // videoView.loadUrl(currentItem.getVideo_url());
                        }
                        else
                        {
                            videoView.setVisibility(View.GONE);
                            relativeLayout2.setVisibility(View.GONE);
                        }


                        hideSoftKeyboard(getActivity());

                    }
                    else
                    {Toast.makeText(getActivity(), "Wrong Answer", Toast.LENGTH_SHORT).show();
                        linearLayout.setVisibility(View.VISIBLE);
                        descriptionLayout.setVisibility(View.GONE);
                    }
                }
            });
            String optionsValue = currentItem.getOptions();
            optionsValue = "{\"options\":"+optionsValue +"}";
            txtDescription.setText(Html.fromHtml(currentItem.getDescription()));

            try {
                JSONObject jsonObject =  new JSONObject(optionsValue);
                JSONArray jsonArray = jsonObject.getJSONArray("options");

                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject oneObject = jsonArray.getJSONObject(i);

                    String key = oneObject.getString("key");
                    String value = oneObject.getString("val");
                    if(i==0)
                    {
                        option1.setText(value);
                    }
                    if(i==1)
                    {
                        option2.setText(value);

                    }
                    if(i==2)
                    {
                        option3.setText(value);
                    }
                    if(i==3)
                    {
                        option4.setText(value);
                    }
                    Log.i("Key",key +" "+i);
                    Log.i("Value",value+" "+i);
                }

                //String aJsonString = jsonObject.getString("key");
                //Log.i("Key",aJsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }





            random_question.setText(Html.fromHtml(currentItem.getQuestion()));
          //  options.setText(Html.fromHtml(currentItem.getOptions()));
            hints.setText(Html.fromHtml(currentItem.getHints()));

            Log.i("Inside Adapter","1");
            /*

            for(int j=0;j<options.length();j++)
            {
                JSONObject optionsBuffer = options.getJSONObject(j);
                String key  = optionsBuffer.getString("key");
                String val  = optionsBuffer.getString("val");
                Log.i("Key",key);
                Log.i("Val",val);
            }
*/


            }catch (Exception e){
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            return  convertView;
        }
    }


     class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPostExecute(Void result) {

            try {
                if (outputFile != null) {
                    //Susses Download
                    //Toast.makeText(DetailWallpaper.this, "Download Success", Toast.LENGTH_SHORT).show();
                } else {
                    //Failed Download
                    // Toast.makeText(DetailWallpaper.this, "Download Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //Toast.makeText(DetailWallpaper.this, "Download Failed", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(result);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                //That is url file you want to download
                URL url = new URL(pdfPath);
                    Log.i("PDF Path",pdfPath);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                //Creating Path
                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                + "Magnus IAS");

                if (!apkStorage.exists()) {
                    //Create Folder From Path
                    apkStorage.mkdir();
                }

                //Path And Filename.type
                outputFile = new File(apkStorage, "MagnusPDF_0.pdf");

                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e("Magnus PDF", "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }

                fos.close();
                is.close();

                //com.github.barteksc.pdfviewer.PDFView pdfView1 = findViewById(R.id.pdfview1);
                //  pdfView1.fromFile(outputFile).load();
                // ;
                //View view = inflater.inflate(R.layout.random_exam_test, container, false);

                //com.joanzapata.pdfview.PDFView pdfView =
                pdfView.setSwipeVertical(true);
                pdfView.fromFile(outputFile).load();
                Log.i("PDF", "PDF Loaded");

            } catch (Exception e) {
            }

            return null;
        }
    }


    /**************************************************************************************************************************/




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
           // pdfView.fromStream(inputStream).load();
            // pdfView.setMinZoom(5);

        }
    }

}
