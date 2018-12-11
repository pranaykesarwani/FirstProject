package pranay.example.com.magnusias4;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.ImageView;
import android.widget.ListView;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TestLayoutFragment0 extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView,recyclerView2;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private List<Album> dataFeed= new ArrayList<Album>();
    ListView listView;
    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.test_layout, container, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.backdrop);
       // imageView.setImageR(R.drawable.cover1);
        Log.i("Subject Categ","1");
        databaseHelper = new DatabaseHelper(getActivity());

//                initCollapsingToolbar(view);
        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

       // Toast.makeText(getActivity(), "Height n Width "+""+dpHeight +" "+dpWidth, Toast.LENGTH_SHORT).show();
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
       /* RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
*/
        //
        //
        // final ArrayAdapter<DataItem> adapter = new TestLayoutFragment.Adapter();
        Cursor cursor  = databaseHelper.getSubjectData();

        Log.i("Count",""+cursor.getCount());
        if (cursor.getCount()>0)
        {

        while (cursor.moveToNext()){
            Album a=  new Album(cursor.getString(1), cursor.getString(6),0,cursor.getString(0));
           albumList.add(a);
            Log.i("From Subject Table",cursor.getString(0)+" "+cursor.getString(1)+" "+ cursor.getString(6));
        }
        }
        //Album a=  new Album(subject_name, "http://magnusias.com/"+img_path,url,0);
        //albumList.add(a);

    else
     //  final Album[] a = new Album[1000];
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://magnusias.com/app-api/get-paper.php", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray dataArray = response.getJSONArray("paper");
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

                String subject_name= buffer.getString("name");
                String img_path = buffer.getString("src");
                String url = buffer.getString("for_subject");
              //  Log.i("Image Path",img_path);
                Album a=  new Album(subject_name, "http://magnusias.com/"+img_path,url,0);
                albumList.add(a);

            }




/*



*/
        adapter.notifyDataSetChanged();
    }

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    void imageDownload(String URL){
        Drawable drawable = getResources().getDrawable(R.drawable.cover);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ContextWrapper wrapper = new ContextWrapper(getActivity());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);// To remomove the exception

        try {
            int file_length = 0;
            URL url = new URL(URL);

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


            Log.i("Image Size",""+file_length);
            File file = wrapper.getDir("Images_PRK", MODE_PRIVATE);
            file = new File(file, "UniqueFileName" + ".jpg");

            try {

                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);
                byte[] data = new byte[1024];
                int total=0;
                int count = 0;
                OutputStream stream = null;
                stream = new FileOutputStream(file);
                while ((count = inputStream.read(data))!=-1)
                {

                    total+=count;
                    stream.write(data,0,count);
                    //int progress = (int)total * 100 / file_length;

                }
                inputStream.close();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();

            } catch (IOException e) // Catch the exception
            {
                e.printStackTrace();
            }

            // Parse the gallery image url to uri
            Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        }
        catch (Exception e)
        {
            Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("Exception ",e.toString());
        }

    }
}
