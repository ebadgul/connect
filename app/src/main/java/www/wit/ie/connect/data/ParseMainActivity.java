/*
package www.wit.ie.connect.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import www.wit.ie.connect.MainActivity;

*/
/**
 * Created by ebad on 27/01/16.
 *//*

public class ParseMainActivity extends Activity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

            //Determine whether the current user is an anonymous user...
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
            //if user is anonymous, send the user to Login activity...
            Intent intent = new Intent(ParseMainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null){
                //send logged in user to MainActivity....
                Intent intent = new Intent(ParseMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                //else send the user to signup activity....
                startActivity(new Intent(ParseMainActivity.this, Signup.class));
                finish();
            }
        }

    }
}
*/
