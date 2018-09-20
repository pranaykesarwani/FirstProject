package pranay.example.com.magnusias4;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity //implements HomeFragment.SendMessage
{
    Activity context;
    LinearLayout llLeft;
    Button openDrawer;
    DrawerLayout drawer;
    boolean login_result;
    String login_username,login_password;
    LinearLayout submenulayout,subsubmenuLayout,subsubmenuLayout2,aboutLayout,resourcesLayout,gallerylayout,reflayout;

    Button btnTopMenu,btnSubMenu,btnSubMenu2,btnOurResources,btnContact,btnSampleClasses,btnPricing,btnBibliography,btnNotice,btnarticle,btnVideo,btnImage,btnTestSeries,btnSubjects,btnfaq,btnmission,btnMethodology,btnDirector,btnOurTeam,home,about,subject,btntest_series,btnresources,btngallery,btnresource,btnref, btnAboutUs;
    boolean topMenuFlag=false;
    boolean subMenuFlag=false;
    boolean subMenuFlag1  = false;
    boolean aboutMenu = false;
    TextView title_bar;
    VideoView videoView;
    String imei,android_id;

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase = null;


    boolean k=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);

        }
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {

            imei = telephonyManager.getDeviceId();
            android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }catch (Exception e){
            Log.i("Device ID Exception",e.toString());

        }


            Display display = getWindowManager(). getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        Log.i("Width", "" + dpWidth);
        Log.i("height", "" + dpHeight);
      //  Toast.makeText(this, "Width "+ width, Toast.LENGTH_SHORT).show();

Button serverSync = (Button)findViewById(R.id.serverSync);

serverSync.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

      //  syncFromServer();

        //MyAsyncTasks runner = new MyAsyncTasks();
      //  String sleepTime = time.getText().toString();
      //  runner.execute();
      /*  getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new LoginFragment()).commit();
      */

        view = LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_login,null);
         final EditText   username = (EditText)view.findViewById(R.id.login_username);
         final EditText  password = (EditText)view.findViewById(R.id.login_password);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false)
                .setTitle("Aunthentication")
                .setView(view)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         login_username = username.getText().toString();
                         login_password = password.getText().toString();

                        // login_result =


                                 authenticate(login_username,login_password,android_id);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Login Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

        drawer.closeDrawer(llLeft);




    }
});




//        RequestQueue queue1 = Volley.newRequestQueue(this);



       // Cursor cursor = databaseHelper.getSubjectData();
     //   Toast.makeText(this, ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new TestLayoutFragment0()).commit();

      /* if (savedInstanceState == null) {

        }*/


        //VimeoClient
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/
        context=this;
        // get slide list items from strings.xml
        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        openDrawer=(Button) findViewById(R.id.openDrawer);
        llLeft= (LinearLayout) findViewById(R.id.left_slide);


       /* submenulayout= (LinearLayout) findViewById(R.id.subMenuLayout);
        subsubmenuLayout= (LinearLayout) findViewById(R.id.subsubMenuLayout);
        subsubmenuLayout2 = (LinearLayout) findViewById(R.id.subsubMenuLayout0);
       */ aboutLayout = (LinearLayout) findViewById(R.id.aboutLayout);
        resourcesLayout = (LinearLayout) findViewById(R.id.resourcesLayout);
        gallerylayout = (LinearLayout) findViewById(R.id.resources_gallery_Layout);
        reflayout = (LinearLayout) findViewById(R.id.referencelayout);


       // reflayout.setVisibility(View.GONE);
        resourcesLayout.setVisibility(View.GONE);


       /* btnTopMenu = (Button) findViewById(R.id.topMenu1);
        btnSubMenu = (Button) findViewById(R.id.subMenu1);
        btnSubMenu2 = (Button) findViewById(R.id.subMenu2);
       */ home = (Button)findViewById(R.id.home);
        about = (Button)findViewById(R.id.about);
        btnresources = (Button)findViewById(R.id.resources);
     //   btnresources.setVisibility(View.GONE);
        btngallery =  (Button)findViewById(R.id.gallery);
        //btngallery.setVisibility(View.GONE);
        btnref  =(Button)findViewById(R.id.reference);
       // btnref.setVisibility(View.GONE);
        btnAboutUs = (Button)findViewById(R.id.aboutus);
        btnOurTeam = (Button)findViewById(R.id.ourteam);
        btnDirector= (Button)findViewById(R.id.director);
        btnMethodology = (Button)findViewById(R.id.methodology);
        btnmission = (Button)findViewById(R.id.mission);
        btnfaq = (Button)findViewById(R.id.faq);
        btnSubjects = (Button)findViewById(R.id.subjects);
        btnTestSeries = (Button)findViewById(R.id.test_series);
        btnImage = (Button)findViewById(R.id.g_image);
        btnVideo = (Button)findViewById(R.id.g_video);
        btnarticle = (Button)findViewById(R.id.article);
        btnNotice = (Button)findViewById(R.id.notice);
        btnBibliography= (Button)findViewById(R.id.bibliography);
        btnPricing = (Button)findViewById(R.id.pricing);
        btnSampleClasses = (Button)findViewById(R.id.sampleClasses);
        btnSampleClasses.setVisibility(View.GONE);
        btnContact= (Button)findViewById(R.id.contact);
        btnOurResources = (Button)findViewById(R.id.our_resources);

        title_bar = (TextView)findViewById(R.id.toolbar_title);



        btnOurResources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new OurResourcesFragment()).addToBackStack(null).commit();
                title_bar.setText("Our Resources");
                drawer.closeDrawer(llLeft);


            }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              //  Toast.makeText(context, "Home!!!", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new TestLayoutFragment0()).addToBackStack(null).commit();
                title_bar.setText("Home");



                drawer.closeDrawer(llLeft);

            }
        });

about.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (aboutLayout.getVisibility()==View.VISIBLE)
        {
            aboutLayout.setVisibility(View.GONE);
        }
        else
        {
            aboutLayout.setVisibility(View.VISIBLE);


        }
        if (resourcesLayout.getVisibility()==View.VISIBLE)
        {
            resourcesLayout.setVisibility(View.GONE);
        }
    }
});

btnresources.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (resourcesLayout.getVisibility()==View.VISIBLE)
        {
            resourcesLayout.setVisibility(View.GONE);
        }
        else
        {            resourcesLayout.setVisibility(View.VISIBLE);


        }

        if (gallerylayout.getVisibility()==View.VISIBLE)
        {
            gallerylayout.setVisibility(View.GONE);
        }

        if (reflayout.getVisibility()==View.VISIBLE)
        {
            reflayout.setVisibility(View.GONE);
        }

        if (aboutLayout.getVisibility()==View.VISIBLE)
        {
            aboutLayout.setVisibility(View.GONE);
        }



    }
});

btngallery.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (gallerylayout.getVisibility()==View.VISIBLE)
        {
            gallerylayout.setVisibility(View.GONE);
        }
        else
        {
            gallerylayout.setVisibility(View.VISIBLE);
        }


    }
});


btnref.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (reflayout.getVisibility()==View.VISIBLE)
        {
            reflayout.setVisibility(View.GONE);
        }
        else
        {
            reflayout.setVisibility(View.VISIBLE);
        }
    }
});
 btnAboutUs.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

       //  Toast.makeText(context, "About US!!!", Toast.LENGTH_SHORT).show();
         getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                 new AboutUsFragment()).addToBackStack(null).commit();

        title_bar.setText("About Us");

         drawer.closeDrawer(llLeft);

     }
 });
 btnOurTeam.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
      //   Toast.makeText(context, "Our Team!!!", Toast.LENGTH_SHORT).show();
         getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                 new OurTeamFragment()).addToBackStack(null).commit();


         title_bar.setText("Our Team");
         drawer.closeDrawer(llLeft);

     }
 });

 btnDirector.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

      //   Toast.makeText(context, "Director's Desk!!!", Toast.LENGTH_SHORT).show();
         getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                 new DirectorFragment()).addToBackStack(null).commit();


         title_bar.setText("From Director's Desk");
         drawer.closeDrawer(llLeft);

     }
 });

 btnMethodology.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
        // Toast.makeText(context, "Methodology!!!", Toast.LENGTH_SHORT).show();
         getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                 new MethodologyFragment()).addToBackStack(null).commit();
         title_bar.setText("Methodology");


         drawer.closeDrawer(llLeft);

     }
 });
 btnmission.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                 new MissionFragment()).addToBackStack(null).commit();

         title_bar.setText("Mission & Vision");

         drawer.closeDrawer(llLeft);

     }
 });

     btnfaq.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             //Toast.makeText(context, "FAQ!!!", Toast.LENGTH_SHORT).show();
             getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                     new FaqFragment()).addToBackStack(null).commit();

             title_bar.setText("FAQs");

             drawer.closeDrawer(llLeft);
         }
     });
btnSubjects.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new SubjectFragment()).addToBackStack(null).commit();

        title_bar.setText("Subjects");

        drawer.closeDrawer(llLeft);
    }
});

btnTestSeries.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new TestSeriesFragment()).addToBackStack(null).commit();

        title_bar.setText("Test Series");

        drawer.closeDrawer(llLeft);
    }
});


btnImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new TestLayoutFragment0()).commit();


        title_bar.setText("Images");
        drawer.closeDrawer(llLeft);
    }
});


btnVideo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getApplicationContext(),LiveUpdate.class);
        startActivity(intent);

       /* getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new VideoFragment()).commit();
*/
       /* videoView = (VideoView)findViewById(R.id.video1);
        Uri uri = Uri.parse("http://54.254.243.194/vid1.mp4");
       */// mediaController.setAnchorView(videoView);
       // videoView.setVideoURI(uri);
       // Toast.makeText(context, "Tap on video frames to Play/Pause", Toast.LENGTH_LONG).show();
       //videoView.start();
        title_bar.setText("Videos");
        drawer.closeDrawer(llLeft);


        //Uri uri = Uri.parse("");
        /*Uri uri = Uri.parse("http://54.254.243.194/vid1.mp4");
        videoView.setVideoURI(uri);
        videoView.start();
        *///VideoView videoView = (VideoView)findViewById(R.id.videoView);

        //mediaController.setAnchorView(videoView);

//        videoView.setMediaController(mediaController);
       // videoView.setVideoURI(uri);
        //videoView.start();

    }
});

btnarticle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new ArticleFragment()).addToBackStack(null).commit();
        title_bar.setText("Article from Reliable Sources");
        drawer.closeDrawer(llLeft);
    }
});

btnNotice.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new NoticeFragment()).addToBackStack(null).commit();
        title_bar.setText("Important Notice");
        drawer.closeDrawer(llLeft);
    }
});
btnBibliography.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new bibliographyFragment()).addToBackStack(null).commit();
        title_bar.setText("Reference from Bibliography");
        drawer.closeDrawer(llLeft);
    }
});

btnPricing.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new PricingFragment()).addToBackStack(null).commit();
        title_bar.setText("Pricing");
        drawer.closeDrawer(llLeft);
    }
});
btnSampleClasses.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new SampleClassesFragment()).addToBackStack(null).commit();

        drawer.closeDrawer(llLeft);
    }
});
btnContact.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new ContactFragment()).addToBackStack(null).commit();
        title_bar.setText("Contact Us");

        drawer.closeDrawer(llLeft);

    }
});




        /* btnTopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(submenulayout.getVisibility()==View.VISIBLE)
                {
                    submenulayout.setVisibility(View.GONE);
                }
                else{
                    submenulayout.setVisibility(View.VISIBLE);
                }


                if(subsubmenuLayout.getVisibility()==View.VISIBLE)
                {
                    subsubmenuLayout.setVisibility(View.GONE);
                    subMenuFlag=false;
                }

                topMenuFlag=!topMenuFlag;

            }
        });


        btnSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(subsubmenuLayout.getVisibility()==View.VISIBLE)
                {
                    subsubmenuLayout.setVisibility(View.GONE);
                }
                else{
                    subsubmenuLayout.setVisibility(View.VISIBLE);
                }

                subMenuFlag=!subMenuFlag;

            }
        });

        btnSubMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(subsubmenuLayout2.getVisibility()==View.VISIBLE)
                {
                    subsubmenuLayout2.setVisibility(View.GONE);
                }
                else{
                    subsubmenuLayout2.setVisibility(View.VISIBLE);
                }

                subMenuFlag1=!subMenuFlag1;

            }
        });
*/
        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k=!k;
                // unlockDrawer(k);
                if(k)
                    drawer.openDrawer(llLeft);
                else
                    drawer.closeDrawer(llLeft);
            }
        });

    }

    private void authenticate(String u, String p, final String android_id) {
        Log.i("Authenticate","1");
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        Log.i("Authenticate","2");

        StringRequest stringRequest  = new StringRequest(Request.Method.GET, "http://magnusias.com/app-api/app-login.php?username="+ u+"&password="+p+"&device_id="+android_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().trim().equals("1")){
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Log.i("Server Response",response);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                            Log.i("Server Response",response);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Server Error Response",error.toString());
            }
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);






        /*JsonObjectRequest jsonObjectRequest8 = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.43.144/mr_loc_tracker.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                            //JSONArray temp = response.getJSONArray("mission-vision");
                            Log.i("Login Result", "" +response.toString());
//                            counter =dataArray.length();
                            //prepareAlbums(dataArray);

*//*

                            for (int i = 0; i < temp.length(); i++) {
                                JSONObject buffer = temp.getJSONObject(i);
                                String id = buffer.getString("id");
                                String page_name = buffer.getString("page_name");
                                String details = buffer.getString("details");

                                databaseHelper.setMissionData(id,page_name,details);
                            }
*//*


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Director Desk Error",error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                params.put("username","pankaj.raj997@gmail.com" );//login_username
                params.put("password", "Mania@got1");//login_password
                params.put("device_id","452" );//


                return params;
            }




        };*/
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //queue.add(stringRequest);


        //return  false;
    }
/*
    @Override
    public void sendData(String message) {
        GeographyFragment geographyFragment = new GeographyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Subject",message);
        geographyFragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container,geographyFragment,null);

        fragmentTransaction.commit();



    }*/



public void getChapterList(String ChapterURL ){

    FragmentManager fragmentManager  = getSupportFragmentManager();
    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    ChapterListFragment chapterListFragment = new ChapterListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url",ChapterURL);
        chapterListFragment.setArguments(bundle);
    Log.i("ChapterURL",ChapterURL);
        fragmentTransaction.replace(R.id.frame_container,chapterListFragment).addToBackStack(null)
        .commit();



}

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
          //  getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



   //public void getExamTest(String name, String total_question,String marks_correct_ans,String negative_mark,String duration,String ques_pdf)
    public void getExamTest(String url)
    {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ExamTestFragment examTestFragment = new ExamTestFragment();

        Bundle bundle = new Bundle();
       /* bundle.putString("name",name);
        bundle.putString("total_question",total_question);
        bundle.putString("marks_correct_ans",marks_correct_ans);
        bundle.putString("negative_mark",negative_mark);
        bundle.putString("duration",duration);
        bundle.putString("ques_pdf",ques_pdf);*/
        bundle.putString("url",url);


        examTestFragment.setArguments(bundle);
      //  Log.i("url",url);

        fragmentTransaction.replace(R.id.frame_container,examTestFragment).addToBackStack(null)
                .commit();
    }
    public void getTestSeriesSubjectList(String url) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestSeriesFragment3 testSeriesFragment3 = new TestSeriesFragment3();

        Bundle bundle = new Bundle();
        bundle.putString("url",url);

        testSeriesFragment3.setArguments(bundle);
        Log.i("url",url);

        fragmentTransaction.replace(R.id.frame_container,testSeriesFragment3).addToBackStack(null)
                .commit();
    }

    public void getMockTestLevel2(String url) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MockTestSeriesLevel2Fragment mockTestSeriesLevel2Fragment = new MockTestSeriesLevel2Fragment();

        Bundle bundle = new Bundle();
        bundle.putString("url",url);

        mockTestSeriesLevel2Fragment.setArguments(bundle);
        Log.i("url",url);

        fragmentTransaction.replace(R.id.frame_container,mockTestSeriesLevel2Fragment).addToBackStack(null)
                .commit();
    }

    public void getTestSeriesSubjectListLevel2(String url) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestSeriesFragment4 testSeriesFragment4 = new TestSeriesFragment4();

        Bundle bundle = new Bundle();
        bundle.putString("url",url);

        testSeriesFragment4.setArguments(bundle);
        Log.i("url",url);

        fragmentTransaction.replace(R.id.frame_container,testSeriesFragment4).addToBackStack(null)
                .commit();
    }


    public void getTestSeries(String url) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestSeriesFragment2 testSeriesFragment2 = new TestSeriesFragment2();

        Bundle bundle = new Bundle();
        bundle.putString("url",url);

        testSeriesFragment2.setArguments(bundle);
        Log.i("url",url);

        fragmentTransaction.replace(R.id.frame_container,testSeriesFragment2).addToBackStack(null)
                .commit();

    }

    public void getTest(String url) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MockTestSeriesFragment mockTestSeriesFragment = new MockTestSeriesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);

        mockTestSeriesFragment.setArguments(bundle);
        Log.i("url",url);

        fragmentTransaction.replace(R.id.frame_container,mockTestSeriesFragment).addToBackStack(null)
                .commit();

    }

    public void getChapterVideoList(String chapterName, String chapterDescription, String chapterId) {

        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChapterVideoListFragment chapterVideoListFragment = new ChapterVideoListFragment();

        Bundle bundle = new Bundle();
        bundle.putString("chapterName",chapterName);
        bundle.putString("chapterDescription",chapterDescription);
        bundle.putString("chapterId",chapterId);
        chapterVideoListFragment.setArguments(bundle);
        Log.i("chapterName",chapterName);

        fragmentTransaction.replace(R.id.frame_container,chapterVideoListFragment).addToBackStack(null)
                .commit();

    }

    public void getTopicCardViewList(String TopicURL,String id)
    {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestLayoutFragment2 testLayoutFragment2 = new TestLayoutFragment2();

        Bundle bundle = new Bundle();
        bundle.putString("url",TopicURL);
        bundle.putString("id",id);
        testLayoutFragment2.setArguments(bundle);
      //  Log.i("ChapterURL",TopicURL);
        fragmentTransaction.replace(R.id.frame_container,testLayoutFragment2).addToBackStack(null)
                .commit();

    }

   public void getChapterCardViewList(String ChapterURL,String subject_id, String topic_id)
    {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestLayoutFragment3 testLayoutFragment3 = new TestLayoutFragment3();

        Bundle bundle = new Bundle();
        bundle.putString("url",ChapterURL);
        bundle.putString("subject_id",subject_id);
        bundle.putString("topic_id",topic_id);
     //   Toast.makeText(this, topic_id+" "+subject_id, Toast.LENGTH_SHORT).show();

        testLayoutFragment3.setArguments(bundle);
//        Log.i("ChapterURL",ChapterURL);
        fragmentTransaction.replace(R.id.frame_container,testLayoutFragment3).addToBackStack(null)
                .commit();

    }

    public void getVideoCardViewList(String id, String subject_id, String topic_id, String chapter_id)
    {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestLayoutFragment4 testLayoutFragment4 = new TestLayoutFragment4();

        Bundle bundle = new Bundle();
        bundle.putString("subject_id",subject_id);
        bundle.putString("topic_id",topic_id);
        bundle.putString("chapter_id",chapter_id);
        bundle.putString("id",id);

        testLayoutFragment4.setArguments(bundle);
        //Log.i("ChapterURL",ChapterURL);
        fragmentTransaction.replace(R.id.frame_container,testLayoutFragment4).addToBackStack(null)
                .commit();

    }
    public void getSubjectCardViewList(String url, String subject_name,String id)
    {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TestLayoutFragment testLayoutFragment = new TestLayoutFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("subject_name",subject_name);
        bundle.putString("id",id);
        testLayoutFragment.setArguments(bundle);
        //Log.i("ChapterURL",ChapterURL);
        fragmentTransaction.replace(R.id.frame_container,testLayoutFragment).addToBackStack(null)
                .commit();

    }

    public  void getVideoList(String chpater_content_id,String chapter_id){

        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VideoFragment videoFragment = new VideoFragment();

        Bundle bundle = new Bundle();
        bundle.putString("chpater_content_id",chpater_content_id);
        bundle.putString("chapter_id",chapter_id);
        videoFragment.setArguments(bundle);
        Log.i("IDs in Main",chpater_content_id+" "+chapter_id);
        fragmentTransaction.replace(R.id.frame_container,videoFragment).addToBackStack(null)
                .commit();

    }

    public  void getResourceList(String url){

        FragmentManager fragmentManager  = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VideoFragment videoFragment = new VideoFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url",url);

        videoFragment.setArguments(bundle);
        Log.i("url in Main",url);
        fragmentTransaction.replace(R.id.frame_container,videoFragment).addToBackStack(null).commit();
         /* Intent intent = new Intent(this,ResourceContent.class);
          intent.putExtra("url",url);
          startActivity(intent);
*/
    }


    String imageDownload(String URL,String filename){
        Drawable drawable = getResources().getDrawable(R.drawable.cover);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ContextWrapper wrapper = new ContextWrapper(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);// To remomove the exception

        try {
            int file_length = 0;
            java.net.URL url = new URL(URL);

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            file_length = urlConnection.getContentLength();
            //  Toast.makeText(wrapper, ""+file_length, Toast.LENGTH_SHORT).show();


            Log.i("Image Size",""+file_length);
            File file = wrapper.getDir("Images_PRK", MODE_PRIVATE);
            file = new File(file, filename + ".jpg");

            try {

                InputStream inputStream = new BufferedInputStream(url.openStream(),1024);
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
               // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();

            } catch (IOException e) // Catch the exception
            {
                Log.i("File Download Exception",e.toString());
                e.printStackTrace();
            }

            // Parse the gallery image url to uri
            Uri savedImageURI = Uri.parse(file.getAbsolutePath());
            return  savedImageURI.toString();
        }
        catch (Exception e)
        {
            Toast.makeText(wrapper, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("File Download Exception",e.toString());
        }

        return null;
    }

public void syncFromServer()
{
    databaseHelper = new DatabaseHelper(this);

    databaseHelper.onUpgrade(sqLiteDatabase,1,1);

    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

    JsonObjectRequest jsonObjectRequest8 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/mission-vision.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray temp = response.getJSONArray("mission-vision");
                        Log.i("Temp", "" +response.toString());
//                            counter =dataArray.length();
                        //prepareAlbums(dataArray);


                        for (int i = 0; i < temp.length(); i++) {
                            JSONObject buffer = temp.getJSONObject(i);
                            String id = buffer.getString("id");
                            String page_name = buffer.getString("page_name");
                            String details = buffer.getString("details");

                            databaseHelper.setMissionData(id,page_name,details);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Director Desk Error",error.toString());

        }
    });
    jsonObjectRequest8.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest8);



    JsonObjectRequest jsonObjectRequest7 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/faq.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray temp = response.getJSONArray("faq");
                        Log.i("Temp", "" +response.toString());
//                            counter =dataArray.length();
                        //prepareAlbums(dataArray);


                        for (int i = 0; i < temp.length(); i++) {
                            JSONObject buffer = temp.getJSONObject(i);
                            String id = buffer.getString("id");
                            String question = buffer.getString("question");
                            String answer = buffer.getString("answer");

                            databaseHelper.setFAQData(id,question,answer);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Director Desk Error",error.toString());

        }
    });
    jsonObjectRequest7.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest7);



    JsonObjectRequest jsonObjectRequest6 = new JsonObjectRequest(Request.Method.GET,
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
                            img_path = "http://magnusias.com/"+img_path;
                            img_path= imageDownload(img_path,"director"+id);

                            databaseHelper.setDirectorDeskData(id,name,post,img_path,details,email,mobile,hobbies,qualification,skills);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Director Desk Error",error.toString());

        }
    });
    jsonObjectRequest6.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest6);


    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/get-paper.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("paper");
                        Log.i("Temp", "" + dataArray.length());
                        //   server_row[0] = dataArray.length();
                        // Log.i("Table Rows",""+table_row);


                            /*Log.i("Result",server_row[0]+response.toString());
                            Log.i("Server Rows",""+server_row[0]);
                          */int i=0;
                        for ( i = 0; i < dataArray.length(); i++) {
                            JSONObject temp = dataArray.getJSONObject(i);
                            //  Log.i("Temp", temp.toString());


                            String name = temp.getString("name");
                            String heading = temp.getString("heading");
                            String sub_heading = temp.getString("sub_heading");

                            String details = temp.getString("details");
                            String id = temp.getString("id");

                            String image_path =  temp.getString("src");
                            image_path = "http://magnusias.com/"+image_path;
                            image_path= imageDownload(image_path,"Subject Categ"+id);

                            String url = temp.getString("for_subject");
                              databaseHelper.insertSubjectData(id,name,heading,sub_heading,details,image_path,url);

                            Log.i("Name", temp.getString("name"));
                              /*  if (server_row[0]!= table_row)
                                {



                                    Log.i("Final Result ","Row Updated!");
                                    //    Toast.makeText(this, table_row+" Tables upgraded!!! "+server_row[0], Toast.LENGTH_SHORT).show();
                                }*/




                        }
                        //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                        Log.i("Rows",""+i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Paper Error",error.toString());

        }
    });
    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest);

    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/all-subject.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("subject");
                        Log.i(" Subject Temp", response.toString());
                        //   server_row[0] = dataArray.length();
                        // Log.i("Table Rows",""+table_row);


                            /*Log.i("Result",server_row[0]+response.toString());
                            Log.i("Server Rows",""+server_row[0]);
                          */int i=0;
                        for ( i = 0; i < dataArray.length(); i++) {
                            JSONObject temp = dataArray.getJSONObject(i);
                            //  Log.i("Temp", temp.toString());


                            String name = temp.getString("name");
                            String heading = temp.getString("heading");
                            String categ = temp.getString("categ");

                            String details = temp.getString("details");
                            String id = temp.getString("id");

                            String image_path =  temp.getString("img_path");
                            image_path= imageDownload(image_path,"Subject"+id);


                            // String url = temp.getString("url");
                               databaseHelper.insertChapterData(id,name,heading,details,categ,image_path);

                            Log.i("Name", temp.getString("name"));
                        }
                        //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                        Log.i("Rows",""+i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Subject Error",error.toString());

        }
    });
    jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest1);

    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/all-subject-topic.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("topic");
                        Log.i(" Subject Temp", response.toString());
                        //   server_row[0] = dataArray.length();
                        // Log.i("Table Rows",""+table_row);


                            /*Log.i("Result",server_row[0]+response.toString());
                            Log.i("Server Rows",""+server_row[0]);
                          */int i=0;
                        for ( i = 0; i < dataArray.length(); i++) {
                            JSONObject temp = dataArray.getJSONObject(i);
                            //  Log.i("Temp", temp.toString());


                            String name = temp.getString("name");
                            String subject_id = temp.getString("subject_id");

                            String details = temp.getString("details");
                            String id = temp.getString("id");

                            String image_path =  temp.getString("img_path");
                            image_path = "http://magnusias.com/"+image_path;

                            //  image_path = "http://magnusias.com/"+image_path;
                            image_path= imageDownload(image_path,"Topic"+id);

                             databaseHelper.insertTopicData(id,name,subject_id,details,image_path);

                            Log.i("Name", temp.getString("name"));
                        }
                        //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                        Log.i("Rows",""+i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Topic Error",error.toString());

        }
    });
    jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest2);

    JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/all-chapter.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("chapter");
                        Log.i(" Subject Temp", response.toString());
                        //   server_row[0] = dataArray.length();
                        // Log.i("Table Rows",""+table_row);


                            /*Log.i("Result",server_row[0]+response.toString());
                            Log.i("Server Rows",""+server_row[0]);
                          */int i=0;
                        for ( i = 0; i < dataArray.length(); i++) {
                            JSONObject temp = dataArray.getJSONObject(i);
                            //  Log.i("Temp", temp.toString());


                            String subject_id = temp.getString("subject_id");
                            String heading = temp.getString("heading");
                            String topic_id = temp.getString("topic_id");

                            String details = temp.getString("details");
                            String id = temp.getString("id");

                            String ch_num =  temp.getString("ch_num");

                            // String url = temp.getString("url");
                              databaseHelper.insertChapterList(id,subject_id,topic_id,heading,details,ch_num);

//                                Log.i("Name", temp.getString("name"));
                        }
                        //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                        Log.i("Rows",""+i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i(" Chapter Error",error.toString());

        }
    });
    jsonObjectRequest3.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest3);


    JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/our-team.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("our-team");
                        Log.i(" Subject Temp", response.toString());
                        //   server_row[0] = dataArray.length();
                        // Log.i("Table Rows",""+table_row);


                            /*Log.i("Result",server_row[0]+response.toString());
                            Log.i("Server Rows",""+server_row[0]);
                          */int i=0;
                        for ( i = 0; i < dataArray.length(); i++) {
                            JSONObject temp = dataArray.getJSONObject(i);
                            //  Log.i("Temp", temp.toString());


                            String skills = temp.getString("skills");
                            String name = temp.getString("name");
                            String post = temp.getString("post");

                            String details = temp.getString("details");
                            String id = temp.getString("id");

                            String src =  temp.getString("src");
                            String mobile =  temp.getString("mobile");

                            String hobbies =  temp.getString("hobbies");
                            String email = temp.getString("email");
                            src = "http://magnusias.com/"+src;
                            src= imageDownload(src,"Team Mate"+id);


                            String qualification = temp.getString("qualification");
                                 databaseHelper.ourTeamData(id,name,post,src,details,email,mobile,hobbies,qualification,skills);

//                                Log.i("Name", temp.getString("name"));
                        }
                        //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                        Log.i("Rows",""+i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Our Team Error",error.toString());

        }
    });
    jsonObjectRequest4.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest4);



    JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET,
            "http://magnusias.com/app-api/all-video.php", null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("video");
                        Log.i(" Subject Temp", response.toString());
                        //   server_row[0] = dataArray.length();
                        // Log.i("Table Rows",""+table_row);


                            /*Log.i("Result",server_row[0]+response.toString());
                            Log.i("Server Rows",""+server_row[0]);
                          */int i=0;
                        for ( i = 0; i < dataArray.length(); i++) {
                            JSONObject temp = dataArray.getJSONObject(i);
                            //  Log.i("Temp", temp.toString());


                            String id = temp.getString("id");
                            String access_type = temp.getString("access_type");
                            String subject = temp.getString("subject");

                            String video_id = temp.getString("video_id");
                            String pdf_document = temp.getString("pdf_document");

                            String description =  temp.getString("description");
                            String img = temp.getString("img");
                            img = "http://magnusias.com/upload/chapter/thumb/"+img;
                            String cc_heading =  temp.getString("cc_heading");

                            String chapter_num =  temp.getString("chapter_num");
                            String chapter = temp.getString("chapter");

                            String topic = temp.getString("topic");
                            img= imageDownload(img,"Video"+id);


                            //String chapter = temp.getString("chapter");
                             databaseHelper.chapterContentData(id,access_type,subject,video_id,pdf_document,description,img,cc_heading,chapter_num,chapter,topic);

                                Log.i("Name", temp.getString("cc_heading"));
                        }
                        //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                        Log.i("Rows",""+i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("Video Error",error.toString());

        }
    });
    jsonObjectRequest5.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    queue.add(jsonObjectRequest5);






}




    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

            // implement API in background and store the response in current variable
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    syncFromServer();
                    // return the data to onPostExecute method
                    System.out.print(current);
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {
                // JSON Parsing of data


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
