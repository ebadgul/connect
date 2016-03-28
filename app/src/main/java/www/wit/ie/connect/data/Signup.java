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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
    private EditText email;
    private EditText password;

    private String _username;
    private String _password;
    private String _email;
    private Button signupBtn;

    private Intent intent;
    private Intent serviceIntent;


    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;

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


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);



        username = (EditText) findViewById(R.id.sgup_name);
        password = (EditText) findViewById(R.id.sgup_password);
        email = (EditText) findViewById(R.id.sgup_email);
        signupBtn = (Button) findViewById(R.id.btn_signup);


        username.addTextChangedListener(new MyTextWatcher(username));
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _username = username.getText().toString();
                _password = password.getText().toString();
                _email = email.getText().toString();

                submitForm();

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
    public void linkToSignin(View view){
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
    }

    public void onDestroy(){
        stopService(new Intent(this, MessageService.class));
        super.onDestroy();
    }
//*******************************************************************************
private void submitForm() {
    if (!validateName()) {
        return;
    }

    if (!validateEmail()) {
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

    private boolean validateEmail() {
        String emaill = email.getText().toString().trim();

        if (emaill.isEmpty() || !isValidEmail(emaill)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
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

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                case R.id.sgup_email:
                    validateEmail();
                    break;
                case R.id.sgup_password:
                    validatePassword();
                    break;
            }
        }
    }

//*******************************************************************************
}
