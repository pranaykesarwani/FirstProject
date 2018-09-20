package pranay.example.com.magnusias4;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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

public class TestLayoutFragment2 extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private List<Album> dataFeed= new ArrayList<Album>();
    ListView listView;
    DatabaseHelper databaseHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.content_main, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.titleLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.pageTitle);
        textView.setText("Topics");
        // Toast.makeText(getActivity(), "Stage 2", Toast.LENGTH_SHORT).show();

        //     initCollapsingToolbar(view);
        Bundle bundle = getArguments();
        String url = bundle.getString("url");
        String id = bundle.getString("id");


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
        Cursor cursor = databaseHelper.getTestTopicData(id);

        //+cursor.getString(5)
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Album a = new Album(cursor.getString(1), 2, cursor.getString(0), id, cursor.getString(4));//Different constructor
                //  Album a = new Album("","");
                albumList.add(a);
                Log.i("From Topic Test Table", cursor.getString(0) + " " + cursor.getString(1) + " ");
            }
        }
        // prepareAlbums();
        //final Album[] a = new Album[1000];
        //Log.i("Stage 2","Stage 2");
        else{
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("topic");
                            Log.i("2", "" + response.toString());
//                            counter =dataArray.length();
                            prepareAlbums(dataArray);

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject temp = dataArray.getJSONObject(i);
                                Log.i("2", temp.toString());


                                String first_name = temp.getString("name");
                              /*  String last_name = temp.getString("details");
                                String image_path = "http://magnusias.com/" + temp.getString("img_path");
                                ;
                                String chapter_path = temp.getString("url");
                              */
                                Log.i("2", temp.getString("url"));
                                // a[i] = new Album("General Studies Paper 1", 8, R.drawable.gs1);

                                //adapter.notifyDataSetChanged();

                                // dataFeed.add(new DataItem(first_name, last_name, " ", " ", image_path, chapter_path));
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);

    }

        return view;
    }

  /*  private class Adapter extends ArrayAdapter<Album> {

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
            Log.i("image", String.valueOf(currentItem.getImageId()));
            Picasso.get().load(currentItem.getImageURL()).into(imageView);

            return convertView;
        }
    }
*/


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
                String img_path = buffer.getString("img_path");
                String url = buffer.getString("url");

                Album a=  new Album(subject_name, "http://magnusias.com/"+img_path,url,2);
                albumList.add(a);

            }




/*
            a = new Album("1", 13, covers[0]);
        albumList.add(a);

        a = new Album("General Studies Paper 1", 8, covers[1]);
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
