package pranay.example.com.magnusias4;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.List;

public class TestLayoutFragment4 extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private List<Album> dataFeed= new ArrayList<Album>();
    ListView listView;
    String chapter_id;
    DatabaseHelper databaseHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.content_main, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.titleLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.pageTitle);
        textView.setText("List of Video(s)");
        // Toast.makeText(getActivity(), "Stage 3", Toast.LENGTH_SHORT).show();


        //     initCollapsingToolbar(view);
        Bundle bundle = getArguments();
        String subject_id = bundle.getString("subject_id");
        String topic_id = bundle.getString("topic_id");
        chapter_id = bundle.getString("chapter_id");
        String id = bundle.getString("id");
        databaseHelper = new DatabaseHelper(getActivity());
        //Log.i("Stage 4 ID",topic_id);
        //.i("Stage 3","Before Loop "+url);

      //  Toast.makeText(getActivity(), chapter_id, Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);


        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;


        RecyclerView.LayoutManager mLayoutManager =null;
        if (dpWidth>1200)
            mLayoutManager = new GridLayoutManager(getActivity(), 4);

        if (dpWidth>500 && dpWidth<1200)
            mLayoutManager = new GridLayoutManager(getActivity(), 3);

        if (dpWidth<500)
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //
        Cursor cursor = databaseHelper.getChapterListContentData(subject_id, topic_id, chapter_id);

        if (cursor.getCount() > 0) {


            while (cursor.moveToNext()) {
                //Log.i("Chapter Name",cursor.getString(3));
//            Album a=  new Album(4,cursor.getString(7),subject_id ,topic_id,cursor.getString(0));
                Album a = new Album(cursor.getString(7), cursor.getString(0), 4, cursor.getString(6), cursor.getString(9),"","");
                albumList.add(a);
                Log.i("From Topic Table", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(5));
            }

        }
else
        //final ArrayAdapter<DataItem> adapter = new TestLayoutFragment.Adapter();

        // prepareAlbums();
        {
           Log.i("Stage 4 url","http://magnusias.com/app-api/get-video.php?v_id="+ chapter_id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://magnusias.com/app-api/get-video.php?v_id=" + chapter_id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("video");

                            // Toast.makeText(getActivity(), dataArray.toString(), Toast.LENGTH_SHORT).show();
                            //  Log.i("Video", "" +response.toString());
//                            counter =dataArray.length();
                            prepareAlbums(dataArray);

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

    }

        return view;
    }



    private void prepareAlbums(JSONArray temp) throws JSONException {
        /*int[] covers = new int[]{
                R.drawable.english,
                R.drawable.gs1,
                R.drawable.gs2,
                R.drawable.gs3,
                R.drawable.gs4,
                R.drawable.gs1,
                R.drawable.gs2,
                R.drawable.gs3,
                R.drawable.gs1,
                R.drawable.gs2,
                R.drawable.gs3};*/



        int i=0;
        Log.i("Stage 4",temp.toString());
        for ( i = 0; i < temp.length(); i++) {
          //  Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();
            Log.i("Stage 4 Data",temp.getString(i));
            JSONObject buffer = temp.getJSONObject(i);

                String video_name= buffer.getString("cc_heading");
               // String chapter_content = buffer.getString("details");
                String thumbnail = buffer.getString("img");
                String id = buffer.getString("id");
                String video_id = buffer.getString("video_id");
                String pdfpath = buffer.getString("pdf_document");
                Log.i("Stage 4 ",pdfpath);
           // Album a=  new Album(chapter_name, "http://magnusias.com/upload/chapter/thumb/"+thumbnail,"",4);
            Album a = new Album(video_name,id,4,"https://magnusias.com/upload/chapter/thumb/"+thumbnail,chapter_id,video_id,pdfpath);
            albumList.add(a);

            } Log.i("Stage 4","After Loop");
      //  Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();





/*


*/
        adapter.notifyDataSetChanged();
    }
/*

    private void initCollapsingToolbar(View view) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
