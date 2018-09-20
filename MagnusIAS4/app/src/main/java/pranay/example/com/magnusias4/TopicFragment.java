package pranay.example.com.magnusias4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class TopicFragment extends Fragment {
    private List<DataItem> dataFeed= new ArrayList<DataItem>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
       // final ArrayAdapter<DataItem> adapter = new customAdapter();

        JsonObjectRequest myReq= new JsonObjectRequest(Request.Method.GET,
                "http://magnusias.com//app-api//get-subject-topic.php?request=1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Log.i("Pranay =>","In try");
//                    Toast.makeText(getApplicationContext(),"In Try",Toast.LENGTH_SHORT).show();

                    JSONArray MRDatas = response.getJSONArray("topic");

                    Log.i("Pranay =>",response.toString());

                    //Toast.makeText(getApplicationContext(),MRDatas.length(),Toast.LENGTH_SHORT).show();
                    for (int i=0;i<MRDatas.length();i++)
                    {
                        //Log.i("Pranay =>",String.valueOf(MRDatas.length()));
                        JSONObject temp = MRDatas.getJSONObject(i);

                        String first_name = temp.getString("name");
                        String last_name = temp.getString("details");
                        String image_path ="http://magnusias.com/"+ temp.getString("img_path");;
                        Log.i("Image",image_path);
                        // String link = temp.getString("http://www.google.com");
                        dataFeed.add(new DataItem(first_name,last_name," "," ", image_path,""));
                        //  Toast.makeText(getApplicationContext(),"3.1",Toast.LENGTH_SHORT).show();
                        // Log.i("Pranay =>", );
                        //adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                   // Toast.makeText,"In Catch",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    Log.i("JSONException error =>",e.toString());

                }
            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //Toast.makeText(getApplicationContext(),"Before My Req",Toast.LENGTH_SHORT).show();

        myReq.setRetryPolicy( new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(myReq);

        View view;
        ListView myFirstListView;// = (ListView) view.findViewById(R.id.l);





        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class customAdapter extends ArrayAdapter<DataItem> {
        public customAdapter(@NonNull Context context, int resource) {
            super(context,  R.layout.data_items,dataFeed);
        }
    }
}

