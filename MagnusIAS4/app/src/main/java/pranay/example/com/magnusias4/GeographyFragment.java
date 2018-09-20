package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GeographyFragment extends Fragment {



    private List<DataItem> dataFeed= new ArrayList<DataItem>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ArrayAdapter<DataItem> adapter = new GeographyFragment.Adapter();

        View view;

        view =  inflater.inflate(R.layout.fragment_topic2,container,false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // JsonObjectRequest myReq= new JsonObjectRequest(Request.Method.GET,

        listView = view.findViewById(R.id.listview);
        dataFeed.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://magnusias.com//app-api//get-subject-topic.php?request=7", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("topic");
                            Log.i("Temp",""+dataArray.length());
                            for(int i=0;i<dataArray.length();i++) {
                                JSONObject temp = dataArray.getJSONObject(i);
                                Log.i("Temp",temp.toString());


                                String first_name = temp.getString("name");
                                String last_name = temp.getString("details");
                                String image_path ="http://magnusias.com/"+ temp.getString("img_path");;
                                String chapter_path = temp.getString("url");
                                String id = temp.getString("id");
                                Log.i("Image",temp.getString("url"));

                                dataFeed.add(new DataItem(first_name,last_name,id," ", image_path,chapter_path));
                                adapter.notifyDataSetChanged();
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

        listView.setAdapter( adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                DataItem currentItem = dataFeed.get(position);

                Log.v("position", currentItem.getTime());
               //  
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.getChapterList(currentItem.getChapterURL());



            }
        });


         


        return view;
    }
    private class Adapter extends ArrayAdapter<DataItem> {

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
                // Toast.makeText(MainActivity.this,currentItem.getImageURL().toString(),Toast.LENGTH_SHORT).show();
                Log.i("image", String.valueOf(currentItem.getImageId()));
                Picasso.get().load(currentItem.getImageURL()).into(imageView);

            return convertView;
        }
    }


  /*  LinearLayout indian_geography,physical_geography;
    FragmentTransaction fragmentManager;
    OnMessageSendListener onMessageSendListener;
    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       // Toast.makeText(getActivity(), "No Bundle", Toast.LENGTH_SHORT).show();
       *//* if (savedInstanceState==null) {
            Toast.makeText(getActivity(), "Null", Toast.LENGTH_SHORT).show();
        }*//*return inflater.inflate(R.layout.fragment_geography, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       Bundle bundle = getArguments();
        String message =  bundle.getString("Subject");
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://magnusias.com/app-api/get-subject.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    JSONArray dataArray = response.getJSONArray("subject");
                   // Toast.makeText(getActivity(),"hi", Toast.LENGTH_SHORT).show();
                    Log.i("Json Response",response.toString());

                    for(int i=0;i<dataArray.length();i++)
                    {

                        JSONObject temp= dataArray.getJSONObject(i);
                        String id = temp.getString("id");
                        url = temp.getString("url");
                        String details = temp.getString("details");
                        Log.i("Json Element",id+" "+details+"  "+url);
                        if (id.equals("7"))
                        {
                            break;
                        }

                    }

                }catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Json Error "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        jsonObjectRequest.setRetryPolicy( new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);

        try {
            URL url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //  url = Uri.parse(url);

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST,"http://magnusias.com/app-api/get-subject-topic.php?request=7",null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                   JSONArray dataArray = response.getJSONArray("topic");
                  //  Toast.makeText(getActivity(),dataArray1.length(), Toast.LENGTH_SHORT).show();
                    Log.i("Json Inside Response",response.toString());

                    for(int i=0;i<dataArray.length();i++)
                    {

                        JSONObject temp= dataArray.getJSONObject(i);
                        String id = temp.getString("id");
                        String name = temp.getString("name");
                        //String details = temp.getString("details");
                        Log.i("Json Inside Element",id+" "+name);



                    }


                }catch (Exception e) {

                    Log.i("Json Error",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Json Error "+error.toString(),Toast.LENGTH_SHORT).show();
                Log.i("Json Error",error.toString());
            }
        });
        jsonObjectRequest1.setRetryPolicy( new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest1);




        //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();



*//*
StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://magnusias.com/app-api/get-subject.php", new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {

      //  Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

        Log.i("Response",response);
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("Error",error.toString());
    }
});

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
*//*




        indian_geography = (LinearLayout)view.findViewById(R.id.indian_geography);
        physical_geography = (LinearLayout)view.findViewById(R.id.physical_geography);
        fragmentManager =getActivity().getSupportFragmentManager().beginTransaction();

        indian_geography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager.replace(R.id.frame_container,new IndianGeographyFragment()).commit();

            }
        });

        physical_geography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.replace(R.id.frame_container,new PhysicalGeographyFragment()).commit();

            }
        });



    }


    public interface OnMessageSendListener{
        public void onMessageSend(String message);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;
        try{

            onMessageSendListener = (OnMessageSendListener)activity;
        }catch (Exception e){}


    }*/



}
