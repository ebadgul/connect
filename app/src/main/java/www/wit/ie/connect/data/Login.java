package www.wit.ie.connect.data;

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

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import www.wit.ie.connect.MainActivity;
import www.wit.ie.connect.R;

public class Login extends AppCompatActivity {

    protected EditText username;
    protected EditText password;
//    protected Button loginBtn;

    protected String _username;
    protected String _password;
    protected Button _loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        username = (EditText) findViewById(R.id.lg_name);
        password = (EditText) findViewById(R.id.lg_password);
        _loginBtn = (Button) findViewById(R.id.btn_login);

        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _username = username.getText().toString();
                _password = password.getText().toString();

                ParseUser.logInInBackground(_username, _password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {

                        if(parseUser != null){
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "No such user exist, please Signup",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });




    }

    public void linkToSignup(View view){
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
    }

}
