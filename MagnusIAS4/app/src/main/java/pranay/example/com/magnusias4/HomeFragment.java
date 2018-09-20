package pranay.example.com.magnusias4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment
//        implements GeographyFragment.OnMessageSendListener
{

  LinearLayout indian_society,live_updates,current_affairs,news,ethics_integrity,ethics,science,indian_economy,ecology,international_relation,english,geography,history,indiansociety,polity,disaster_management;
    FragmentTransaction fragmentManager;
    Bundle bundle;
    String key,value;
    //SendMessage sendMessage;

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
        live_updates = (LinearLayout)view.findViewById(R.id.live_updates);
        indian_society  = (LinearLayout)view.findViewById(R.id.indiansociety);



         fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();

         live_updates.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getActivity(),LiveUpdate.class);
                 startActivity(intent);
                 // /fragmentManager.replace(R.id.frame_container,new LiveUpdatesFragment()).addToBackStack(null).commit();
             }
         });
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
                 fragmentManager.replace(R.id.frame_container,new IndianSocietyFragment()).addToBackStack(null).commit();
             }
         });

        bundle=new Bundle();
        //bundle.putString();
        key = "Subject";
         value = "English";

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "English", Toast.LENGTH_SHORT).show();

               // Bundle bundle=new Bundle();
                //bundle.putString("Subject","English");
                bundle=new Bundle();

                bundle.putString("Subject","English");

           EnglishFragment englishFragment = new EnglishFragment();
           englishFragment.setArguments(bundle);
                fragmentManager.replace(R.id.frame_container,
                        new EnglishFragment()).addToBackStack("English").commit();
                Log.i("English","English");
            }
        });
        geography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              /*  Bundle bundle  = new Bundle();
                bundle.putString("Subject","Geogra");*/

               GeographyFragment geographyFragment = new GeographyFragment();
                geographyFragment.setArguments(bundle);                 //sendMessage.sendData("Geography");
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
                        new IndianSocietyFragment()).addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        });
    }


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_home, container, false);


    }

/*
    @Override
    public void onMessageSend(String message) {
        GeographyFragment geographyFragment = new GeographyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Subject","Geography");
        geographyFragment.setArguments(bundle);
        
    }
    public interface SendMessage{

        public void sendData(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendMessage = (SendMessage) getActivity();
        }
        catch (ClassCastException e)
        {
            Log.i("Error",e.toString());

            throw new ClassCastException("Error in Data trasmission between fragments");
        }

    }
    @Override
    public void onResume() {
        super.onResume();

    }
*/


}
