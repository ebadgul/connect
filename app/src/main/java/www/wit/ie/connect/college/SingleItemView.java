package www.wit.ie.connect.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.games.GamesMetadata;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import www.wit.ie.connect.R;
import www.wit.ie.connect.users.UserProfile;

public class SingleItemView extends AppCompatActivity {

    private TextView txtname, txtType, title, worth, duedate, details ;
    private String _name, _type, _title, _worth, _duedate, _details;

    private Button _saveChanges;
    final ParseObject currentUser = new ParseObject("connect");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtname = (TextView) findViewById(R.id.name_input_i);
        txtType = (TextView) findViewById(R.id.type_input_i);
        title = (TextView) findViewById(R.id.title_input_i);
        worth = (TextView) findViewById(R.id.worth_input_i);
        duedate = (TextView) findViewById(R.id.duedate_input_i);
        details = (TextView) findViewById(R.id.details_input_i);


        Intent intent = getIntent();
        _name = intent.getStringExtra("subject");
        _type = intent.getStringExtra("type");
        _title = intent.getStringExtra("title");
        _worth = intent.getStringExtra("worth");
        _duedate = intent.getStringExtra("duedate");
        _details = intent.getStringExtra("details");



        txtname.setText(_name);
        txtType.setText(_type);
        title.setText(_title);
        worth.setText(_worth);
        duedate.setText(_duedate);
        details.setText(_details);



        _saveChanges = (Button) findViewById(R.id.save_changes);
//        String subject = currentUser.getString("suject");


//        txtname.setText(subject);


        _saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser.put("subject", txtname.getText().toString());
                currentUser.put("type", txtType.getText().toString());
                currentUser.put("title", title.getText().toString());
                currentUser.put("worth", worth.getText().toString());
                currentUser.put("duedate", duedate.getText().toString());
                currentUser.put("details", details.getText().toString());

                currentUser.saveInBackground();
                Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

            }

        });


        /*Button _delete = (Button) findViewById(R.id.deleteBtnS);
        Log.v("delete1", ""+_delete);
        _delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          *//*      Log.v("delete2", "delete2");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("connect");
                query.whereEqualTo("subject","");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        for (ParseObject object: objects){
                            objects.deleteInBackground();

                            Log.v("delete3", "dddd");
                        }

                    }
                });*//*


            }
        });
*/


      /*  ParseQuery<ParseObject> query = ParseQuery.getQuery("your table name");
        query.whereEqualTo("table_coloumn_name", "");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (ParseObject object : objects) {
                    try {
                        object.delete();
                        object.saveInBackground();
                    } catch (ParseException exe) {
                        exe.printStackTrace();
                    }

                }
            }
        });

*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_one) {
//            startActivity(new Intent(SingleItemView.this, UniActivities.class));

//            ParseObject object = new ParseObject("connect");
//            final String objectId = object.getObjectId();


            final String shipp;
            Intent intent = getIntent();
            shipp = intent.getStringExtra("subject");


            ParseQuery<ParseObject> query = ParseQuery.getQuery("connect");
            query.whereEqualTo("subject", shipp);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Log.v("delete problem", "sdd" + e);
                        Log.v("object id", "sdd" +shipp);
//                        object.deleteInBackground();
//                        Log.v("detle obj", ""+object.getObjectId());
//                        object.deleteInBackground();
                    } else {
                        object.deleteInBackground();
                    }
                }
            });



/*            ParseQuery<ParseObject> query = ParseQuery.getQuery("connect");
            query.getInBackground("objectId", new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        // you can use your returned object here
                        object.deleteInBackground();
                    } else {
                        Log.v("shit happened", ""+e);
                        // something went wrong
                    }
                }
            });*/



         /*   query.whereEqualTo("User", ParseUser.getCurrentUser());
            query.getFirstInBackground(new FindCallBack() {

                @Override
                public void done(ParseObject object, com.parse.ParseException arg0) {
                    // TODO Auto-generated method stub
                    object.delete();
                    object.saveInBackground();
                }
            }););*/











        }
        return super.onOptionsItemSelected(item);
    }




}
