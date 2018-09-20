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

public class TestLayoutFragment3 extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    ListView listView;
    DatabaseHelper databaseHelper;
     String subject_id,topic_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.content_main, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.titleLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.pageTitle);
        textView.setText("List of Chapter(s)");

        // Toast.makeText(getActivity(), "Stage 3", Toast.LENGTH_SHORT).show();
        Log.i("Stage 3", "Stage 3");

        //     initCollapsingToolbar(view);
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        //Log.i("Stage 3 URL",url);
       // Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
        topic_id = bundle.getString("topic_id");
        subject_id = bundle.getString("subject_id");
        Log.i("Stage 3", "Before Loop " + topic_id + " " + subject_id);
        databaseHelper = new DatabaseHelper(getActivity());
        Cursor cursor = databaseHelper.getChapterListData(topic_id, subject_id);
        // Toast.makeText(getActivity(), subject_id+" "+topic_id+" " + cursor.getCount(), Toast.LENGTH_SHORT).show();


        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


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
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //Log.i("Chapter Name",cursor.getString(3));
                Album a = new Album(3, cursor.getString(3), subject_id, topic_id, cursor.getString(0));
                albumList.add(a);
                Log.i("From Topic Table", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(5));
            }
        }

        //Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();


        //      final ArrayAdapter<DataItem> adapter = new TestLayoutFragment.Adapter();
        else
        {   JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("chapter");

                            prepareAlbums(dataArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Chapter Error",error.toString());
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);

    }

        return view;
    }



    private void prepareAlbums(JSONArray temp) throws JSONException {
        int[] covers = new int[]{
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
                R.drawable.gs3};



        int i=0;
        Log.i("Stage 3","Before Loop");
        for ( i = 0; i < temp.length(); i++) {
          //  Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();

            JSONObject buffer = temp.getJSONObject(i);
                    Log.i("Chapter Data",temp.toString());
                String chapter_name= buffer.getString("heading");
                String chapter_content = buffer.getString("details");
            String id = buffer.getString("id");



            Log.i("Stage 3",id+" "+chapter_name);
            Log.i("Stage 3","Inside Loop");
           // Album a=  new Album(3,cursor.getString(3),subject_id ,topic_id,cursor.getString(0));
            Album a=  new Album(3,id, chapter_name,chapter_content);

          //  Album a=  new Album(chapter_name, "http://magnusias.com/upload//topic//758c5886ed027a7fade64bf41c19b32d.png","",3,id);
            albumList.add(a);

            } Log.i("Stage 3","After Loop");
       // Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();





/*


*/
        adapter.notifyDataSetChanged();
    }
/*


*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
