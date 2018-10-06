package pranay.example.com.magnusias4;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends Fragment {
    EditText username;
    EditText password;
    String url;
    int flag=0;
    private Context context;

public LoginPage(){}
@SuppressLint("ValidFragment")
public LoginPage(Context context){
    this.context = context;
}
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view =  inflater.inflate(R.layout.login_page,container,false);

        username = (EditText)view.findViewById(R.id.login_username);
        password = (EditText)view.findViewById(R.id.login_password);
        Bundle bundle = getArguments();
        final String  android_id = bundle.getString("android_id");
         url = bundle.getString("url");
         Log.i("url in Login Page","url "+url);
        Button login = (Button)view.findViewById(R.id.login);
        //final MainActivity mainActivity = (MainActivity)context;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue loginqueue = Volley.newRequestQueue(getActivity());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://magnusias.com/app-api/app-login.php?username=" + username.getText().toString() + "&password=" + password.getText().toString() + "&device_id=" + android_id, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{
                            JSONObject temp = response.getJSONObject("login-status");
                            String login_status = temp.getString("login-status");
                            String user_type = temp.getString("user_type");
                            if (login_status.equals("success") && (user_type.equals("paid"))) {
                                    try{
                                        flag = 1;
                                        //move(url);
                                //MainActivity mainActivity = (MainActivity)context;
                            //    mainActivity.getExamTestLevel5(url);

                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                    }
                            else
                            {
                                flag=0;
                                Log.i("login_status",login_status);

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                loginqueue.add(jsonObjectRequest);


                //Toast.makeText(getActivity(), username.getText().toString()+" "+ password.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;

    }
    public void move(String url){
        Log.i("URl",url);
     /*   AlbumsAdapter albumsAdapter = new AlbumsAdapter();
        albumsAdapter.moveToExamPage(url);
     */


    }
    /*public LoginPage(Context context){

        this.context = context;
    }*/

}
