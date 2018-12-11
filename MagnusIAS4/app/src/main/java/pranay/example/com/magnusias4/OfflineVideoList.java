package pranay.example.com.magnusias4;


import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import magnus.example.com.magnusias4.AnswerPDF;

import static android.content.Context.MODE_PRIVATE;

public class OfflineVideoList extends Fragment {

    private List<OfflineVideoTableClass> dataFeed= new ArrayList<OfflineVideoTableClass>();

    ListView listView;
    DatabaseHelper databaseHelper;
     ArrayAdapter<OfflineVideoTableClass> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_videos_list, container, false);
        setHasOptionsMenu(true);
      //  Toast.makeText(getActivity(), "Press & Hold ", Toast.LENGTH_SHORT).show();
        adapter   = new OfflineVideoList.Adapter();
            databaseHelper = new DatabaseHelper(getActivity());
            Cursor cursor = null;
            dataFeed.clear();
            cursor = databaseHelper.getOVTData();
            if (cursor.getCount()>0)
            {   cursor.moveToFirst();


            do{
                Log.i(cursor.getString(0),cursor.getString(cursor.getColumnIndex("videoID")));
                Log.i(cursor.getString(1),cursor.getString(cursor.getColumnIndex("offline_pdf_path")));
               /* Log.i(cursor.getString(2),cursor.getString(2));
                Log.i(cursor.getString(3),cursor.getString(3));
                Log.i(cursor.getString(4),cursor.getString(4));
//*/
                dataFeed.add(new OfflineVideoTableClass(cursor.getString(0),cursor.getString(2),cursor.getString(1),cursor.getString(4),cursor.getString(3)));
                adapter.notifyDataSetChanged();

            }while (cursor.moveToNext());


              //  String v_id = cursor.getString(0);
              //  Toast.makeText(getActivity(), v_id, Toast.LENGTH_SHORT).show();
            }

        //dataFeed.add(new OfflineVideoTableClass(cursor.getString(0),cursor.getString(2), cursor.getString(1), cursor.getString(3)));


            /*
               if (cursor.getCount()>0) {
                    while (cursor.moveToNext())
                    {


                    }




                }*/

            listView = view.findViewById(R.id.offline_video_listview);

            listView.setAdapter(adapter );



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                final OfflineVideoTableClass currentItem = dataFeed.get(position);
              //  Toast.makeText(getActivity(),"Loading...", Toast.LENGTH_SHORT).show();

               View view = LayoutInflater.from(getActivity()).inflate(R.layout.offline_video_player,null);
               VideoView videoView = view.findViewById(R.id.offline_video_player);
                //Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
           //    VideoFragment videoFragment = new VideoFragment();
               //String videoPath  = decryption_second_step_process(currentItem.getVideoFilePath());

                /*OfflineVideoPlayer  offlineVideoPlayer = new OfflineVideoPlayer();

                Bundle bundle = new Bundle();
                bundle.putString("videoPath",currentItem.getVideoFilePath());
                bundle.putString("videoTitle",currentItem.getVideoTitle());
                offlineVideoPlayer.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container_0,
                        offlineVideoPlayer).commit();*/
                Intent intent = new Intent(getActivity(),OfflinePlayerActivity.class);
                intent.putExtra("videoPath",currentItem.getVideoFilePath());
                intent.putExtra("link",currentItem.getOffline_pdf_path());
                intent.putExtra("videoTitle",currentItem.getVideoTitle());
                startActivity(intent);
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false)
                        .setTitle("Please select option")
                        .setView(view)
                        .setPositiveButton("View PDF", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(getActivity(),AnswerPDF.class);
                                intent.putExtra("link",currentItem.getOffline_pdf_path());
                                intent.putExtra("linkType","offline");
                                startActivity(intent);
                                //login_username = username.getText().toString();
                                //login_password = password.getText().toString();

                                // login_result =


                                //authenticate(login_username,login_password,android_id);
                            }
                        })
                        .setNegativeButton("Watch Video", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(, "Login Cancelled", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();*/





             //   Toast.makeText(getActivity(), videoPath, Toast.LENGTH_SHORT).show();
                   //           videoView.setVideoPath(videoPath);
               // videoView.setMediaController(new MediaController(getActivity()));
                 //             videoView.start();
               // final EditText   username = (EditText)view.findViewById(R.id.login_username);
                //final EditText  password = (EditText)view.findViewById(R.id.login_password);
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true)
                        .setTitle("Offline Video Streaming")
                        .setView(view)
                        .setPositiveButton("", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                              //  login_username = username.getText().toString();
                                //login_password = password.getText().toString();

                                // login_result =


                              //  authenticate(login_username,login_password,android_id);
                            }
                        })

                        .show();
              */  //  Toast.makeText(getActivity(), "Videos will be available shortly!", Toast.LENGTH_SHORT).show();
                //
                //              MainActivity mainActivity = (MainActivity)getActivity();
//                mainActivity.getChapterVideoList(currentItem.getChapterURL());



            }
        });

/*
        offline_video_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* PopupMenu popupMenu = new PopupMenu(getActivity(),view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.context_offline_video_list_menu, popupMenu.getMenu());*//*
            }
        });*/
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       getActivity().getMenuInflater().inflate(R.menu.context_offline_video_list_menu, menu);
  /*      super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_offline_video_list_menu, menu);
        menu.setHeaderTitle("Select The Action");
*/

    }
/*
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId()==R.id.menu_show_offline_pdf){
            Toast.makeText(getActivity(),"calling code",Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==R.id.){
            Toast.makeText(getActivity(),"sending sms code",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }*/


    private class Adapter extends ArrayAdapter<OfflineVideoTableClass> {
        public Adapter() {
            super(getActivity(), R.layout.fragment_offline_videos_list,dataFeed);
        }



        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.fragment_offline_video_list_sample,null,false);
            }
            final OfflineVideoTableClass currentItem = dataFeed.get(position);
            ImageView videoView = convertView.findViewById(R.id.offline_videoView);
            TextView videoName = convertView.findViewById(R.id.videoName);
            videoName.setText(currentItem.getVideoTitle());
        //    videoView.setVideoPath(currentItem.getVideoFilePath());
            final ImageView offline_video_option;
            offline_video_option = convertView.findViewById(R.id.offline_video_option);
            registerForContextMenu(offline_video_option);
            offline_video_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  Toast.makeText(getActivity(), "dssssssssssss", Toast.LENGTH_SHORT).show();

                    PopupMenu popup = new PopupMenu(getActivity(), offline_video_option);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.context_offline_video_list_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                            databaseHelper.removeVideo(currentItem.videoID);
                            dataFeed.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), currentItem.getVideoTitle()+" Video Deleted!", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });


                }
            });
            return  convertView;

        }
    }


        class OfflineVideoTableClass{
        String videoID,videoTitle,videoFilePath,offlineAvailability,offline_pdf_path;


            public OfflineVideoTableClass(String videoID, String videoTitle, String videoFilePath, String offlineAvailability, String offline_pdf_path) {
                this.videoID = videoID;
                this.videoTitle = videoTitle;
                this.videoFilePath = videoFilePath;
                this.offlineAvailability = offlineAvailability;
                this.offline_pdf_path = offline_pdf_path;

            }

            public String getVideoID() {

            return videoID;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public String getVideoFilePath() {
            return videoFilePath;
        }

        public String getOfflineAvailability() {
            return offlineAvailability;
        }
            public String getOffline_pdf_path() {
                return offline_pdf_path;
            }

            public void setOffline_pdf_path(String offline_pdf_path) {
                this.offline_pdf_path = offline_pdf_path;
            }

        }



}

