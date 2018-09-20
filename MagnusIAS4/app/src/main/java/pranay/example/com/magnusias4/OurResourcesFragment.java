package pranay.example.com.magnusias4;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class OurResourcesFragment extends Fragment {

    private RecyclerView recyclerView,recyclerView2;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private List<Album> dataFeed= new ArrayList<Album>();
    ListView listView;
    DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.titleLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.pageTitle);
        textView.setText("Our Reources");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        databaseHelper = new DatabaseHelper(getActivity());
        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;

        Toast.makeText(getActivity(), ""+dpWidth, Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);
        RecyclerView.LayoutManager mLayoutManager =null;
        if (dpWidth>1200)
            mLayoutManager = new GridLayoutManager(getActivity(), 4);

        if (dpWidth>500 && dpWidth<1200)
            mLayoutManager = new GridLayoutManager(getActivity(), 3);

        if (dpWidth<500)
            mLayoutManager = new GridLayoutManager(getActivity(), 2);


        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        Cursor cursor  = databaseHelper.getSubjectData();

//        Log.i("Count",""+cursor.getCount());
        if (0>0)
        {

            while (cursor.moveToNext()){
                Album a=  new Album(cursor.getString(1), cursor.getString(6),0,cursor.getString(0));
                albumList.add(a);
                Log.i("From Subject Table",cursor.getString(0)+" "+cursor.getString(1)+" "+ cursor.getString(6));
            }
        }
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "http://magnusias.com/app-api/our-resources.php", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray dataArray = response.getJSONArray("our-resources");
                                Log.i("Temp", "" + response.toString());
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


        for (int i = 0; i < temp.length(); i++) {
            JSONObject buffer = temp.getJSONObject(i);

            String heading= buffer.getString("heading");
            String img_path = buffer.getString("image");
            String url = buffer.getString("link");
              Log.i("url",url);
            Album a=  new Album(heading, "http://magnusias.com/upload/our_resources/"+img_path,url,1000);
            albumList.add(a);

        }




        /*



         */
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
