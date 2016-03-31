package www.wit.ie.connect.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import www.wit.ie.connect.R;

public class UniActivities extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText subject, title, worth;
    private String _subject, _title, _worth;
    private Button _saveBtn;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.activityType);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Project");
        categories.add("Exam");
        categories.add("Presentation");
        categories.add("Essay");
        categories.add("Event");
        categories.add("Other");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        subject = (EditText) findViewById(R.id.subject_input);
        title = (EditText) findViewById(R.id.title_input);
        worth = (EditText) findViewById(R.id.worth_input);
        _saveBtn = (Button) findViewById(R.id.save_input);

        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _subject = subject.getText().toString();
                _title = title.getText().toString();
                _worth = worth.getText().toString();

                if (_subject.equals("") || _title.equals("") || _worth.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                } else {

                    ParseObject dataObject = new ParseObject("connect");
                    dataObject.put("subject", _subject);
                    dataObject.put("type", item);
                    dataObject.put("title", _title);
                    dataObject.put("worth", _worth);
                    dataObject.saveInBackground();


                }
                startActivity(new Intent(UniActivities.this, Projects.class));
            }
        });



    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
         item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub



//        List<ParseObject> po =  listView = (ListView) listView.findViewById(R.id.listview);
//        ArrayAdapter<String> adapter = adapter = new ArrayAdapter<String>(UniActivities.this, R.layout.listview_item);



    }



   /* public void onSave(View view){
        startActivity(new Intent(UniActivities.this, Project.class));
    }*/


}
