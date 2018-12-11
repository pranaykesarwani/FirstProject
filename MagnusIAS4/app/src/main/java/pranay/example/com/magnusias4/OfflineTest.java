package pranay.example.com.magnusias4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class OfflineTest extends Fragment {

    ListView listView;
    String test_id,marked_answer;
    String q_no;
    String duration,subject;
    int hour,minute,second;
    int attempt_counter=0;
    int null_counter = 0;
    String question_no;
    String marks_correct_ans,negative_mark;
    TextView duration_text,test_name,rem_time;
    float deducted_marks,marks,total_marks=0;
    DatabaseHelper databaseHelper;
    ExamActivity.MyAdapter1 adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        view =  inflater.inflate(R.layout.activity_exam,container,false);


        return view;
    }
}
