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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChapterListFragment extends Fragment {

    private List<DataItem> dataFeed= new ArrayList<DataItem>();
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ArrayAdapter<DataItem> adapter = new ChapterListFragment.Adapter();

        View view;

        view =  inflater.inflate(R.layout.fragment_chapter_list,container,false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        listView = view.findViewById(R.id.listview_chapter_list);
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        Log.i("url",url);
           dataFeed.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray chapterArray = response.getJSONArray("chapter");

                    for (int i=0;i<chapterArray.length();i++) {
                        JSONObject temp = chapterArray.getJSONObject(i);
                        String heading = temp.getString("heading");
                        String id = temp.getString("id");
                        String details = temp.getString("details");
                        dataFeed.add(new DataItem(heading,details,id,"","",""));
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

        listView.setAdapter( adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                DataItem currentItem = dataFeed.get(position);

                Log.v("position", currentItem.getImageURL());
                //
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.getChapterVideoList(currentItem.getNewsHeading(),currentItem.getNewsDesc(),currentItem.getTime());



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
                // Toast.makeText(MainActivity.this,currentItem.getImageURL().toString(),Toast.LENGTH_SHORT).show();
                Log.i("image", String.valueOf(currentItem.getImageId()));
                // Picasso.get().load(currentItem.getImageURL()).into(imageView);
                // Toast.makeText(getActivity(), ""+listView.getAdapter().getCount(), Toast.LENGTH_SHORT).show();

            return convertView;
        }
    }

}
