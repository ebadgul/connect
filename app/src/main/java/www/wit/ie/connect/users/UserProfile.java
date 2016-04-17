package www.wit.ie.connect.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import www.wit.ie.connect.R;
import www.wit.ie.connect.college.Timetable;
import www.wit.ie.connect.college.UniActivities;
import www.wit.ie.connect.data.Login;
import www.wit.ie.connect.data.Signup;

public class UserProfile extends AppCompatActivity {


    private TextView pname;
    private Button _delete;


    //***************************************
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    //***************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.uploadPhoto);
//        ivImage = (ImageView) findViewById(R.id.uploadProfilePhoto);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
//        ivImage = (ImageView) findViewById(R.id.uploadProfilePhoto);




        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("profielPhoto");
        query.whereEqualTo("belongsToo", ParseUser.getCurrentUser());
        query.orderByDescending("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {

                ParseFile file = (ParseFile) parseObject.get("ImageFile");
                file.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {

                        if (e == null){
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ivImage = (ImageView) findViewById(R.id.uploadProfilePhoto);
                            ivImage.setImageBitmap(bmp);
                        }else {
                            Log.v("Testing", "there was a problem");
                        }
                    }
                });
            }
        });









        pname= (TextView) findViewById(R.id.pName);
        _delete = (Button) findViewById(R.id.deletePBtn);


        final ParseUser currentUser =  ParseUser.getCurrentUser();
        String username = currentUser.getUsername().toString();
        pname.setText(username);


        _delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Context context;
                new AlertDialog.Builder(UserProfile.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete your profile?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                currentUser.deleteInBackground();
                                currentUser.saveInBackground();
                                ParseUser.logOut();
                                startActivity(new Intent(UserProfile.this, Signup.class));
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_timetable) {
            startActivity(new Intent(this, Timetable.class));
        }else if (id == R.id.action_settings){
            startActivity(new Intent(this, UniActivities.class));
        }else if (id ==  R.id.user_profile){
            startActivity(new Intent(this, UserProfile.class));
        }*/
        if (id == R.id.action_logout){
            ParseUser.logOut();
            startActivity(new Intent(this, Login.class));
//            Intent intent = new Intent(this, Login.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }



//imiage view ******************************


    public void uploadit(){


        Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("profileImage.png", image);
        // Upload the image into Parse Cloud
        file.saveInBackground();

        // Create a New Class called "profielPhoto" in Parse
        ParseObject imgupload = new ParseObject("profielPhoto");

        // Create a column named "ImageName" and set the string
        imgupload.put("ImageName", "timetable");
        imgupload.put("belongsToo", ParseUser.getCurrentUser());

        // Create a column named "ImageFile" and insert the image
        imgupload.put("ImageFile", file);

        // Create the class and the columns
        imgupload.saveInBackground();

        // Show a simple toast message
        Toast.makeText(UserProfile.this, "image Saved",
                Toast.LENGTH_SHORT).show();


    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
        uploadit();
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);
    }
}

/*
references
http://www.androidbegin.com/tutorial/android-parse-com-image-upload-tutorial/
http://www.androidbegin.com/tutorial/android-parse-com-image-download-tutorial/
http://stackoverflow.com/questions/6650398/android-imageview-zoom-in-and-zoom-out
 */