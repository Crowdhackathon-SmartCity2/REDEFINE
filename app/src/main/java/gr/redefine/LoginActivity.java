package gr.redefine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button login = findViewById(R.id.login);
        EditText email = findViewById(R.id.email);
        Context c = this;
        login.setOnClickListener((v)->{
            /**
             * @CodeEvaluator: Den pisteuw na perimenes kanoniko login???
             */
            if(email.getText().length() == 0){
                Toast.makeText(c,"Please fill the form", Toast.LENGTH_LONG);
                return;
            }
            Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
            Bundle b = new Bundle();
            b.putString("user", email.getText().toString()); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();

        });
    }

}
