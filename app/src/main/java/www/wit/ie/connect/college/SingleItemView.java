package www.wit.ie.connect.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.games.GamesMetadata;
import com.parse.ParseObject;
import com.parse.ParseUser;

import www.wit.ie.connect.R;

public class SingleItemView extends AppCompatActivity {

    private TextView txtname, txtType, title, worth, duedate, details ;
    private String _name, _type, _title, _worth, _duedate, _details;

    private Button _saveChanges;

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


        final ParseObject currentUser = new ParseObject("connect");
        _saveChanges = (Button) findViewById(R.id.save_changes);
//        String subject = currentUser.getString("suject");


//        txtname.setText(subject);


        _saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser.put("subject",txtname.getText().toString());
                currentUser.put("type", txtType.getText().toString());
                currentUser.put("title", title.getText().toString());
                currentUser.put("worth", worth.getText().toString());
                currentUser.put("duedate", duedate.getText().toString());
                currentUser.put("details", details.getText().toString());

                currentUser.saveInBackground();
                Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

            }

        });


    }

}
