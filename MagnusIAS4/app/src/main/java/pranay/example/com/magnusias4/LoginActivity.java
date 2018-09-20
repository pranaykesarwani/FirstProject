package pranay.example.com.magnusias4;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View view;

        view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.fragment_login,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true)
                .setTitle("Aunthentication")
                .setView(view)
                .show();
    }
}
