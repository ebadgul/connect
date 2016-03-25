package www.wit.ie.connect.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import www.wit.ie.connect.MainActivity;
import www.wit.ie.connect.R;
import www.wit.ie.connect.chat.MessageService;

public class Signup extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;

    private String _username;
    private String _password;
    private String _email;
    private Button signupBtn;

    private Intent intent;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        intent = new Intent(getApplicationContext(), MainActivity.class);
        serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            startActivity(intent);
            startService(serviceIntent);
        }



        username = (EditText) findViewById(R.id.sgup_name);
        password = (EditText) findViewById(R.id.sgup_password);
        email = (EditText) findViewById(R.id.sgup_email);
        signupBtn = (Button) findViewById(R.id.btn_signup);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _username = username.getText().toString();
                _password = password.getText().toString();
                _email = email.getText().toString();

                if (_username.equals("") && _password.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(_username);
                    user.setPassword(_password);
                    user.setEmail(_email);

                    Log.v("namee", "" + _username);
                    Log.v("passwordd", "" + _password);
                    Log.v("Emaillllll", "" + _email);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                startActivity(intent);
                                startService(serviceIntent);

                                Toast.makeText(getApplicationContext(), "You'r Signed up", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(Signup.this, MainActivity.class));
//                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });



    }
    public void onDestroy(){
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }

    /*public void linkToSignin(View view){
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
    }*/

}
