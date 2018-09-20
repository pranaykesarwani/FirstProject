package pranay.example.com.magnusias4;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Subjects extends Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "Inside Subject", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.test_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // JsonObjectRequest myReq= new JsonObjectRequest(Request.Method.GET,
       // dataFeed.clear();
        //listView = view.findViewById(R.id.listview);
//        getDialog().setTitle("Subject should be displayed here...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://magnusias.com/app-api/get-subject.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("subject");
                            Log.i("Temp",""+dataArray.length());
                            for(int i=0;i<dataArray.length();i++) {
                                JSONObject temp = dataArray.getJSONObject(i);
                                Log.i("Temp",temp.toString());


                                String subject_name = temp.getString("name");
                               /* String last_name = temp.getString("details");
                                String image_path ="http://magnusias.com/"+ temp.getString("img_path");;
                                String chapter_path = temp.getString("url");*/
                                Log.i("Image",temp.getString("url"));

                                prepareAlbums(subject_name);
                               // dataFeed.add(new DataItem(first_name,last_name," "," ", image_path,chapter_path));
                               // adapter.notifyDataSetChanged();
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



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



    }

    private void prepareAlbums(String subject_name) {
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
               };

      //  Album a = new Album(subject_name, 13, covers[0]);
       // albumList.add(a);

        /*a = new Album("General Studies Paper 1", 8, covers[1]);
        albumList.add(a);

        a = new Album("General Studies Paper 2", 11, covers[2]);
        albumList.add(a);

        a = new Album("General Studies Paper 3", 12, covers[3]);
        albumList.add(a);

        a = new Album("General Studies Paper 4", 14, covers[4]);
        albumList.add(a);

        a = new Album("Views on News", 1, covers[5]);
        albumList.add(a);

        a = new Album("Civil Service Aptitude Test", 11, covers[6]);
        albumList.add(a);
*/

        adapter.notifyDataSetChanged();
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
