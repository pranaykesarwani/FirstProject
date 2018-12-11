package pranay.example.com.magnusias4;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class SubjectFragment extends Fragment {
    LinearLayout indian_society,live_updates,current_affairs,news,ethics_integrity,ethics,science,indian_economy,ecology,international_relation,english,geography,history,indiansociety,polity,disaster_management;
    FragmentTransaction fragmentManager;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private List<Album> dataFeed= new ArrayList<Album>();
    ListView listView;
    DatabaseHelper databaseHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        View view = inflater.inflate(R.layout.content_main, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.titleLayout);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView textView = (TextView) view.findViewById(R.id.pageTitle);
        textView.setText("Papers");

        Log.i("Subject Categ","1");
        databaseHelper = new DatabaseHelper(getActivity());

       // initCollapsingToolbar(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getActivity(), albumList);
        Display display = getActivity().getWindowManager(). getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
       // Log.i("Height of Device",""+dpHeight);
      //  Toast.makeText(getActivity(), ""+outMetrics.heightPixels, Toast.LENGTH_SHORT).show();
      //  Log.i("Height of Device",""+dpHeight);

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

       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //      final ArrayAdapter<DataItem> adapter = new TestLayoutFragment.Adapter();
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
            String url = buffer.getString("for_subject");
            //  Log.i("Image Path",img_path);
            Album a=  new Album(subject_name, "http://magnusias.com/"+img_path,url,0);
            albumList.add(a);

        }




        /*



         */
        adapter.notifyDataSetChanged();
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        english = (LinearLayout)view.findViewById(R.id.english);
        geography = (LinearLayout)view.findViewById(R.id.geography);
        history = (LinearLayout)view.findViewById(R.id.history);
        indiansociety = (LinearLayout)view.findViewById(R.id.indiansociety);
        polity = (LinearLayout)view.findViewById(R.id.polity);
        international_relation = (LinearLayout)view.findViewById(R.id.international_relation);
        disaster_management = (LinearLayout)view.findViewById(R.id.disaster_management);
        ecology = (LinearLayout)view.findViewById(R.id.ecology);
        indian_economy = (LinearLayout)view.findViewById(R.id.indian_economy);
        science = (LinearLayout)view.findViewById(R.id.science);
        ethics = (LinearLayout)view.findViewById(R.id.ethics);
        ethics_integrity = (LinearLayout)view.findViewById(R.id.ethics_integrity);
        current_affairs = (LinearLayout)view.findViewById(R.id.current_affairs);
        news  = (LinearLayout)view.findViewById(R.id.news_analysis);
        indian_society  = (LinearLayout)view.findViewById(R.id.indiansociety);

        fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
        current_affairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new CurrentAffairsFragment()).addToBackStack(null).commit();
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new NewAnalysisFragment()).addToBackStack(null).commit();

            }
        });
        ethics_integrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new EthicsIntegrityFragment()).addToBackStack(null).addToBackStack(null).commit();

            }
        });
        ethics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new EthicsFragment()).addToBackStack(null).commit();

            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager.replace(R.id.frame_container,new ScienceFragment()).addToBackStack(null).commit();

            }
        });
        indian_economy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new IndianEconomyFragment()).addToBackStack(null).commit();

            }
        });

        ecology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new EcologyFragment()).addToBackStack(null).commit();

            }
        });
        disaster_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new DisasterManagementFragment()).addToBackStack("frs").commit();

            }
        });
        international_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().startActivity(new Intent(getActivity(),Level2.class));
                //getActivity().finish();
                fragmentManager.replace(R.id.frame_container,new InternationaRelationFragment()).addToBackStack(null)
                        .commit();


                //  fragmentManager.replace(R.id.frame_container,new ListViewFragment()).commit();

            }
        });


        polity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager.replace(R.id.frame_container,new PolityFragment()).addToBackStack(null)
                        .commit();

            }
        });

        indiansociety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new IndianSocietyFragment()).commit();
            }
        });
*//*

        bundle=new Bundle();
        //bundle.putString();
        key = "Subject";
        value = "English";
*//*

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "English", Toast.LENGTH_SHORT).show();

                // Bundle bundle=new Bundle();
                //bundle.putString("Subject","English");
              //  bundle=new Bundle();

                //bundle.putString("Subject","English");

                EnglishFragment englishFragment = new EnglishFragment();
               // englishFragment.setArguments(bundle);
                fragmentManager.replace(R.id.frame_container,
                        new EnglishFragment()).addToBackStack("English").commit();
                Log.i("English","English");
            }
        });
        geography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              *//*  Bundle bundle  = new Bundle();
                bundle.putString("Subject","Geogra");*//*

                GeographyFragment geographyFragment = new GeographyFragment();
             //   geographyFragment.setArguments(bundle);                 //sendMessage.sendData("Geography");
                fragmentManager.replace(R.id.frame_container,
                        new GeographyFragment()).addToBackStack("Geography").commit();
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,
                        new HistoryFragment()).addToBackStack("history").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
        indian_society.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,
                        new SubjectFragment()).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });

    }*/
}

