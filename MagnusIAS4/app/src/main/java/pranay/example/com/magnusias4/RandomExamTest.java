package pranay.example.com.magnusias4;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RandomExamTest extends Fragment {
    TextView random_question,option1,option2,option3,option4,hints,txtDescription;
    EditText answer;
    Button verify_answer;
    private List<RandomTest> dataFeed= new ArrayList<RandomTest>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        Log.i("bundle",url);

        View view = inflater.inflate(R.layout.random_exam_test, container, false);
        /*random_question = (TextView)view.findViewById(R.id.random_question);
        hints = (TextView)view.findViewById(R.id.hints);
        verify_answer = (Button)view.findViewById(R.id.verify_answer);
        */final ArrayAdapter<RandomTest> adapter = new RandomExamTest.Adapter();

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
                        String video_url = buffer.getString("video_url");
                        String hints = buffer.getString("hints");

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

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


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

            hints = (TextView)convertView.findViewById(R.id.hints);
            verify_answer = (Button)convertView.findViewById(R.id.verify_answer);
            answer = (EditText)convertView.findViewById(R.id.answer);
            final RandomTest currentItem = dataFeed.get(position);
            Log.i("options",currentItem.getOptions());
            verify_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (answer.getText().toString().equals(currentItem.getAnswers())) {
                        descriptionLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Correct Answer", Toast.LENGTH_SHORT).show();
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

            return  convertView;
        }
    }


}
