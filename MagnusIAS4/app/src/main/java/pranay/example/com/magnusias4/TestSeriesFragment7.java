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

public class TestSeriesFragment7 extends Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String url = bundle.getString("url");
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
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("subject-list");
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

    private void prepareAlbums(JSONArray temp)  {
        int stage =0;
        String details = null;
        String img = null;
        String id="", name="",url="";
        Album a;
        String next_page_test = "";
        for (int i = 0; i < temp.length(); i++) {
            try{
            JSONObject buffer = temp.getJSONObject(i);
             id= buffer.getString("id");
              name= buffer.getString("name");
            next_page_test = buffer.getString("next_page_test");
            Log.i("next_page_test",next_page_test);
             url = buffer.getString("url");
             details =buffer.getString("type");
             img = buffer.getString("img");
            // img = "http://magnusias.com/"+img;
            }catch(JSONException e){Log.i("JSONException",e.toString());}
           /* if ((img == null) ||(details == null))
            {

            }*/
            if (next_page_test.equals("0"))
            {
               a=  new Album(id, name,"",url,details,img,2020);
               // albumList.add(a);
                stage =2023;
                //a=  new Album(id, name,"",url,details,img,stage);
                albumList.add(a);
                Log.i("","Not Found");
            }
            else {
                Log.i("next_page_test", " Found");
                a=  new Album(id, name,"",url,details,img,2020,"0",0);
               // albumList.add(a);
                //stage  = 2022;
                //a=  new Album(id, name,"",url,details,img,stage);
                albumList.add(a);
            }





        }





        adapter.notifyDataSetChanged();
    }

}
