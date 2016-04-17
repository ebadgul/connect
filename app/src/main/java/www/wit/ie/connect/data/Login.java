package www.wit.ie.connect.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import www.wit.ie.connect.MainActivity;
import www.wit.ie.connect.R;
import www.wit.ie.connect.chat.MessageService;

public class Login extends AppCompatActivity {

    protected EditText username;
    protected EditText password;
//    protected Button loginBtn;

    protected String _username;
    protected String _password;
    protected Button _loginBtn;

    private Intent intent;
    private Intent serviceIntent;

    private TextInputLayout inputLayoutName, inputLayoutPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this);

        intent = new Intent(getApplicationContext(), MainActivity.class);
        serviceIntent = new Intent(getApplicationContext(), MessageService.class);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            startActivity(intent);
            startService(serviceIntent);
        }


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);


        username = (EditText) findViewById(R.id.lg_name);
        password = (EditText) findViewById(R.id.lg_password);
        _loginBtn = (Button) findViewById(R.id.btn_login);

        username.addTextChangedListener(new MyTextWatcher(username));
        password.addTextChangedListener(new MyTextWatcher(password));

        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _username = username.getText().toString();
                _password = password.getText().toString();

                submitForm();

                ParseUser.logInInBackground(_username, _password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {

                        if(parseUser != null){
                            startActivity(intent);
                            startService(serviceIntent);

                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in",
                                    Toast.LENGTH_SHORT).show();
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

    public void onDestroy(){
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }

    public void linkToSignup(View view){
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
    }

    //*******************************************************************************
    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

//    Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (username.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(username);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(password);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.sgup_name:
                    validateName();
                    break;
                case R.id.sgup_password:
                    validatePassword();
                    break;
            }
        }
    }

//*******************************************************************************

}
/*
reference
 http://www.androidbegin.com/tutorial/android-parse-com-simple-login-and-signup-tutorial/
 */