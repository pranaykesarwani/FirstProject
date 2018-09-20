package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public class DirectorFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.fragment_director, container, false);
//        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView)view.findViewById(R.id.pageTitle);

        //textView.setText("Our Team");

        Log.i("Subject Categ","1");
         databaseHelper = new DatabaseHelper(getActivity());


        //recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com/app-api/director-desk.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp = response.getJSONArray("director-desk");
                            Log.i("Temp", "" +response.toString());
//                            counter =dataArray.length();
                            //prepareAlbums(dataArray);


                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                String id = buffer.getString("id");
                                String name = buffer.getString("name");
                                String img_path = buffer.getString("src");
                                String details = buffer.getString("details");
                                String email = buffer.getString("email");
                                String mobile = buffer.getString("mobile");
                                String hobbies = buffer.getString("hobbies");
                                String qualification = buffer.getString("qualification");
                                String skills = buffer.getString("skills");
                                String post = buffer.getString("post");
                                databaseHelper.setDirectorDeskData(id,name,post,img_path,details,email,mobile,hobbies,qualification,skills);
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


        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        //recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
      //  recyclerView.setAdapter(adapter);
        //      final ArrayAdapter<DataItem> adapter = new TestLayoutFragment.Adapter();
//        Cursor cursor  = databaseHelper.getSubjectData();

  /*      Log.i("Count",""+cursor.getCount());
        if (cursor.getCount()>0)
        {

            while (cursor.moveToNext()){
                Album a=  new Album(cursor.getString(1), cursor.getString(6),0,cursor.getString(0));
                albumList.add(a);
                Log.i("From Subject Table",cursor.getString(0)+" "+cursor.getString(1)+" "+ cursor.getString(6));
            }
        }
*//*
        //Album a=  new Album(subject_name, "http://magnusias.com/"+img_path,url,0);
        //albumList.add(a);
        //  final Album[] a = new Album[1000];

*/

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



        for (int i = 0; i < temp.length(); i++) {
            JSONObject buffer = temp.getJSONObject(i);

            String subject_name= buffer.getString("name");
            String img_path = buffer.getString("src");
            String details = buffer.getString("details");
            String email = buffer.getString("email");
            String mobile = buffer.getString("mobile");
            String hobbies = buffer.getString("hobbies");
            String qualification = buffer.getString("qualification");
            String skills = buffer.getString("skills");
            String post = buffer.getString("post");
            //  Log.i("Image Path",img_path);
            Album a=  new Album(subject_name,details,email,mobile,hobbies,qualification,skills,post,"http://magnusias.com/"+img_path);
            albumList.add(a);

        }
        adapter.notifyDataSetChanged();
    }
}
