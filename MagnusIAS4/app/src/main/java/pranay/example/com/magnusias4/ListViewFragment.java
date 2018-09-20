package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment {

    ListView listView;
    private List<TopicData> dataFeed= new ArrayList<TopicData>();

    ArrayAdapter<TopicData> adapter ;//= new customAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://magnusias.com//app-api//get-subject-topic.php?request=7", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Data",response.toString());
                        try {
                            JSONArray dataArray = response.getJSONArray("topic");

                            for (int i=0;i<dataArray.length();i++)
                            {
                                JSONObject temp = dataArray.getJSONObject(i);
                                String topicName = temp.getString("name");
                                String details = temp.getString("details");
                                String imageUrl = temp.getString("img_path");

                                dataFeed.add(new TopicData(topicName,details,"http://magnusias.com"+imageUrl ));
                                adapter.notifyDataSetChanged();

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
        jsonObjectRequest.setRetryPolicy( new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);

        return inflater.inflate(R.layout.fragment_listview, container, false);
    }



    /*private class customAdapter extends ArrayAdapter<TopicData> {
        public customAdapter() {
            super();
//            super(getActivity(), R.layout.data_items, dataFeed);
        }


        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.data_items, parent, false);

            TopicData currentItem = dataFeed.get(position);
            ImageView newsImage = (ImageView) convertView.findViewById(R.id.leftIcon);
            TextView heading = (TextView) convertView.findViewById(R.id.mr_name);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);


            heading.setText(currentItem.getTopicName());
            desc.setText((CharSequence) currentItem.getDetails());
            //newsImage.setImageResource(currentItem.getImageId());
            // Toast.makeText(MainActivity.this,currentItem.getImageURL().toString(),Toast.LENGTH_SHORT).show();
            //Log.i("image",currentItem.getImageURL());
            //Picasso.with(MainActivity.this).load(currentItem.getImageURL()).into(newsImage);

            return convertView;

        }
    }*/
    }
