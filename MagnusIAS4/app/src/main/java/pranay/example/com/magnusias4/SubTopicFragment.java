package pranay.example.com.magnusias4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

public class SubTopicFragment extends Fragment {


    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3[];
    LinearLayout linearLayout3_1[];
    LinearLayout linearLayout3_2[];

    TextView textView;

    ScrollView scrollViewLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        linearLayout1 = new LinearLayout(getActivity());
        View view = linearLayout1;
        linearLayout2 = new LinearLayout(getActivity());
        textView = new TextView(getActivity());
        textView.setText("Sample");
        textView.setBackgroundColor(Color.RED);
        //linearLayout1.setBackgroundColor(Color.BLACK);

      //  linearLayout1.addView(textView);

        scrollViewLayout = new ScrollView(getActivity());

        LinearLayout.LayoutParams linearLayoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final LinearLayout.LayoutParams linearLayoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout.LayoutParams linearLayoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ScrollView.LayoutParams scrollLayoutParams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        linearLayout1.setLayoutParams(linearLayoutParams1);

        scrollViewLayout.setLayoutParams(scrollLayoutParams);
        linearLayout1.addView(scrollViewLayout);

        linearLayout2.setLayoutParams(linearLayoutParams1);
        linearLayout2.setOrientation(LinearLayout.VERTICAL);
        //linearLayout2.setBackgroundColor(Color.RED);
        linearLayout2.addView(textView);
        scrollViewLayout.addView(linearLayout2);






        //working Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://magnusias.com/app-api/get-subject-topic.php?request=7", null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      //working  Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = response.getJSONArray("topic");
                           // working Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_SHORT).show();

                           /// Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();

                            int length = jsonArray.length();

                         LinearLayout   linearLayout3[] = new LinearLayout[length];
                            LinearLayout   linearLayout3_1[] = new LinearLayout[2];
                            ImageView imageView = new ImageView(getActivity());

                        // LinearLayout linearLayout3_1[] = new LinearLayout[2];
                           /* linearLayout3[0] = new LinearLayout(getActivity());
                            linearLayout3[0].setLayoutParams(linearLayoutParams2);
                            linearLayout2.addView(linearLayout3[0]);

                            linearLayout3[1] = new LinearLayout(getActivity());
                            linearLayout3[1].setLayoutParams(linearLayoutParams2);
                            linearLayout2.addView(linearLayout3[1]);
*/

                            for (int i=0;i<jsonArray.length();i++)
                            {
                                /*JSONObject temp = jsonArray.getJSONObject(i);
                                String id = temp.getString("id");
                                String subName = temp.getString("name");

                                linearLayout3[i] = new LinearLayout(getActivity());
                                linearLayout3[i].setLayoutParams(linearLayoutParams2);

                                  */

                                linearLayout3[i] = new LinearLayout(getActivity());
                                linearLayout3[i].setLayoutParams(linearLayoutParams2);
                                        //linearLayout3_1[i]

                                //linearLayout3[i].addView(textView);



                                linearLayout2.addView(linearLayout3[i]);




                                            linearLayout3_1[0] = new LinearLayout(getActivity());
                                            linearLayout3_1[0].setLayoutParams(linearLayoutParams3);
                                            linearLayout3[i].addView(linearLayout3_1[0]);

                                                imageView.setLayoutParams(linearLayoutParams3);
                                                imageView.setImageResource(R.drawable.arun);
/*

                                                linearLayout3_1[0].removeView(imageView);
                                                linearLayout3_1[0].addView(imageView);
*/

                                            addImageView(linearLayout3_1[0],imageView);



                               // Log.i("Loop",id+" "+subName);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i("Json Response Sub Topic",response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        jsonObjectRequest.setRetryPolicy( new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);

        return view ;
    }

    private void addImageView(LinearLayout linearLayout, ImageView imageView) {

      //  linearLayout.addView(imageView);
    }
}
