package www.wit.ie.connect.college;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import www.wit.ie.connect.R;

public class Testing extends AppCompatActivity {

    Button button;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });








        // Locate the button in main.xml
        button = (Button) findViewById(R.id.button);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                progressDialog = ProgressDialog.show(Testing.this, "",
                        "Downloading Image...", true);

                // Locate the class table named "ImageUpload" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ImageUpload");

                // Locate the objectId from the class
                query.getInBackground("UVPhdYUkpb",
                        new GetCallback<ParseObject>() {

                            public void done(ParseObject object, ParseException e) {
                                // TODO Auto-generated method stub

                                // Locate the column named "ImageName" and set
                                // the string
                                ParseFile fileObject = (ParseFile) object.get("ImageFile");
                                fileObject.getDataInBackground(new GetDataCallback() {

                                    public void done(byte[] data, ParseException e) {
                                        if (e == null) {
                                            Log.d("test", "We've got data in data.");
                                            // Decode the Byte[] into
                                            // Bitmap
                                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                                            // Get the ImageView from
                                            // main.xml
                                            ImageView image = (ImageView) findViewById(R.id.image);

                                            // Set the Bitmap into the
                                            // ImageView
                                            image.setImageBitmap(bmp);

                                            // Close progress dialog
                                            progressDialog.dismiss();

                                        } else {
                                            Log.d("test",
                                                    "There was a problem downloading the data.");
                                        }
                                    }
                                });
                            }
                        });
            }

        });





    }

}
