package pranay.example.com.magnusias4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    FragmentTransaction fragmentManager;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView)view.findViewById(R.id.card_view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
           // overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public AlbumsAdapter(){}

    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        final String url = album.url;
        final int stage = album.getStage();
        final String id = album.getId();
        final String subject_name = album.getName();
        final String subject_id= album.getSubject_id();
        final String topic_id = album.getTopic_id();
        final String chapter_id = album.getChapter_id();
        final String imagePath = album.getImg_path();
        final  String chpater_content_id = album.getChpater_content_id();
        final String sub_menu = album.getSub_menu();
        final String test_type = album.getTest_type();
        final int stage2 = album.getStage2();

        if (imagePath!=null) {
            Uri path = Uri.parse(imagePath);
            holder.thumbnail.setImageURI(path);
        }
        else
            holder.thumbnail.setImageResource(R.drawable.english);

        if (album.img_path!=null)
        Glide.with(mContext).load(album.img_path).into(holder.thumbnail);//---------------------------------For Image

       // Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();

      //  Picasso.get().load(album.getImg_path()).into();

        // holder.count.setText(album.getNumOfSongs() + " songs");
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stage ==0)
                {
                    MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getSubjectCardViewList(url,subject_name,id);
                }
                if (stage==1)
                {
                    MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getTopicCardViewList(url,id);



                }
                if (stage==2)
                {
                    MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getChapterCardViewList(url,subject_id,topic_id);

                }
                if (stage==3)
                {
                    MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getVideoCardViewList(id,subject_id,topic_id,chapter_id);

                }
                if(stage==4)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.checkLogin(url,"video",chpater_content_id,chapter_id);
                    //mainActivity.getVideoList(chpater_content_id,chapter_id);
                }
                if(stage==1000)
                {
                    MainActivity mainActivity = (MainActivity)mContext;
                //    Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();

                    mainActivity.getResourceList(url);
                }
                if(stage==2000)
                {
                    if (sub_menu.equals("No"))
                    {
                        if (id.equals("58")){

                            MainActivity mainActivity = (MainActivity)mContext;
                           // Toast.makeText(mainActivity, "Wait....", Toast.LENGTH_LONG).show();
                            //  mainActivity.checkLogin(url,"random",null,null);

                            //Log.i("MockTest mains","************************Working on it********************************");
                            //mainActivity.getMockTestMains(url);
                            //Toast.makeText(mainActivity, "Wait.....", Toast.LENGTH_SHORT).show();
                            mainActivity.getTest(url,"58");
                        }
                        else {
                            MainActivity mainActivity = (MainActivity) mContext;
                            Toast.makeText(mContext, "" + stage, Toast.LENGTH_SHORT).show();
                            mainActivity.getTest(url, "");
                        }

//                        Toast.makeText(mContext, sub_menu, Toast.LENGTH_SHORT).show();
                    }
                   // Toast.makeText(mContext, sub_menu, Toast.LENGTH_SHORT).show();
                   if (sub_menu.equals("Yes"))
                    {
                        MainActivity mainActivity = (MainActivity)mContext;

                        mainActivity.getTestSeries(url);

                    }

                }
                if(stage==2001)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getTestSeriesSubjectList(url);
                   // Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                    Log.i("url",url);

                }
                if(stage==2002)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getTestSeriesSubjectListLevel2(url);
                   // Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                    Log.i("url",url);

                }
                if(stage==2020)
                {
                    MainActivity mainActivity = (MainActivity)mContext;
                           Log.i("M Id in AlbumAdpter",id);
                    /*if (id.equals("58")){
                        Toast.makeText(mainActivity, "Wait.....", Toast.LENGTH_SHORT).show();
                    }*/
                    if (test_type.equals("random"))
                    {
                       // mainActivity.getRandomTest(url);
                        mainActivity.checkLogin(url,"random",null,null);
                        //Toast.makeText(mainActivity, "Random", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (stage2 == 58)
                        {
                            mainActivity.checkLogin(url,"mock_exam",null,null);
                        }
                        else
                         mainActivity.checkLogin(url,"exam",null,null);
                     /*   if (result==true) {
                            mainActivity.getExamTest(url);
                         Log.i("Result",""+result);
                        }*/
                    }
                    //Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                    Log.i("url",url);

                }
                if(stage==2021)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getExamTestLevel5(url);
                 //   Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();
                    Log.i("stage",""+stage);

                }
                if(stage==2022)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.checkLogin(url,"exam",null,null);
                 //    Toast.makeText(mContext, ""+stage2, Toast.LENGTH_SHORT).show();
                    Log.i("stage",""+stage);

                }
                if(stage==2023)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                     mainActivity.getExamTestLevel6(url);
                  //  Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();
                    Log.i("stage",""+stage);

                }




                //   Toast.makeText(mContext, holder.title.getText(), Toast.LENGTH_SHORT).show();
                // fragmentManager.replace(R.id.frame_container,new HistoryFragment()).addToBackStack(null).commit();
              //  Intent intent = new Intent (mContext,MainActivity.class);
               // mContext.startActivity(intent);
                //new Close();

/*

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new HistoryFragment();

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, myFragment).addToBackStack(null).commit();
*/




            }

        });

        final MainActivity mainActivity = (MainActivity)mContext;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragmentManager.replace(R.id.frame_container,new Subjects()).addToBackStack(null).commit();
                if (stage ==0)
                {

                    mainActivity.getSubjectCardViewList(url,subject_name,id);
                }
                if (stage==1)
                {
                   // MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getTopicCardViewList(url,id);


                }
                if (stage==2)
                {
                    //MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getChapterCardViewList(url,subject_id,topic_id);

                }
                if (stage==3)
                {
                   // MainActivity mainActivity = (MainActivity)mContext;
                    mainActivity.getVideoCardViewList(id,subject_id,topic_id,chapter_id);

                }
                if(stage==4)
                {
                   // mainActivity.getVideoList(chpater_content_id,chapter_id);
                    mainActivity.checkLogin(url,"video",chpater_content_id,chapter_id);

                    // Toast.makeText(mContext, "Videos will be available shortly!", Toast.LENGTH_SHORT).show();
                }
                if(stage==1000)
                {
                    mainActivity.getResourceList(url);
                    //Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();

                }
                if(stage==2000)
                {
                    if (sub_menu.equals("No"))
                    {
                        mainActivity.getTest(url,"58");

                       // Toast.makeText(mContext, sub_menu, Toast.LENGTH_SHORT).show();
                    }
                    if (sub_menu.equals("Yes"))
                    {

                        mainActivity.getTestSeries(url);
                        Toast.makeText(mContext, sub_menu, Toast.LENGTH_SHORT).show();
                    }

                }
                if(stage==2001)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getTestSeriesSubjectList(url);
                   // Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();

                }
                if(stage==2020)
                {
                    MainActivity mainActivity = (MainActivity)mContext;


                    if (test_type.equals("random"))
                    {
                     //   mainActivity.getRandomTest(url);
                        mainActivity.checkLogin(url,"random",null,null);
                        //Toast.makeText(mainActivity, "Random", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (stage2 == 58)
                        {
                            mainActivity.checkLogin(url,"mock_exam",null,null);
                        }
                        else
                            mainActivity.checkLogin(url,"exam",null,null);

                     /*   if (result==true) {
                            mainActivity.getExamTest(url);
                         Log.i("Result",""+result);
                        }*/
                    }
                    //Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                    Log.i("url",url);

                }
                if(stage==2021)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getExamTestLevel5(url);
                   // Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();
                    Log.i("stage",""+stage);

                }
                if(stage==2022)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.checkLogin(url,"exam",null,null);
                 //   Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();
                    Log.i("stage",""+stage);

                }
                if(stage==2023)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getExamTestLevel6(url);
               //     Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();
                    Log.i("//stage",""+stage);

                }
                if(stage==2024)
                {
                    MainActivity mainActivity = (MainActivity)mContext;

                    mainActivity.getExamTestLevel7(url);
                //    Toast.makeText(mContext, ""+stage, Toast.LENGTH_SHORT).show();
                    Log.i("//stage",""+stage);

                }


            }
        });
        // loading album cover using Glide library

/*
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });*/
    }



    /**
     * Showing popup menu when tapping on 3 dots
     */
 /*   private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }*/

    /**
     * Click listener for popup menu items
     */
  /*  class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        *//*@Override
      public boolean onMenuItemClick(MenuItem menuItem) {
          switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }*//*
    }*/

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class Close extends AppCompatActivity{
        public Close() {
            finish();
        }
    }


}
