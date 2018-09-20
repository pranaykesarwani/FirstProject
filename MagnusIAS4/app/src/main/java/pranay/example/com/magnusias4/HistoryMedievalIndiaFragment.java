package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HistoryMedievalIndiaFragment extends Fragment {
    LinearLayout parentLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view;
       // for (int i=0;i<5;i++) {


        parentLayout.addView(parentLayout);
            view = layoutInflater.inflate(R.layout.fragment_history_medieval_india, parentLayout, false);
            
        //}

        return view;

    //    return inflater.inflate(R.layout.fragment_history_medieval_india, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         parentLayout = (LinearLayout)view.findViewById(R.id.parent);


    }
}
