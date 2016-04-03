package www.wit.ie.connect.users;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import org.w3c.dom.Text;

import www.wit.ie.connect.R;
import www.wit.ie.connect.college.Timetable;
import www.wit.ie.connect.college.UniActivities;
import www.wit.ie.connect.data.Login;
import www.wit.ie.connect.data.Signup;

public class UserProfile extends AppCompatActivity {


    private TextView pname;
    private Button _delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        pname= (TextView) findViewById(R.id.pName);
        _delete = (Button) findViewById(R.id.deletePBtn);


        final ParseUser currentUser =  ParseUser.getCurrentUser();
        String username = currentUser.getUsername().toString();
        pname.setText(username);


        _delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Context context;
                new AlertDialog.Builder(UserProfile.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete your profile?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                currentUser.deleteInBackground();
                                currentUser.saveInBackground();
                                ParseUser.logOut();
                                startActivity(new Intent(UserProfile.this, Signup.class));
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_timetable) {
            startActivity(new Intent(this, Timetable.class));
        }else if (id == R.id.action_settings){
            startActivity(new Intent(this, UniActivities.class));
        }else if (id ==  R.id.user_profile){
            startActivity(new Intent(this, UserProfile.class));
        }*/
        if (id == R.id.action_logout){
            ParseUser.logOut();
            startActivity(new Intent(this, Login.class));
//            Intent intent = new Intent(this, Login.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}
