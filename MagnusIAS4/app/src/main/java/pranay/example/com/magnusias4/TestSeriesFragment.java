package pranay.example.com.magnusias4;

import android.os.Bundle;
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

public class TestSeriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;


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
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com/app-api/get-test-series.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("test_main_menu");
                            Log.i("Temp", "" + response.toString());
//                          ;
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


        return view;
    }

    private void prepareAlbums(JSONArray temp) throws JSONException {



        for (int i = 0; i < temp.length(); i++) {
            JSONObject buffer = temp.getJSONObject(i);
            String id= buffer.getString("id");
            String name= buffer.getString("name");
            String sub_menu = buffer.getString("sub_menu");
            String url = buffer.getString("url");
            String main_menu_id = buffer.getString("main_menu_id");

            if (id.equals("58"))
            {
                Log.i("Mock Test","ID Found!!!!!!!!!!");
            }

              Log.i("sub_menu",sub_menu+" "+id);
            Album a=  new Album(id, name,main_menu_id,sub_menu,url,2000);
            albumList.add(a);

        }





        adapter.notifyDataSetChanged();
    }

}
