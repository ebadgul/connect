package www.wit.ie.connect.college;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import www.wit.ie.connect.R;

public class UniActivities extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private EditText subject, title, worth, details;
    private String _subject, _title, _worth, _date, _details;
    private Button _saveBtn;
    private String item;




    private EditText fromDateEtxt;
//    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
//    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // datePicker
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();


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
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        subject = (EditText) findViewById(R.id.subject_input);
        title = (EditText) findViewById(R.id.title_input);
        worth = (EditText) findViewById(R.id.worth_input);
        details = (EditText) findViewById(R.id.details);
        _saveBtn = (Button) findViewById(R.id.save_input);

        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _subject = subject.getText().toString();
                _title = title.getText().toString();
                _worth = worth.getText().toString();
                _date = fromDateEtxt.getText().toString();
                _details = details.getText().toString();

                if (_subject.equals("") || _title.equals("") || _worth.equals("") || _date.equals("") || _details.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                } else {

                    ParseObject dataObject = new ParseObject("connect");
                    dataObject.put("subject", _subject);
                    dataObject.put("type", item);
                    dataObject.put("title", _title);
                    dataObject.put("worth", _worth);
                    dataObject.put("duedate", _date);
                    dataObject.put("details", _details);
                    dataObject.put("createdBy", ParseUser.getCurrentUser());
                    dataObject.saveInBackground();

//                    ParseObject game = new ParseObject("Game");
//                    game.put("createdBy", ParseUser.getCurrentUser());

//                    ParseObject game = new ParseObject("Game");


                   /* NextFragment nextFrag= new NextFragment();
                    this.getFragmentManager().beginTransaction()
                            .replace(R.id.Layout_container, nextFrag,TAG_FRAGMENT)
                            .addToBackStack(null)
                            .commit();*/

                    Fragment pf = new Fragment();
//                    ProjectsFragment pf =  new ProjectsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_uni_activities, pf);
                    transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();

//                    this.getFragmentManager().



//                    startActivity(new Intent(UniActivities.this, Projects.class));
                    finish();

                }

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
    // datePicker start

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

    }
//*******************code from androidopentutorials start***********************
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }
//*******************code from androidopentutorials end***********************
    // datePicker end
}
/*
reference
http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event
 */