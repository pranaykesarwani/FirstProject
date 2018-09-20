package pranay.example.com.magnusias4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<String> {
    Context context;
    String [] players;
   // int [] images;
    LayoutInflater inflater;
    public Adapter(Context context1,String[] players1){
        super(context1,R.layout.data_items,players1);

        this.context = context1;
        this.players = players1;
       // this.images = images;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_items,null,false);

        }

        TextView subjectName = (TextView) convertView.findViewById(R.id.subject_name);
        TextView subjectDetails = (TextView)convertView.findViewById(R.id.subject_details);
        subjectName.setText(players[position]);
        subjectDetails.setText(players[position]);

        return convertView;
    }
}
