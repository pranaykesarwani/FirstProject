package pranay.example.com.magnusias4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    String android_id,url,pageType,chpater_content_id,chapter_id,videoID,videoTitle,pdfPath;
    DatabaseHelper databaseHelper ;

    boolean isLogin_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        Intent intent =getIntent();

        pageType = intent.getStringExtra("pageType");
        chapter_id = intent.getStringExtra("chapter_id");
        videoID =  intent.getStringExtra("videoID");
        videoTitle = intent.getStringExtra("videoTitle");
        chpater_content_id = intent.getStringExtra("chpater_content_id");
        pdfPath = intent.getStringExtra("pdfPath");
        url = intent.getStringExtra("url");
        username = (EditText)findViewById(R.id.login_username);
        password = (EditText)findViewById(R.id.login_password);
        Button login = (Button)findViewById(R.id.login);
        android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        String db_login_status="";
        try{
        Cursor cursor =null;
        cursor = databaseHelper.getSessionData();
        Log.i("Login Data",""+cursor.getCount());
        cursor.moveToFirst();
        db_login_status = cursor.getString(0);
        Log.i("Login Data",db_login_status);
/*        if (db_login_status.equals("true"))
        {
            //LoginSession.login_status = true;
             Log.i("Login inside Condition",db_login_status);
            //MainActivity mainActivity = new MainActivity();
          //  mainActivity.btnLogout.setVisibility(View.VISIBLE);
        }
        else
        {
         //   LoginSession.login_status = false   ;
         //        MainActivity mainActivity = new MainActivity();
           // mainActivity.btnLogout.setVisibility(View.VISIBLE);
            Log.i("Login inside Condition",db_login_status);
        }*/
        }catch(Exception e){Log.i("Session",e.toString());}
        Log.i("db_login_status","Result "+db_login_status);
        if ((LoginSession.login_status==true)||(db_login_status.equals("true")))
        {
            Intent i = new Intent(this,VideoAcitvity.class);
            if (pageType.equals("video")){
               /* i.putExtra("chapter_id",chapter_id);
                i.putExtra("chpater_content_id",chpater_content_id);
                startActivity(i);
                finish();*/
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                relativeLoginLayout.setBackgroundColor(Color.WHITE);
                loginLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                VideoFragment videoFragment = new VideoFragment();

                Bundle bundle = new Bundle();
                bundle.putString("chapter_id",chapter_id);
                bundle.putString("chpater_content_id",chpater_content_id);
                bundle.putString("videoID",videoID);
                bundle.putString("videoTitle",videoTitle);
                bundle.putString("pdfPath",pdfPath);
                videoFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                        videoFragment).commit();

            }
            if (pageType.equals("exam"))
            {
                i = new Intent(LoginActivity.this,ExamActivity.class);

                i.putExtra("url",url);
                startActivity(i);
                finish();

            }
            if (pageType.equals("random"))
            {
                //Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                relativeLoginLayout.setBackgroundColor(Color.WHITE);
                loginLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

                //FragmentManager fragmentManager  = getSupportFragmentManager();
              //  android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RandomExamTest randomExamTest = new RandomExamTest();

                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                randomExamTest.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                        randomExamTest).commit();

               /* i = new Intent(LoginActivity.this,ExamActivity.class);

                i.putExtra("url",url);
                startActivity(i);
                finish();
*/
            }

            if (pageType.equals("mock_exam"))
            {
                //Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                relativeLoginLayout.setBackgroundColor(Color.WHITE);
                loginLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

                //FragmentManager fragmentManager  = getSupportFragmentManager();
                //  android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //    RandomExamTest randomExamTest = new RandomExamTest();
                MockTestMains mockTestMains = new MockTestMains();
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                Log.i("Main URL in Login",url);
                mockTestMains.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                        mockTestMains).commit();

            }


            if (pageType.equals("offline_Videos"))
            {


                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                relativeLoginLayout.setBackgroundColor(Color.WHITE);

                loginLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                OfflineVideoList offlineVideoList = new OfflineVideoList();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                        offlineVideoList).commit();
               /* FragmentManager fragmentManager  = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                OfflineVideoList offlineVideoList = new OfflineVideoList();
                fragmentTransaction.replace(R.id.frame_container_0,
                        offlineVideoList).commit();
*/
               /* OfflineVideoList offlineVideoList = new OfflineVideoList();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                        offlineVideoList).commit();
             *///   Toast.makeText(this, "Offline_Videos", Toast.LENGTH_SHORT).show();
            }


            if (pageType.equals("offline_Test"))
            {


                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                relativeLoginLayout.setBackgroundColor(Color.WHITE);

                loginLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);

                Toast.makeText(this, "Offline Test will be available soon!", Toast.LENGTH_SHORT).show();

            }


        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue loginqueue = Volley.newRequestQueue(LoginActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://magnusias.com/app-api/app-login.php?username=" + username.getText().toString().trim() + "&password=" + password.getText().toString() + "&device_id=" + android_id, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject temp = null;
                        try {
                            temp = response.getJSONObject("login-status");

                        String login_status = temp.getString("login-status");
                        String user_type = temp.getString("user_type");

                            Intent intent;
                            if (login_status.equals("success") && (user_type.equals("paid"))) {
                                try{

                                    databaseHelper.setSession("true");

                                     LoginSession.login_status = true;
                                     Log.i("Session.login_status",""+LoginSession.login_status);

                                      if (pageType.equals("exam")){
                                    intent = new Intent(LoginActivity.this,ExamActivity.class);
                                    Log.i("Login URL",url);
                                   intent.putExtra("url",url);
                                   startActivity(intent);
                                   finish();
                                    }

                                    if (pageType.equals("mock_exam"))
                                    {
                                        //Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                                        LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                                        RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                                        relativeLoginLayout.setBackgroundColor(Color.WHITE);
                                        loginLayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);

                                        //FragmentManager fragmentManager  = getSupportFragmentManager();
                                        //  android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                     //   RandomExamTest randomExamTest = new RandomExamTest();
                                        MockTestMains mockTestMains = new MockTestMains();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("url",url);
                                        Log.i("Main URL in Login",url);
                                        mockTestMains.setArguments(bundle);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                                                mockTestMains).commit();

                                    }


                                    if(pageType.equals("video"))
                                    {
                                        //Toast.makeText(LoginActivity.this, "Video Page!!! "+ chapter_id+" "+chpater_content_id, Toast.LENGTH_SHORT).show();
                                        //Log.i("Page Type","Video page"+ chapter_id+chpater_content_id);

                                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                                        LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                                        RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                                        relativeLoginLayout.setBackgroundColor(Color.WHITE);
                                        loginLayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        VideoFragment videoFragment = new VideoFragment();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("chapter_id",chapter_id);
                                        bundle.putString("chpater_content_id",chpater_content_id);
                                        bundle.putString("videoID",videoID);
                                        bundle.putString("videoTitle",videoTitle);
                                        bundle.putString("pdfPath",pdfPath);
                                        videoFragment.setArguments(bundle);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                                                videoFragment).commit();

                                       /* intent = new Intent(LoginActivity.this,VideoAcitvity.class);
//                                        Log.i("Login URL",url);
                                        intent.putExtra("chapter_id",chapter_id);
                                        intent.putExtra("chpater_content_id",chpater_content_id);
                                        startActivity(intent);
                                        finish();
*/

                                        //videoFragment();
                                        /*FragmentManager fragmentManager  = getSupportFragmentManager();
                                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        VideoFragment videoFragment = new VideoFragment();
                                        Log.i("Pagetype",pageType);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("chpater_content_id",chpater_content_id);
                                        bundle.putString("chapter_id",chapter_id);
                                        videoFragment.setArguments(bundle);
                                        Log.i("IDs in Main",chpater_content_id+" "+chapter_id);
                                        fragmentTransaction.replace(R.id.frame_container,videoFragment).addToBackStack(null)
                                                .commit();*/



                                    }

                                    if (pageType.equals("offline_Videos"))
                                    {   LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                                        LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                                        RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                                        relativeLoginLayout.setBackgroundColor(Color.WHITE);

                                        loginLayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        OfflineVideoList offlineVideoList = new OfflineVideoList();
                                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                                                offlineVideoList).commit();



                                      /*  FragmentManager fragmentManager  = getSupportFragmentManager();
                                          android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        OfflineVideoList offlineVideoList = new OfflineVideoList();
                                        fragmentTransaction.replace(R.id.frame_container_0,
                                                offlineVideoList).commit();
*/

                                       // Toast.makeText(LoginActivity.this, "Offline_Videos", Toast.LENGTH_SHORT).show();
                                    }

                                    if (pageType.equals("offline_Test"))
                                    {


                                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                                        LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                                        RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                                        relativeLoginLayout.setBackgroundColor(Color.WHITE);

                                        loginLayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);

                                        Toast.makeText(LoginActivity.this, "Offline Test will be available soon!", Toast.LENGTH_SHORT).show();

                                    }

                                    if (pageType.equals("random"))
                                    {
                                        //Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.frame_container_0);
                                        LinearLayout loginLayout = (LinearLayout)findViewById(R.id.login_Layout);
                                        RelativeLayout relativeLoginLayout = (RelativeLayout) findViewById(R.id.linearLoginPage);
                                        relativeLoginLayout.setBackgroundColor(Color.WHITE);

                                        loginLayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);

                                        //FragmentManager fragmentManager  = getSupportFragmentManager();
                                        //  android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        RandomExamTest randomExamTest = new RandomExamTest();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("url",url);
                                        randomExamTest.setArguments(bundle);
                                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_0,
                                                randomExamTest).commit();

               /* i = new Intent(LoginActivity.this,ExamActivity.class);

                i.putExtra("url",url);
                startActivity(i);
                finish();
*/
                                    }




                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Invalid Credentials or You don't have rights to access content", Toast.LENGTH_LONG).show();
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

                loginqueue.add(jsonObjectRequest);

            }
        });


        //View view;

       // view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.fragment_login,null);


    }

}
