package pranay.example.com.magnusias4;

import android.database.Cursor;
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
import android.widget.ListView;
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

public class OurTeamFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.content_main, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.titleLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.pageTitle);

        textView.setText("Our Team");

        Log.i("Subject Categ", "1");
        databaseHelper = new DatabaseHelper(getActivity());


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
        //      final ArrayAdapter<DataItem> adapter = new TestLayoutFragment.Adapter();
        Cursor cursor = databaseHelper.getOurTeamData();

        Log.i("Count", "" + cursor.getCount());
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Album a = new Album(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getColumnName(8), cursor.getColumnName(9), cursor.getString(2), cursor.getString(3));
                albumList.add(a);
                Log.i("From Subject Table", cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(6));
            }
        }

        //Album a=  new Album(subject_name, "http://magnusias.com/"+img_path,url,0);
        //albumList.add(a);
        //  final Album[] a = new Album[1000];
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com/app-api/our-team.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp = response.getJSONArray("our-team");
                            Log.i("Temp", "" + response.toString());
//                            counter =dataArray.length();
                            prepareAlbums(temp);

                           /* for (int i = 0; i < temp.length(); i++) {
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
                               // databaseHelper.ourTeamData(id, name, post, img_path, details, email, mobile, hobbies, qualification, skills);
                            }
*/

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

            String name= buffer.getString("name");
            String img_path = buffer.getString("src");
            String details = buffer.getString("details");
            String email = buffer.getString("email");
            String mobile = buffer.getString("mobile");
            String hobbies = buffer.getString("hobbies");
            String qualification = buffer.getString("qualification");
            String skills = buffer.getString("skills");
            String post = buffer.getString("post");



            //  Log.i("Image Path",img_path);
            Album a=  new Album(name,details,email,mobile,hobbies,qualification,skills,post,"http://magnusias.com/"+img_path);
            albumList.add(a);

        }




        /*



         */
        adapter.notifyDataSetChanged();
    }
}
