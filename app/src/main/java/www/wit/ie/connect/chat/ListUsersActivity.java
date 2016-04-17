package www.wit.ie.connect.chat;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import www.wit.ie.connect.R;

public class ListUsersActivity extends Fragment {


    private String currentUserId;
    private ArrayAdapter<String> namesArrayAdapter;
    private ArrayList<String> names;
    private ListView usersListView;
    private Button logoutButton;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_users);
        View v = inflater.inflate(R.layout.activity_list_users, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        showSpinner();
        return v;
    }

    private void setConversationsList() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        names = new ArrayList<String>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", currentUserId);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> userList, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        names.add(userList.get(i).getUsername().toString());
                    }
                    usersListView = (ListView) getView().findViewById(R.id.usersListView);
                    namesArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.user_list_item, names);
                    usersListView.setAdapter(namesArrayAdapter);

                    usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                            openConversation(names, i);
                        }
                    });

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Error loading user list", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openConversation(ArrayList<String> names, int pos) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", names.get(pos));
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> user, ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MessagingActivity.class);
                    intent.putExtra("RECIPIENT_ID", user.get(0).getObjectId());
                    startActivity(intent);
                } else {

                    Toast.makeText(getActivity().getApplicationContext(), "Error finding that user", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showSpinner() {
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.hide();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
                progressDialog.dismiss();
                if (!success) {
                    Toast.makeText(getActivity().getApplicationContext(), "Messaging service failed start", Toast.LENGTH_LONG).show();
                }
            }
        };
        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(receiver, new IntentFilter("www.wit.ie.connect.chat.ListUsersActivity"));
    }

    public void onStart(){
        setConversationsList();
        super.onStart();
    }

    public void onResume() {
        setConversationsList();
        super.onResume();
    }
}

/*
reference
https://www.sinch.com/tutorials/android-messaging-tutorial-using-sinch-and-parse/
 */