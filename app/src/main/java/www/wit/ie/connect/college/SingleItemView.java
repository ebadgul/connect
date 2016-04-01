package www.wit.ie.connect.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.games.GamesMetadata;

import www.wit.ie.connect.R;

public class SingleItemView extends AppCompatActivity {

    TextView txtname;
    String name;

    TextView txtType;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        name = intent.getStringExtra("subject");
        type = intent.getStringExtra("type");
        txtname = (TextView) findViewById(R.id.name);
        txtType = (TextView) findViewById(R.id.type);
        txtname.setText(name);
        txtType.setText(type);

        Log.v("nameee!!1", "" + txtname);


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
