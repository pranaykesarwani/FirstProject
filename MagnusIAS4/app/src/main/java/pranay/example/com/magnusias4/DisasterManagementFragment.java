package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisasterManagementFragment extends Fragment {

    private List<DataItem> dataFeed= new ArrayList<DataItem>();
    ListView listView;

    static int counter =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_topic2, container, false);


        final ArrayAdapter<DataItem> adapter = new DisasterManagementFragment.Adapter();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        listView = view.findViewById(R.id.listview);
        dataFeed.clear();
        Log.i("Counter", "" + counter);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com//app-api//get-subject-topic.php?request=11", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("topic");
                            Log.i("Temp", "" + dataArray.length());
                            counter =dataArray.length();
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject temp = dataArray.getJSONObject(i);
                                Log.i("Temp", temp.toString());


                                String first_name = temp.getString("name");
                                String last_name = temp.getString("details");
                                String image_path = "http://magnusias.com/" + temp.getString("img_path");
                                ;
                                String chapter_path = temp.getString("url");
                                Log.i("Image", temp.getString("url"));


                                dataFeed.add(new DataItem(first_name, last_name, " ", " ", image_path, chapter_path));
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                DataItem currentItem = dataFeed.get(position);

                Log.v("position", currentItem.getImageURL());
                // Toast.makeText(getActivity(), currentItem.getChapterURL(), Toast.LENGTH_SHORT).show();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.getChapterList(currentItem.getChapterURL());


            }
        });



        return view;
    }
    private class Adapter extends ArrayAdapter<DataItem> {

        public Adapter() {
            super(getActivity(), R.layout.data_items,dataFeed);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.data_items,null,false);

            }

            DataItem currentItem = dataFeed.get(position);
            TextView heading = (TextView) convertView.findViewById(R.id.subject_name);
            TextView desc = (TextView) convertView.findViewById(R.id.subject_details);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.leftIcon);


            heading.setText(currentItem.getNewsHeading() + " ");
            desc.setText(Html.fromHtml(currentItem.getNewsDesc()));
            imageView.setImageResource(currentItem.getImageId());
            Log.i("image", String.valueOf(currentItem.getImageId()));
            Picasso.get().load(currentItem.getImageURL()).into(imageView);

            return convertView;
        }
    }

}
