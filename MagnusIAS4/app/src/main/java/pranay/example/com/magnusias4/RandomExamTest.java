package pranay.example.com.magnusias4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    TextView random_question,options,hints;
    EditText answer;
    Button verify_answer;
    private List<RandomTest> dataFeed= new ArrayList<RandomTest>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.random_exam_test, container, false);
        random_question = (TextView)view.findViewById(R.id.random_question);
        options = (TextView)view.findViewById(R.id.options);
        hints = (TextView)view.findViewById(R.id.hints);
        verify_answer = (Button)view.findViewById(R.id.verify_answer);
        final ArrayAdapter<RandomTest> adapter = new RandomExamTest.Adapter();


        Bundle bundle = getArguments();
        String url = bundle.getString("url");
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
            random_question = (TextView)convertView.findViewById(R.id.random_question);
            options = (TextView)convertView.findViewById(R.id.options);
            hints = (TextView)convertView.findViewById(R.id.hints);
            verify_answer = (Button)convertView.findViewById(R.id.verify_answer);
            answer = (EditText)convertView.findViewById(R.id.answer);
            RandomTest currentItem = dataFeed.get(position);
            random_question.setText(currentItem.getQuestion());
            options.setText(currentItem.getOptions());
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
