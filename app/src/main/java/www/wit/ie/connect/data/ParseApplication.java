package www.wit.ie.connect.data;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;


public class ParseApplication extends Application {

    public void onCreate() {
        super.onCreate();

        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
//        ParseCrashReporting.enable(this);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

//        Parse.enableLocalDatastore(this);
//        Parse.initialize(this, "m8rw0GBo4Uc9vA3blEuhGHXjyD5VIBcdQJKSbKbt", "CmNQGyW1sxJvmPIVm3s12AxUFri7UAdO0QHZlFtm");


//        ParseInstallation.getCurrentInstallation().saveInBackground();

        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
//        Parse.enableLocalDatastore(this);
//
//        Parse.initialize(this);

    }
}
