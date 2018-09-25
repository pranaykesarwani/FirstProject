package pranay.example.com.magnusias4;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamTestFragment extends Fragment {
    List<String> objects;
    public List<String> ob;

    String ques_pdf;
    PDFView pdfView;
    private List<VideoListDataItem> dataFeed= new ArrayList<VideoListDataItem>();
    ListView listView;
    String test_id,marked_answer;
    String q_no;
    String duration,subject;
    int hour,minute,second;
    String question_no;
    String marks_correct_ans,negative_mark;
    TextView duration_text,test_name,rem_time;
    float deducted_marks,marks,total_marks=0;
    DatabaseHelper databaseHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
         databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.createAnswerTable();
        Button submitExam;
        ob=new ArrayList<String>();

        objects=new ArrayList<>();

       /* String name = bundle.getString("name");
        String total_question = bundle.getString("total_question");
        String marks_correct_ans = bundle.getString("marks_correct_ans");
        String negative_mark = bundle.getString("negative_mark");
        String duration = bundle.getString("duration");
        String ques_pdf = bundle.getString("ques_pdf");*/

       String url  = bundle.getString("url");
        Log.i("Exam Test URL",url);
        View view = inflater.inflate(R.layout.fragment_exam_test, container, false);
         duration_text = view.findViewById(R.id.maximum_marks);
         test_name = view.findViewById(R.id.test_name);
         rem_time = view.findViewById(R.id.rem_time);
         final TextView full_marks = (TextView)view.findViewById(R.id.full_marks);
        final TextView total_q = (TextView)view.findViewById(R.id.total_questions);
       // final TextView total_attempt = (TextView)view.findViewById(R.id.total_attempt);
        final TextView correct_ans = (TextView)view.findViewById(R.id.correct);
        final TextView incorrect_ans = (TextView)view.findViewById(R.id.incorrect);
        final TextView marks_obtained = (TextView)view.findViewById(R.id.marks_obtained);
                final TextView total_time = (TextView)view.findViewById(R.id.total_time);
         final ScrollView scrollView = (ScrollView)view.findViewById(R.id.scrollViewLayout) ;
        pdfView = (PDFView)view.findViewById(R.id.question_pdf);
        final ArrayAdapter<VideoListDataItem> adapter = new ExamTestFragment.Adapter();
        listView = view.findViewById(R.id.listview_answers);
        submitExam = (Button)view.findViewById(R.id.submitTest);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                                 test_id = buffer.getString("id");
                                 test_name.setText(buffer.getString("name"));
                                Log.i(" Test id",buffer.getString("id"));
                               // Toast.makeText(getActivity(), test_id, Toast.LENGTH_SHORT).show();
                                Log.i("negative_mark",negative_mark);
                                //Uri uri = Uri.parse("/data/user/0/com.example.com.magnusias4/app_Images_PRK/NMMSSGuidelines.pdf");

                                int total_questions = Integer.parseInt(total_question);
                                 deducted_marks= Float.parseFloat(negative_mark);
                                marks = Float.parseFloat(marks_correct_ans);
//                                Log.i("negative_mark",""+);
                                for (int j=1;j<=total_questions;j++) {
                                    dataFeed.add(new VideoListDataItem("" + j, "", "", "", "", ""));
                                    adapter.notifyDataSetChanged();

                                }
                                // pdfView.fromAsset("magnus.pdf").load();
                                new RetrievePDF().execute(ques_pdf);
                                Log.i("ques_pdf",ques_pdf);


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
                                    Toast.makeText(getActivity(), "Matched!!!", Toast.LENGTH_SHORT).show();
                                     question_no="";

                                    for (int j=0;j<answer.toString().length();)
                                    {
                                        //if (j<Integer.parseInt(q_no))
                                        question_no = answer.getString(""+ (j+1));

                                            databaseHelper.setAnswerTableData((""+(j+1)),test_id1,""+subject_name,""+(++j),question_no);
                                       // Log.i("Answer",test_id1+" "+ (j+1)+" "+q);
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


        //Toast.makeText(getActivity(), ques_pdf, Toast.LENGTH_SHORT).show();
      //  adapter.notifyDataSetChanged();

        listView.setAdapter( adapter);
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


        submitExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = databaseHelper.getAnswer(test_id,"1");
                    total_questions[0] =cursor.getCount();
                    Log.i("Total Question",""+total_questions[0]);
                    Log.i("Maximum Marks",""+marks);
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
                Log.i("Answer cursor result",test_id+" "+ total_marks);
                Log.i("Maximum Marks",""+marks *total_questions[0]);
                scrollView.setVisibility(View.GONE);
                full_marks.setText(""+marks *total_questions[0]);
                total_q.setText(""+total_questions[0]);
             //   total_attempt.setText(""+attempted_qustions[0]);
                correct_ans.setText(""+correct[0]);
                incorrect_ans.setText(""+incorrect[0]);
                marks_obtained.setText(""+total_marks);
                total_time.setText(duration_text.getText()+" hour");

            //


            }
        });


        return view;
    }

    private class Adapter extends ArrayAdapter<VideoListDataItem> {

        public Adapter() {
            super(getActivity(), R.layout.sample_answer_fragment,dataFeed);
        }


        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.sample_answer_fragment,null,false);
            }
            View view =getLayoutInflater().inflate(R.layout.sample_answer_fragment,null,false);

            ViewHolder viewHolder;
            viewHolder = new ViewHolder();

            //HashMap<String,String> answer;
           // answer.put()
           /* viewHolder.question_serial = (TextView) convertView.findViewById(R.id.serial_option);
            viewHolder.radioButtonA = (RadioButton)convertView.findViewById(R.id.optionA);
            viewHolder.radioButtonB = (RadioButton)convertView.findViewById(R.id.optionB);
            viewHolder.radioButtonC = (RadioButton)convertView.findViewById(R.id.optionC);
            viewHolder.radioButtonD = (RadioButton)convertView.findViewById(R.id.optionD);
            viewHolder.removeAnswer = (ImageView)convertView.findViewById(R.id.del_option);*/

            VideoListDataItem currentItem = dataFeed.get(position);
            final RadioGroup radioGroup = (RadioGroup)convertView.findViewById(R.id.radiogroup);

            ImageView removeAnswer = (ImageView)convertView.findViewById(R.id.del_option);
            final TextView question_no = (TextView) convertView.findViewById(R.id.serial_option);
            //TextView answer = (TextView) convertView.findViewById(R.id.faq_answer);
            //ImageView imageView = (ImageView) convertView.findViewById(R.id.videoThumbnail);
            //  VideoView videoView = (VideoView)convertView.findViewById(R.id.chapterVideoList);
            final RadioButton radioButtonA = (RadioButton)convertView.findViewById(R.id.optionA) ;
            final RadioButton radioButtonB = (RadioButton)convertView.findViewById(R.id.optionB) ;
            final RadioButton radioButtonC = (RadioButton)convertView.findViewById(R.id.optionC) ;
            final RadioButton radioButtonD = (RadioButton)convertView.findViewById(R.id.optionD) ;
            final RadioButton[] radioButton = new RadioButton[1];
            final int[] selectedId = new int[1];
            final View finalConvertView = convertView;
            question_no.setText(currentItem.getId());

            removeAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioGroup.clearCheck();
                    databaseHelper.saveAnswer(question_no.getText().toString(),test_id,null);
                }
            });


            radioButtonA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    marked_answer = radioButtonA.getText().toString();
                    Toast.makeText(getActivity(), question_no.getText().toString()+" "+marked_answer+" "+position, Toast.LENGTH_SHORT).show();
                    databaseHelper.saveAnswer(question_no.getText().toString(),test_id,marked_answer);
                }
            });
            radioButtonB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    marked_answer = radioButtonB.getText().toString();
                    Toast.makeText(getActivity(), question_no.getText().toString()+" "+marked_answer+" "+position, Toast.LENGTH_SHORT).show();
                      databaseHelper.saveAnswer(question_no.getText().toString(),test_id,marked_answer);

                }
            });
            radioButtonC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    marked_answer = radioButtonC.getText().toString();
                    Toast.makeText(getActivity(), question_no.getText().toString()+" "+marked_answer+" "+position, Toast.LENGTH_SHORT).show();

                    databaseHelper.saveAnswer(question_no.getText().toString(),test_id,marked_answer);

                }
            });
            radioButtonD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    marked_answer = radioButtonD.getText().toString();
                    Toast.makeText(getActivity(), question_no.getText().toString()+" "+marked_answer+" "+position, Toast.LENGTH_SHORT).show();
                    databaseHelper.saveAnswer(question_no.getText().toString(),test_id,marked_answer);

                }
            });
        //    final RadioButton[] radioButton = new RadioButton[1];

           radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
/*
                     selectedId[0] = radioGroup.getCheckedRadioButtonId();

                    radioButton[0] = (RadioButton) finalConvertView.findViewById(selectedId[0]);
                    marked_answer = radioButton[0].getText().toString();
                    Log.i("marked_answer",question_no+" "+marked_answer);
*/
                 //   databaseHelper.saveAnswer(question_no.getText().toString(),test_id,marked_answer);

/*
                    if (radioButtonA.isSelected())
                        marked_answer = radioButtonA.getText().toString();
                    else if(radioButtonB.isSelected())
                        marked_answer = radioButtonB.getText().toString();
                    else if(radioButtonA.isSelected())
                        marked_answer = radioButton[0].getText().toString();
                    else if(radioButtonA.isSelected())
                        marked_answer = radioButton[0].getText().toString();
                    else if */
                 Toast.makeText(getActivity(),question_no.getText().toString() , Toast.LENGTH_SHORT).show();
                   //

                }
            });

          /*  answer.setText(Html.fromHtml(currentItem.getVideo_id(),Html.FROM_HTML_MODE_COMPACT));
          */  //  Log.i("Answer",currentItem.getVideo_id());
            // imageView.setImageResource(currentItem.getImageId());
            // Toast.makeText(MainActivity.this,currentItem.getImageURL().toString(),Toast.LENGTH_SHORT).show();
            // Log.i("image", String.valueOf(currentItem.getImageId()));
//                 Picasso.get().load(currentItem.getVideoThumbnail()).into(imageView);
            // Toast.makeText(getActivity(), ""+listView.getAdapter().getCount(), Toast.LENGTH_SHORT).show();

            return convertView;
        }
        private class ViewHolder {
            TextView question_serial;
            RadioGroup radioGroup;
            RadioButton radioButtonA, radioButtonB, radioButtonC,radioButtonD;
            ImageView removeAnswer;
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
           // pdfView.setMinZoom(5);

        }
    }
}
