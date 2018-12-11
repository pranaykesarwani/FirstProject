

package pranay.example.com.magnusias4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import magnus.example.com.magnusias4.AnswerPDF;

public class ExamActivity extends AppCompatActivity {
    List<String> objects;
    public List<String> ob;
    int total_questions,tq;
    String ques_pdf;
    PDFView pdfView;

    //  private List<VideoListDataItem> dataFeed= new ArrayList<VideoListDataItem>();
    ListView listView;
    String test_id,marked_answer,test_type;
    String answer_url,answer_url_0="2";
    String q_no,answer_set_key;
    String duration,subject;
    int hour,minute,second;
    int attempt_counter=0;
    int null_counter = 0;
    String question_no;
    String marks_correct_ans,negative_mark;
    TextView duration_text,test_name,rem_time;
    float deducted_marks,marks,total_marks=0;
    DatabaseHelper databaseHelper;
    MyAdapter1 adapter;
    Button showAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_exam);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_bar);

        //Bundle bundle = getArguments();
        databaseHelper = new DatabaseHelper(ExamActivity.this);
        databaseHelper.createAnswerTable();
        Button submitExam;
        ob=new ArrayList<String>();

        objects=new ArrayList<>();
        Intent intent = getIntent();

        String url =intent.getStringExtra("url");
        Log.i("Objective Exam URL",url);
        duration_text = (TextView)findViewById(R.id.maximum_marks);
        test_name = (TextView)findViewById(R.id.test_name);
        rem_time = (TextView)findViewById(R.id.rem_time);
        final TextView full_marks = (TextView)findViewById(R.id.full_marks);
        final TextView total_q = (TextView)findViewById(R.id.total_questions);
        // final TextView total_attempt = (TextView)findViewById(R.id.total_attempt);
        final TextView correct_ans = (TextView)findViewById(R.id.correct);
        final TextView incorrect_ans = (TextView)findViewById(R.id.incorrect);
        final TextView marks_obtained = (TextView)findViewById(R.id.marks_obtained);
        final TextView total_time = (TextView)findViewById(R.id.total_time);
        final TextView total_attempt = (TextView)findViewById(R.id.total_attempt);

        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollViewLayout) ;
        pdfView = (PDFView)findViewById(R.id.question_pdf);


        // final ArrayAdapter<VideoListDataItem> adapter = new ExamTestFragment.Adapter();
        listView = findViewById(R.id.listview_answers);
        submitExam = (Button)findViewById(R.id.submitTest);
        RequestQueue queue = Volley.newRequestQueue(ExamActivity.this);
      //  Answer_Data answer_data;
        Answer_Data answer_data;
        final String[] answer_key = new String[1];
        final String[] answer_link = new String[1];

        
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp = response.getJSONArray("mock_test_pt");
                            Log.i("Temp", "" + response.toString());

                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                marks_correct_ans= buffer.getString("marks_correct_ans");
                                negative_mark= buffer.getString("negative_mark");
                                duration_text.setText(buffer.getString("duration")+" hours");
                                ques_pdf = buffer.getString("ques_pdf");
                                test_type = buffer.getString("test_type");
                                Log.i("test_type",test_type);
                                String total_question = buffer.getString("total_question");
                                Log.i("total_question 1",total_question);
                                test_id = buffer.getString("id");
                                test_name.setText(buffer.getString("name"));
                                Log.i(" Test id",buffer.getString("id"));
                                // Toast.makeText(getActivity(), test_id, Toast.LENGTH_SHORT).show();
                                Log.i("negative_mark",negative_mark);
                                //Uri uri = Uri.parse("/data/user/0/com.example.com.magnusias4/app_Images_PRK/NMMSSGuidelines.pdf");

                                Log.i("Temp_Length",""+temp.length());
                                total_questions = Integer.parseInt(total_question);
                                Log.i("total_question 2",total_question);
                               // Toast.makeText(ExamActivity.this, ""+total_questions, Toast.LENGTH_SHORT).show();
                                //  int j=0;

                                deducted_marks= Float.parseFloat(negative_mark);
                                marks = Float.parseFloat(marks_correct_ans);
//                                Log.i("negative_mark",""+);
                                // pdfView.fromAsset("magnus.pdf").load();
                                            if (test_type.equals("Subject wise test"))
                                                 answer_link[0] = "https://magnusias.com/app-api/subject-wise-objective-test-answer.php";

                                            if (test_type.equals("Section wise test"))
                                                answer_link[0] = "https://magnusias.com/app-api/section-wise-objective-test-answer.php";

                                             if (test_type.equals("Chapter wise test"))
                                                 answer_link[0] = "https://magnusias.com/app-api/chapter-wise-objective-test-answer.php";
                                                //answer_data.setAnswer_url(answer_url);
//                                Log.i("Test Answer",answer_url);
                              //  Toast.makeText(ExamActivity.this, answer_url, Toast.LENGTH_SHORT).show();

                                //setAnswer_url(answer_url);
                                new ExamActivity.RetrievePDF().execute(ques_pdf);



                                Log.i("ques_pdf",ques_pdf);

                                tq=total_questions;
                                // populateListView(objects,total_questions);

                               /* Cursor cursor = databaseHelper.getSavedAnswer(test_id);
                                Log.i("Cursor Count",""+cursor.getCount());
                             //   HashMap hashMap<String,String> = new HashMap<String,String>()
                                if (cursor.getCount()>0)
                                {
                                    HashMap<String,String> hashMap = new HashMap<String,String>();
                                    for(int l = 1;l<=cursor.getCount();l++)
                                    {
                                        hashMap.put(cursor.getString(0),cursor.getString(1));
                                    }
                                    for (Map.Entry m:hashMap.entrySet())
                                    {
                                        Log.i("Hashmap Values",m.getKey()+" "+m.getValue());
                                    }

                                }
                                else
                                {
                                    Log.i("Hashmap Values","No Value Found "+test_id);
                                }*/
                                for ( int j=1;j<=total_questions;j++) {
                                   // Log.i("J",""+j);
                                    objects.add(""+j);
                                }

                                adapter=new MyAdapter1(ExamActivity.this,0,objects);
                                listView.setAdapter(adapter);


                                loadAnswer(answer_link[0]);
                               //Log.i("answer_set_key",answer_set_key+"");
                              //  Toast.makeText(ExamActivity.this, answer_set_key, Toast.LENGTH_SHORT).show();

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

        queue.add(jsonObjectRequest);




        Log.i("total_question 3",""+answer_url);

      //  String link = answer_data.getAnswer_url();
      //  Toast.makeText(this, link, Toast.LENGTH_SHORT).show();
   /*     JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET,
                "https://magnusias.com/app-api/subject-wise-objective-test-answer.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Log.i("Answer", "" + response.toString());
                            JSONArray temp = response.getJSONArray("subject_wise_test_ans");
                            //      Toast.makeText(ExamActivity.this, answer_url, Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                JSONObject answer = buffer.getJSONObject("answer");

                                String id= buffer.getString("id");
                                String test_id1= buffer.getString("test_id");
                                Log.i("Test Id in answer",test_id1);
                                String subject_name = buffer.getString("subject_name");
                                String marks_pluse = buffer.getString("marks_pluse");
                                String marks_minus = buffer.getString("marks_minus");
                                String answer_set = buffer.getString("answer");
                                answer_key[0] = buffer.getString("answer_key");
                                q_no = buffer.getString("question_no");
                                String temp_string;
                                temp_string=test_id;
                                //   Log.i("Answer API",answer.toString());
                                Log.i("answer_key",""+ answer_key[0]);

                                if (test_id1.equals(test_id) ) {
                                    Toast.makeText(ExamActivity.this, "Answers are available! You can continue exam ", Toast.LENGTH_SHORT).show();
                                    Log.i("Answer Status","Answers Found in Answer Table");
                                    question_no="";

                                    for (int j=0;j<answer.toString().length();)
                                    {
                                        //if (j<Integer.parseInt(q_no))
                                        question_no = answer.getString(""+ (j+1));

                                        databaseHelper.setAnswerTableData((""+(j+1)),test_id1,""+subject_name,""+(++j),question_no);
                                        Log.i("Setting Answer",test_id1+" "+ (j+1)+" ");
                                    }

                                }  Log.i("QID",test_id+" "+subject_name);
                            }
//


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest2);
*/
        Long time = Long.parseLong(duration_text.getText().toString());
        Long exam_time = time*3600*1000;

        new CountDownTimer(exam_time,1000){
            @Override
            public void onTick(long l) {


                second++;
                if (second>59)
                {
                    minute++;


                    if (minute>59) {
                        hour++;
                        minute=0;
                    }
                    second=0;
                }
                rem_time.setText(hour+":"+minute+":"+second);

            }

            @Override
            public void onFinish() {

            }
        }.start();


        final int[] attempted_qustions = {0};
        final int[] total_questions = {0};
        final float f_marks =  (marks*total_questions[0]);
        final int[] correct = {0};
        final int[] incorrect = { 0 };

        final LinearLayout answerLayout = (LinearLayout)findViewById(R.id.answerLayout);
        final LinearLayout resultLayout = (LinearLayout)findViewById(R.id.resultLayout);

         showAnswer = findViewById(R.id.showAnswer);
         if ((LoginSession.answer_key_pdf == null)||(LoginSession.answer_key_pdf.equals("")))
         {
             showAnswer.setVisibility(View.INVISIBLE);
         }

         showAnswer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent intent = new Intent(ExamActivity.this,AnswerPDF.class);
                 intent.putExtra("link",LoginSession.answer_key_pdf);
                 intent.putExtra("linkType","online");
                 startActivity(intent);
             }
         });


        submitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                answerLayout.setVisibility(View.GONE);
                resultLayout.setVisibility(View.VISIBLE);
                Cursor cursor = databaseHelper.getAnswer(test_id,"1");
                showAnswer.setVisibility(View.VISIBLE);
                total_questions[0] =cursor.getCount();
                Log.i("Total Question",""+total_questions[0]);
                Log.i("Maximum Marks",""+marks);
                int question_count=0;

                while (cursor.moveToNext())
                {
                    Log.i("Col 1", cursor.getString(0));
                    //Log.i("Col 2", cursor.getString(1));
                    String correct_answer = cursor.getString(0);
                    String selected_answer = cursor.getString(1);

                    Log.i("correct_answer n marks",correct_answer+" "+marks);
                    Log.i("selected_answer n marks",selected_answer+" "+ negative_mark);

                    if (correct_answer.equals(selected_answer))
                    {
                        total_marks +=  marks;
                        attempted_qustions[0]++;
                        correct[0]++;
                        //Log.i("Result","True");
                    }
                    else
                    {
                        if (selected_answer== null)
                            break;
                        total_marks-=deducted_marks;
                        // Log.i("Result","False");
                        attempted_qustions[0]++;
                        incorrect[0]++;
                    }
                }
                cursor.moveToFirst();
                do{
                    question_count++;
                    if (cursor.getString(1) == null)
                    {
                        null_counter++;
                    }

                }while(cursor.moveToNext());
                Log.i("Null Counter",""+null_counter);
                attempt_counter = question_count - null_counter;

                //int toq= cursor.getCount();
                //  attempt_counter = toq-null_counter;
                Log.i("Answer cursor result",test_id+" "+ total_marks);
                Log.i("Maximum Marks",""+marks *total_questions[0]);
                // scrollView.setVisibility(View.GONE);
                full_marks.setText(""+marks *total_questions[0]);
                total_q.setText(""+total_questions[0]);
                //   total_attempt.setText(""+attempted_qustions[0]);
                correct_ans.setText(""+correct[0]);
                incorrect_ans.setText(""+incorrect[0]);
                marks_obtained.setText(""+Math.round(total_marks*100)/100.0);
                total_time.setText(duration_text.getText());
                total_attempt.setText(""+attempt_counter);
                }catch(Exception e){Log.i("Exam Submit Exception",e.toString());}
                //


            }
        });

    }

    void loadAnswer(final String answer_url){
        final String[] answer_key = new String[1];
        RequestQueue queue = Volley.newRequestQueue(ExamActivity.this);
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET,
                answer_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Log.i("Answer", "" + response.toString());
                          //  Toast.makeText(ExamActivity.this, answer_url, Toast.LENGTH_SHORT).show();
                            JSONArray temp = response.getJSONArray("test_ans");

                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                JSONObject answer = buffer.getJSONObject("answer");

                                String id= buffer.getString("id");
                                String test_id1= buffer.getString("test_id");
                                Log.i("Test Id in answer",test_id1);
                                String subject_name = buffer.getString("name");
                                String marks_pluse = buffer.getString("marks_pluse");
                                String marks_minus = buffer.getString("marks_minus");
                                String answer_set = buffer.getString("answer");
                                answer_key[0] = buffer.getString("answer_key");
                                q_no = buffer.getString("question_no");
                                String temp_string;
                                temp_string=test_id;
                                //   Log.i("Answer API",answer.toString());
                                Log.i("answer_key",""+ answer_key[0]);
                                LoginSession.answer_key_pdf = answer_key[0];
                                if (test_id1.equals(test_id) ) {
                                   // Toast.makeText(ExamActivity.this, "Answers are available! You can continue exam ", Toast.LENGTH_SHORT).show();
                                 //   Toast.makeText(ExamActivity.this, "Answer key "+answer_key[0], Toast.LENGTH_SHORT).show();
                                    Log.i("Answer Status","Answers Found in Answer Table");
                                    question_no="";

                                    for (int j=0;j<answer.toString().length();)
                                    {
                                        //if (j<Integer.parseInt(q_no))
                                        question_no = answer.getString(""+ (j+1));

                                        databaseHelper.setAnswerTableData((""+(j+1)),test_id1,""+subject_name,""+(++j),question_no);
                                        Log.i("Setting Answer",test_id1+" "+ (j+1)+" ");
                                    }

                                }  Log.i("QID",test_id+" "+subject_name);
                            }

                            //


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest2);

            Log.i("LoginSession answer_key",""+LoginSession.answer_key_pdf);


    }

    String getAnswer_url(String answer_url)
    {
        this.answer_url_0 = answer_url;
        return this.answer_url_0;
    }
    void setAnswer_url( String answer_url){
        answer_url_0= answer_url;
        Log.i("answer_url_0",answer_url_0);
        Log.i("answer_url",answer_url_0);
        String ans = getAnswer_url(answer_url_0);
        //Toast.makeText(this, ans, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
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
            pdfView.fitToWidth();

             pdfView.setMinZoom(5);

        }
    }


    class MyAdapter1 extends ArrayAdapter<String> {
        Context cn;
        List<String> objects;


        public MyAdapter1(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            this.objects=objects;
            cn=context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) cn
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.sample_answer_fragment, parent, false);
            final TextView textView = (TextView) rowView.findViewById(R.id.serial_option);
            final RadioButton rbtn = (RadioButton) rowView.findViewById(R.id.optionA);
            final RadioButton rbtn2 = (RadioButton) rowView.findViewById(R.id.optionB);
            final RadioButton rbtn3 = (RadioButton) rowView.findViewById(R.id.optionC);
            final RadioButton rbtn4 = (RadioButton) rowView.findViewById(R.id.optionD);
            RadioGroup radioGroup = (RadioGroup) rowView.findViewById(R.id.radiogroup);


            textView.setText(objects.get(position));


            rbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ExamActivity) cn).ob.remove(position+"##"+4);

                    ((ExamActivity) cn).ob.remove(position+"##"+3);
                    ((ExamActivity) cn).ob.remove(position+"##"+2);
                    if(((ExamActivity)cn).ob.contains(position+"##"+1))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+1);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn.getText());
                }
            });
            rbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ExamActivity) cn).ob.remove(position+"##"+4);

                    ((ExamActivity) cn).ob.remove(position+"##"+3);
                    ((ExamActivity) cn).ob.remove(position+"##"+1);
                    if(((ExamActivity)cn).ob.contains(position+"##"+2))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+2);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn2.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn2.getText());
                }
            });
            rbtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ExamActivity) cn).ob.remove(position+"##"+4);

                    ((ExamActivity) cn).ob.remove(position+"##"+1);
                    ((ExamActivity) cn).ob.remove(position+"##"+2);
                    if(((ExamActivity)cn).ob.contains(position+"##"+3))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+3);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn3.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn3.getText());
                }
            });

            rbtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ExamActivity) cn).ob.remove(position+"##"+1);
                    ((ExamActivity) cn).ob.remove(position+"##"+2);
                    ((ExamActivity) cn).ob.remove(position+"##"+3);
                    if(((ExamActivity)cn).ob.contains(position+"##"+4))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+4);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn4.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn4.getText());
                }
            });

            if(((ExamActivity)cn).ob.contains(position+"##"+1)){
                //Log.e("value","true");
                rbtn.setChecked(true);
            }
            else
            {
                //Log.e("value","false");
                rbtn.setChecked(false);
            }
            if(((ExamActivity)cn).ob.contains(position+"##"+2)){
                rbtn2.setChecked(true);
            }
            else
            {
                rbtn2.setChecked(false);
            }
            if(((ExamActivity)cn).ob.contains(position+"##"+3)){
                rbtn3.setChecked(true);
            }
            else
            {
                rbtn3.setChecked(false);
            }
            if(((ExamActivity)cn).ob.contains(position+"##"+4)){
                rbtn4.setChecked(true);
            }
            else
            {
                rbtn4.setChecked(false);
            }


            return rowView;
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
                // pdfView.setMinZoom(5);

            }
        }

    }


    class Answer_Data{
        String answer_url;

        public String getAnswer_url() {
            return answer_url;
        }

        public void setAnswer_url(String answer_url) {
            this.answer_url = answer_url;
        }
    }

}












/*
package pranay.example.com.magnusias4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    List<String> objects;
    public List<String> ob;
    int total_questions,tq;
    String ques_pdf;
    PDFView pdfView;
  //  private List<VideoListDataItem> dataFeed= new ArrayList<VideoListDataItem>();
    ListView listView;
    String test_id,marked_answer;
    String q_no;
    String duration,subject;
    int hour,minute,second;
    int attempt_counter=0;
    int null_counter = 0;
    String question_no;
    String marks_correct_ans,negative_mark;
    TextView duration_text,test_name,rem_time;
    float deducted_marks,marks,total_marks=0;
    DatabaseHelper databaseHelper;
    MyAdapter1 adapter;
    LinearLayout answerLayout;
    LinearLayout resultLayout;
    Cursor cursor;
    int attempted_qustions = 0;

    int correct = 0;
    int incorrect = 0 ;

    TextView full_marks,total_q,correct_ans,incorrect_ans,marks_obtained,total_time,total_attempt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
      */
/*  RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclyer) ;
        recyclerView.setNestedScrollingEnabled(false);
       *//*
 //Bundle bundle = getArguments();
        databaseHelper = new DatabaseHelper(ExamActivity.this);
        databaseHelper.createAnswerTable();
        Button submitExam;
        ob=new ArrayList<String>();

        objects=new ArrayList<>();
        Intent intent = getIntent();

        String url =intent.getStringExtra("url");
        duration_text = (TextView)findViewById(R.id.maximum_marks);
        test_name = (TextView)findViewById(R.id.test_name);
        rem_time = (TextView)findViewById(R.id.rem_time);
         full_marks = (TextView)findViewById(R.id.full_marks);
         total_q = (TextView)findViewById(R.id.total_questions);
        // final TextView total_attempt = (TextView)findViewById(R.id.total_attempt);
        correct_ans = (TextView)findViewById(R.id.correct);
         incorrect_ans = (TextView)findViewById(R.id.incorrect);
         marks_obtained = (TextView)findViewById(R.id.marks_obtained);
         total_time = (TextView)findViewById(R.id.total_time);
         total_attempt = (TextView)findViewById(R.id.total_attempt);

        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollViewLayout) ;
        pdfView = (PDFView)findViewById(R.id.question_pdf);
       // final ArrayAdapter<VideoListDataItem> adapter = new ExamTestFragment.Adapter();
        listView = findViewById(R.id.listview_answers);
        submitExam = (Button)findViewById(R.id.submitTest);
        RequestQueue queue = Volley.newRequestQueue(ExamActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp = response.getJSONArray("mock_test_pt");
                            Log.i("Temp", "" + response.toString());
//                          ;
                            //  prepareAlbums(dataArray);

                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                marks_correct_ans= buffer.getString("marks_correct_ans");
                                negative_mark= buffer.getString("negative_mark");
                                duration_text.setText(buffer.getString("duration")+" hours");
                                ques_pdf = buffer.getString("ques_pdf");
                                String total_question = buffer.getString("total_question");
                                Log.i("total_question 1",total_question);
                                test_id = buffer.getString("id");
                                test_name.setText(buffer.getString("name"));
                                Log.i(" Test id",buffer.getString("id"));
                                // Toast.makeText(getActivity(), test_id, Toast.LENGTH_SHORT).show();
                                Log.i("negative_mark",negative_mark);
                                //Uri uri = Uri.parse("/data/user/0/com.example.com.magnusias4/app_Images_PRK/NMMSSGuidelines.pdf");

                                Log.i("Temp_Length",""+temp.length());
                                total_questions = Integer.parseInt(total_question);
                                Log.i("total_question 2",total_question);
                                Toast.makeText(ExamActivity.this, ""+total_questions, Toast.LENGTH_SHORT).show();
                                  //  int j=0;

                                deducted_marks= Float.parseFloat(negative_mark);
                                marks = Float.parseFloat(marks_correct_ans);
//                                Log.i("negative_mark",""+);
                                // pdfView.fromAsset("magnus.pdf").load();
                                new ExamActivity.RetrievePDF().execute(ques_pdf);
                                Log.i("ques_pdf",ques_pdf);

                                tq=total_questions;
                               // populateListView(objects,total_questions);
                                for ( int j=1;j<=total_questions;j++) {
                                    Log.i("J",""+j);
                                    objects.add(""+j);
                                }

                                adapter=new MyAdapter1(ExamActivity.this,0,objects);
                                listView.setAdapter(adapter);


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

        queue.add(jsonObjectRequest);
        Log.i("total_question 3",""+tq);

        Log.i("Test paper","After loading pdf");

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com/app-api/subject-wise-objective-test-answer.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Answer", "" + response.toString());
                            JSONArray temp = response.getJSONArray("subject_wise_test_ans");

                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                JSONObject answer = buffer.getJSONObject("answer");

                                String id= buffer.getString("id");
                                String test_id1= buffer.getString("test_id");
                                Log.i("Test Id in answer",test_id1);
                                String subject_name = buffer.getString("subject_name");
                                String marks_pluse = buffer.getString("marks_pluse");
                                String marks_minus = buffer.getString("marks_minus");
                                String answer_set = buffer.getString("answer");
                                q_no = buffer.getString("question_no");
                                String temp_string;
                                temp_string=test_id;
                                Log.i("Answer API",answer.toString());
                                Log.i("subject_name",""+subject_name);

                                if (test_id1.equals(test_id) ) {
                                    Toast.makeText(ExamActivity.this, "Answers are available! You can continue exam ", Toast.LENGTH_SHORT).show();
                                    question_no="";

                                    for (int j=0;j<answer.toString().length();)
                                    {
                                        //if (j<Integer.parseInt(q_no))
                                        question_no = answer.getString(""+ (j+1));

                                        databaseHelper.setAnswerTableData((""+(j+1)),test_id1,""+subject_name,""+(++j),question_no);
                                         Log.i("Setting Answer",test_id1+" "+ (j+1)+" ");
                                    }

                                }  Log.i("QID",test_id+" "+subject_name);
                            }
//


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest2);

        Long time = Long.parseLong(duration_text.getText().toString());
        Long exam_time = time*3600*1000;

        new CountDownTimer(exam_time,1000){
            @Override
            public void onTick(long l) {


                second++;
                if (second>59)
                {
                    minute++;


                    if (minute>59) {
                        hour++;
                        minute=0;
                    }
                    second=0;
                }
                rem_time.setText(hour+":"+minute+":"+second);

            }

            @Override
            public void onFinish() {

            }
        }.start();



        //final float f_marks =  (marks*total_questions);

        total_questions = 0;
        answerLayout = (LinearLayout)findViewById(R.id.answerLayout);
        resultLayout = (LinearLayout)findViewById(R.id.resultLayout);

        submitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



          //  showResult();

                //


            }
        });


    }

    public void showResult(){

        answerLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        cursor = databaseHelper.getAnswer(test_id,"1");
        total_questions =cursor.getCount();
        Log.i("Total Question",""+total_questions);
        Log.i("Maximum Marks",""+marks);
        int question_count=0;

        while (cursor.moveToNext())
        {
            Log.i("Col 1", cursor.getString(0));
            //Log.i("Col 2", cursor.getString(1));
            String correct_answer = cursor.getString(0);
            String selected_answer = cursor.getString(1);

            Log.i("correct_answer n marks",correct_answer+" "+marks);
            Log.i("selected_answer n marks",selected_answer+" "+ negative_mark);

            if (correct_answer.equals(selected_answer))
            {
                total_marks +=  marks;
                attempted_qustions++;
                correct++;
                //Log.i("Result","True");
            }
            else
            {
                if (selected_answer== null)
                    break;
                total_marks-=deducted_marks;
                // Log.i("Result","False");
                attempted_qustions++;
                incorrect++;
            }
        }
        cursor.moveToFirst();
        do{
            question_count++;
            if (cursor.getString(1) == null)
            {
                null_counter++;
            }

        }while(cursor.moveToNext());
        Log.i("Null Counter",""+null_counter);
        attempt_counter = question_count - null_counter;

        //int toq= cursor.getCount();
        //  attempt_counter = toq-null_counter;
        Log.i("Answer cursor result",test_id+" "+ total_marks);
        Log.i("Maximum Marks",""+marks *total_questions);
        // scrollView.setVisibility(View.GONE);
        full_marks.setText(""+marks *total_questions);
        total_q.setText(""+total_questions);
        //   total_attempt.setText(""+attempted_qustions[0]);
        correct_ans.setText(""+correct);
        incorrect_ans.setText(""+incorrect);
        marks_obtained.setText(""+Math.round(total_marks*100)/100.0);
        total_time.setText(duration_text.getText());
        total_attempt.setText(""+attempt_counter);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            final View view = LayoutInflater.from(ExamActivity.this).inflate(R.layout.test_result,null);

            AlertDialog.Builder closeConfirm = new AlertDialog.Builder(this);
            closeConfirm.setCancelable(false)
                    .setTitle("Do you want to submit the exam?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                   // answerLayout.setVisibility(View.GONE);
                                    //resultLayout.setVisibility(View.VISIBLE);
                                    full_marks = (TextView)view.findViewById(R.id.full_marks);
                                    total_q = (TextView)view.findViewById(R.id.total_questions);
                                    correct_ans = (TextView)view.findViewById(R.id.correct);
                                    incorrect_ans = (TextView)view.findViewById(R.id.incorrect);
                                    marks_obtained = (TextView)view.findViewById(R.id.marks_obtained);
                                    total_time = (TextView)view.findViewById(R.id.total_time);
                                    total_attempt = (TextView)view.findViewById(R.id.total_attempt);
                                    cursor = databaseHelper.getAnswer(test_id,"1");
                                    total_questions =cursor.getCount();
                                    Log.i("Total Question",""+total_questions);
                                    Log.i("Maximum Marks",""+marks);
                                    int question_count=0;

                                    while (cursor.moveToNext())
                                    {
                                        Log.i("Col 1", cursor.getString(0));
                                        //Log.i("Col 2", cursor.getString(1));
                                        String correct_answer = cursor.getString(0);
                                        String selected_answer = cursor.getString(1);

                                        Log.i("correct_answer n marks",correct_answer+" "+marks);
                                        Log.i("selected_answer n marks",selected_answer+" "+ negative_mark);

                                        if (correct_answer.equals(selected_answer))
                                        {
                                            total_marks +=  marks;
                                            attempted_qustions++;
                                            correct++;
                                            //Log.i("Result","True");
                                        }
                                        else
                                        {
                                            if (selected_answer== null)
                                                break;
                                            total_marks-=deducted_marks;
                                            // Log.i("Result","False");
                                            attempted_qustions++;
                                            incorrect++;
                                        }
                                    }
                                    cursor.moveToFirst();
                                    do{
                                        question_count++;
                                        if (cursor.getString(1) == null)
                                        {
                                            null_counter++;
                                        }

                                    }while(cursor.moveToNext());
                                    Log.i("Null Counter",""+null_counter);
                                    attempt_counter = question_count - null_counter;

                                    //int toq= cursor.getCount();
                                    //  attempt_counter = toq-null_counter;
                                    Log.i("Answer cursor result",test_id+" "+ total_marks);
                                    Log.i("Maximum Marks",""+marks *total_questions);
                                    // scrollView.setVisibility(View.GONE);
                                    full_marks.setText(""+marks *total_questions);
                                    Log.i("Full Marks",""+marks *total_questions);
                                    total_q.setText(""+total_questions);
                                    //   total_attempt.setText(""+attempted_qustions[0]);
                                    correct_ans.setText(""+correct);
                                    incorrect_ans.setText(""+incorrect);
                                    marks_obtained.setText(""+Math.round(total_marks*100)/100.0);
                                    total_time.setText(duration_text.getText());
                                    total_attempt.setText(""+attempt_counter);

                                    AlertDialog.Builder displayResult = new AlertDialog.Builder(ExamActivity.this);
                             displayResult.setTitle("Test Result")
                                     .setView(view)
                                     .setCancelable(false)
                                     .setPositiveButton("Close Result", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialogInterface, int i) {
                                             finish();
                                         }
                                     })
                                     .show();

                                }
                            }


                    )
                    .setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(ExamActivity.this, "Exam Submission Cancelled!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .show();




           // Toast.makeText(this, "Back button pressed!!!", Toast.LENGTH_SHORT).show();

        }
        return super.onKeyDown(keyCode, event);
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
            // pdfView.setMinZoom(5);

        }
    }


   class MyAdapter1 extends ArrayAdapter<String> {
        Context cn;
        List<String> objects;


        public MyAdapter1(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            this.objects=objects;
            cn=context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) cn
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.sample_answer_fragment, parent, false);
            final TextView textView = (TextView) rowView.findViewById(R.id.serial_option);
            final RadioButton rbtn = (RadioButton) rowView.findViewById(R.id.optionA);
            final RadioButton rbtn2 = (RadioButton) rowView.findViewById(R.id.optionB);
            final RadioButton rbtn3 = (RadioButton) rowView.findViewById(R.id.optionC);
            final RadioButton rbtn4 = (RadioButton) rowView.findViewById(R.id.optionD);
            RadioGroup radioGroup = (RadioGroup) rowView.findViewById(R.id.radiogroup);


            textView.setText(objects.get(position));


            rbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ExamActivity) cn).ob.remove(position+"##"+4);

                    ((ExamActivity) cn).ob.remove(position+"##"+3);
                    ((ExamActivity) cn).ob.remove(position+"##"+2);
                    if(((ExamActivity)cn).ob.contains(position+"##"+1))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+1);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn.getText());
                }
            });
            rbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ExamActivity) cn).ob.remove(position+"##"+4);

                    ((ExamActivity) cn).ob.remove(position+"##"+3);
                    ((ExamActivity) cn).ob.remove(position+"##"+1);
                    if(((ExamActivity)cn).ob.contains(position+"##"+2))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+2);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn2.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn2.getText());
                }
            });
            rbtn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ExamActivity) cn).ob.remove(position+"##"+4);

                    ((ExamActivity) cn).ob.remove(position+"##"+1);
                    ((ExamActivity) cn).ob.remove(position+"##"+2);
                    if(((ExamActivity)cn).ob.contains(position+"##"+3))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+3);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn3.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn3.getText());
                }
            });

            rbtn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ExamActivity) cn).ob.remove(position+"##"+1);
                    ((ExamActivity) cn).ob.remove(position+"##"+2);
                    ((ExamActivity) cn).ob.remove(position+"##"+3);
                    if(((ExamActivity)cn).ob.contains(position+"##"+4))
                    {

                    }
                    else {
                        ((ExamActivity) cn).ob.add(position+"##"+4);
                    }

                    databaseHelper.saveAnswer(textView.getText().toString(),test_id,rbtn4.getText().toString());
                    Log.i("Result",textView.getText().toString()+" " +test_id+" "+ rbtn4.getText());
                }
            });

            if(((ExamActivity)cn).ob.contains(position+"##"+1)){
                Log.e("valuee","truee");
                rbtn.setChecked(true);
            }
            else
            {
                Log.e("valuee","false");
                rbtn.setChecked(false);
            }
            if(((ExamActivity)cn).ob.contains(position+"##"+2)){
                rbtn2.setChecked(true);
            }
            else
            {
                rbtn2.setChecked(false);
            }
            if(((ExamActivity)cn).ob.contains(position+"##"+3)){
                rbtn3.setChecked(true);
            }
            else
            {
                rbtn3.setChecked(false);
            }
            if(((ExamActivity)cn).ob.contains(position+"##"+4)){
                rbtn4.setChecked(true);
            }
            else
            {
                rbtn4.setChecked(false);
            }


            return rowView;
        }
    }


}
*/
