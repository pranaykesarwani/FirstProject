package pranay.example.com.magnusias4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    EditText username;
    EditText password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;

        view =  inflater.inflate(R.layout.fragment_login,container,false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true)
                .setTitle("Aunthentication")
                .setView(view)
        .show();
         username = (EditText)view.findViewById(R.id.username);
         password = (EditText)view.findViewById(R.id.password);
        Button login = (Button)view.findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getActivity(), username.getText().toString()+" "+ password.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}
