package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MethodologyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.fragment_methodology, container, false);

        TextView textView = (TextView)view.findViewById(R.id.methodology_details);
        textView.setText(Html.fromHtml("<div class=\"mb-50\" id=\"section-one\"><h3 style=\"text-align: center;\">Methodology of Teaching<\\/h3><br><br> <p>Moved by the plight of the young bright minds, we at Magnus IAS, have devised an innovatively improvised take on the methodology of teaching through &ldquo;Smart Audio Video Education&rdquo; complemented with written concise key notes depicting key concepts and key words.<\\/p> <p>This approach has delivered remarkable results as experienced from full-fledged trials with several students. In fact, with this methodology, the students find themselves more equipped than any other method to face the challenges in the Civil Services examination. Before bringing this concept to you, much research work has been done on the pedagogy, content of lectures, standard of keynotes and other aspects.<\\/p> <p>The most important aspect, which has been the central theme of the Audio-video classes, is that the students must find themselves equipped to present their viewpoints in a more analytical manner within the limited time-span of the examination. Based on an extensive research, we have come to observe, that &ldquo;What not to be studied?&rdquo;, &ldquo; How can a student maximize his marks by spending minimum amount of time?&rdquo;<\\/p> <p>Since the class has been developed by a seasoned civil servant with more than 20 years of work experience and teaching, much hard work has been made to maintain the quality of the lectures vis-a-vis the aspirations of the budding civil servant. With this methodology, students will have the ease to learn at their own pace. They will be in position to revise the course within a short span of time, and can analyze their improvements at regular intervals in a more scientific manner. Moreover, they will not be constrained to experience the dehumanized environment of classroom teaching.<\\/p>\\r\\n<\\/div>",Html.FROM_HTML_MODE_COMPACT));
       // Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com/app-api/director-desk.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("director-desk");
                            Log.i("Temp", "" +response.toString());
//                            counter =dataArray.length();
                          //  prepareAlbums(dataArray);


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



        return view;
    }
}
