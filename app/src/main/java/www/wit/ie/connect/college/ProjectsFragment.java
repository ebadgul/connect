package www.wit.ie.connect.college;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import www.wit.ie.connect.R;

import static www.wit.ie.connect.college.Projects.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProjectsFragment extends Fragment {


    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;

    private View fragmentView;

//    OuterClass.InnerClass oi = new OuterClass().new InnerClass();
//    Projects.RemoteDataTask projects = new Projects().new RemoteDataTask();
//    Projects.RemoteDataTask projects = new Projects.RemoteDataTask();

    public ProjectsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//      return inflater.inflate(R.layout.fragment_projects, container, false);
        View v = inflater.inflate(R.layout.fragment_projects, container, false);

        new RemoteDataTask().execute();

//        ForecastFragment.FetchWeatherTask weatherTask = new ForecastFragment.FetchWeatherTask();
//        Inside.Deep id = i.new Deep();

//        new projects.RemoteDataTask().execute();
//        new Projects().new RemoteDataTask();

//        projects.onPostExecute();

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(getContext(), UniActivities.class));
            }
        });

        return v;

    }
       // RemoteDataTask AsyncTask
    public class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Parse.com Simple ListView Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the class table named "Country" in Parse.com
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "connect");
            query.orderByDescending("_created_at");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error!!!", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) getView().findViewById(R.id.listview);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.listview_item);
            // Retrieve object "name" from Parse.com database
            for (ParseObject connect : ob) {
                adapter.add((String) connect.get("subject"));
            }

            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Send single item click data to SingleItemView Class
                    Intent i = new Intent(getActivity(), SingleItemView.class);
                    // Pass data "name" followed by the position
                    i.putExtra("subject", ob.get(position).getString("subject").toString());
                    // Open SingleItemView.java Activity
                    startActivity(i);
                }
            });
        }
    }
}
