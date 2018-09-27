package pranay.example.com.magnusias4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {
    Context cn;
    List<String> objects;
    DatabaseHelper databaseHelper;


    public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.objects=objects;
        cn=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) cn
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.sample_answer_fragment, parent, false);
        final TextView textView = (TextView) rowView.findViewById(R.id.serial_option);
        final RadioButton rbtn = (RadioButton) rowView.findViewById(R.id.optionA);
        final RadioButton rbtn2 = (RadioButton) rowView.findViewById(R.id.optionB);
        final RadioButton rbtn3 = (RadioButton) rowView.findViewById(R.id.optionC);
        final RadioButton rbtn4 = (RadioButton) rowView.findViewById(R.id.optionD);
        textView.setText(objects.get(position));

        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ExamActivity) cn).ob.remove(position+"##"+4);

                ((ExamActivity) cn).ob.remove(position+"##"+3);
                ((ExamActivity) cn).ob.remove(position+"##"+2);
                if(((ExamActivity)cn).ob.contains(position+"##"+1))
                {

                }
                else {
                    ((ExamActivity) cn).ob.add(position+"##"+1);
                }
             //   databaseHelper.saveAnswer(textView.getText().toString(),);

            Log.i("Result",textView.getText().toString()+" " + rbtn.getText());
            }
        });
        rbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ExamActivity) cn).ob.remove(position+"##"+4);

                ((ExamActivity) cn).ob.remove(position+"##"+3);
                ((ExamActivity) cn).ob.remove(position+"##"+1);
                if(((ExamActivity)cn).ob.contains(position+"##"+2))
                {

                }
                else {
                    ((ExamActivity) cn).ob.add(position+"##"+2);
                }
                Log.i("Result",textView.getText().toString()+" " + rbtn2.getText());
            }
        });
        rbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExamActivity) cn).ob.remove(position+"##"+4);

                ((ExamActivity) cn).ob.remove(position+"##"+1);
                ((ExamActivity) cn).ob.remove(position+"##"+2);
                if(((ExamActivity)cn).ob.contains(position+"##"+3))
                {

                }
                else {
                    ((ExamActivity) cn).ob.add(position+"##"+3);
                }
                Log.i("Result",textView.getText().toString()+" " + rbtn3.getText());
            }
        });

        rbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExamActivity) cn).ob.remove(position+"##"+1);
                ((ExamActivity) cn).ob.remove(position+"##"+2);
                ((ExamActivity) cn).ob.remove(position+"##"+3);
                if(((ExamActivity)cn).ob.contains(position+"##"+4))
                {

                }
                else {
                    ((ExamActivity) cn).ob.add(position+"##"+4);
                }
                Log.i("Result",textView.getText().toString()+" " + rbtn4.getText());
            }
        });

        if(((ExamActivity)cn).ob.contains(position+"##"+1)){
            Log.e("valuee","truee");
            rbtn.setChecked(true);
        }
        else
        {
            Log.e("valuee","false");
            rbtn.setChecked(false);
        }
        if(((ExamActivity)cn).ob.contains(position+"##"+2)){
            rbtn2.setChecked(true);
        }
        else
        {
            rbtn2.setChecked(false);
        }
        if(((ExamActivity)cn).ob.contains(position+"##"+3)){
            rbtn3.setChecked(true);
        }
        else
        {
            rbtn3.setChecked(false);
        }
        if(((ExamActivity)cn).ob.contains(position+"##"+4)){
            rbtn4.setChecked(true);
        }
        else
        {
            rbtn4.setChecked(false);
        }


        return rowView;
    }
}