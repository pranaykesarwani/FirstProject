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
import android.widget.Toast;
import android.widget.VideoView;

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

public class ChapterVideoListFragment extends Fragment {

    private List<VideoListDataItem> dataFeed= new ArrayList<VideoListDataItem>();
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // Bundle bundle = getArguments();

       /* String chapterName = bundle.getString("chapterName");
        String chapterDescription = bundle.getString("chapterDescription");
        String chapterId = bundle.getString("chapterId");
*/
        final ArrayAdapter<VideoListDataItem> adapter = new ChapterVideoListFragment.Adapter();

        View view;

        view =  inflater.inflate(R.layout.chapter_data_items,container,false);
        TextView chapter_name = (TextView) view.findViewById(R.id.chapter_name_display );
        TextView chapter_details = (TextView)view.findViewById(R.id.chapter_details);
      /*  chapter_name.setText(chapterName);
        chapter_details.setText(Html.fromHtml(chapterDescription));
*/
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        listView = view.findViewById(R.id.listview_videoview);
       // listView.setClickable(false);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://magnusias.com/app-api/faq.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray chapterArray = response.getJSONArray("faq");


                    for (int i=0;i<chapterArray.length();i++) {
                        JSONObject temp = chapterArray.getJSONObject(i);
                       /* String cc_heading = temp.getString("cc_heading");
                        Log.i("cc_heading",cc_heading);
                        String id = temp.getString("id");
                        String img = temp.getString("img");
                        String access_type = temp.getString("access_type");
                        String video_id = temp.getString("video_id");*/
                        String id = temp.getString("id");
                        String question = temp.getString("question");
                        String answer = temp.getString("answer");


                        dataFeed.add(new VideoListDataItem(id,"","",question,answer,""));
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

                VideoListDataItem currentItem = dataFeed.get(position);


                Toast.makeText(getActivity(), "Videos will be available shortly!", Toast.LENGTH_SHORT).show();
                //
  //              MainActivity mainActivity = (MainActivity)getActivity();
//                mainActivity.getChapterVideoList(currentItem.getChapterURL());



            }
        });


        return view;
    }
    private class Adapter extends ArrayAdapter<VideoListDataItem> {

        public Adapter() {
            super(getActivity(), R.layout.chapter_list_data_items,dataFeed);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.faq_sample,null,false);
            }


                VideoListDataItem currentItem = dataFeed.get(position);
                TextView question = (TextView) convertView.findViewById(R.id.faq_question);
                TextView answer = (TextView) convertView.findViewById(R.id.faq_answer);
                //ImageView imageView = (ImageView) convertView.findViewById(R.id.videoThumbnail);
              //  VideoView videoView = (VideoView)convertView.findViewById(R.id.chapterVideoList);

            question.setText(currentItem.getCc_heading());
            answer.setText(Html.fromHtml(currentItem.getVideo_id(),Html.FROM_HTML_MODE_COMPACT));
          //  Log.i("Answer",currentItem.getVideo_id());
               // imageView.setImageResource(currentItem.getImageId());
                // Toast.makeText(MainActivity.this,currentItem.getImageURL().toString(),Toast.LENGTH_SHORT).show();
               // Log.i("image", String.valueOf(currentItem.getImageId()));
//                 Picasso.get().load(currentItem.getVideoThumbnail()).into(imageView);
                // Toast.makeText(getActivity(), ""+listView.getAdapter().getCount(), Toast.LENGTH_SHORT).show();

            return convertView;
        }
        }

}
