package www.wit.ie.connect.college;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import www.wit.ie.connect.R;

public class Timetable extends AppCompatActivity implements OnTouchListener {
    private Button button;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Button btnSelect;
    ImageView ivImage;

    ZoomControls zoom;

    //    ***************************************
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
//    *********************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* btnSelect = (Button) findViewById(R.id.action_timetable);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/

        //******************************************************************************************


        ParseUser cu = ParseUser.getCurrentUser();
        ParseObject po = new ParseObject("ImageUpload");
        final String obj = cu.getObjectId();
        Log.v("obj1", "" + po.getObjectId());
        Log.v("obj2", "" + obj);
        Log.v("obj3", "" + cu.getObjectId());
        Log.v("obj4", "" + cu.getParseObject("ImageUpload"));
        ParseObject gameScore = new ParseObject("ImageUpload");
        String objectId = gameScore.getObjectId();
        Log.v("obj5", "" + objectId);


        // Locate the class table named "ImageUpload" in Parse.com
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ImageUpload");
        query.whereEqualTo("belongsTo", ParseUser.getCurrentUser());
        query.orderByDescending("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

//                if (e == null)

                if (object ==  null){
                    Toast.makeText(getApplicationContext(), "Please upload your timetable here", Toast.LENGTH_LONG).show();
                }else {

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
                                ImageView image = (ImageView) findViewById(R.id.ivImage);

                                // Set the Bitmap into the
                                // ImageView
                                image.setImageBitmap(bmp);


                            } else {
                                Log.d("test",
                                        "There was a problem downloading the data.");
                            }
                        }
                    });

                }
            }
        });
      /*  query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
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
                            ImageView image = (ImageView) findViewById(R.id.ivImage);

                            // Set the Bitmap into the
                            // ImageView
                            image.setImageBitmap(bmp);


                        } else {
                            Log.d("test",
                                    "There was a problem downloading the data.");
                        }
                    }
                });
            }
        });*/


        //******************************************************************************************
        ivImage = (ImageView) findViewById(R.id.ivImage);

        zoom = (ZoomControls) findViewById(R.id.zoomControls);
        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                float x = ivImage.getScaleX();
                float y = ivImage.getScaleY();

                ivImage.setScaleX((float) (x + 1));
                ivImage.setScaleY((float) (y + 1));
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                float x = ivImage.getScaleX();
                float y = ivImage.getScaleY();

                ivImage.setScaleX((float) (x - 1));
                ivImage.setScaleY((float) (y - 1));
            }
        });

//      *********************************************************************
        ivImage.setOnTouchListener(this);
//      *********************************************************************

//        button = (Button) findViewById(R.id.uploadBtn);
       /* ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Locate the image in res > drawable-hdpi
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.saveit);
//                Bitmap shithead = BitmapFactory.;

//                TextView textView=(TextView)view.findViewById(R.id.textView);
//                ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
//                String textViewString=textView.getText().toString();

                Bitmap bitmap = ((BitmapDrawable)ivImage.getDrawable()).getBitmap();
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("Timetable.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();

                // Create a New Class called "ImageUpload" in Parse
                ParseObject imgupload = new ParseObject("ImageUpload");

                // Create a column named "ImageName" and set the string
                imgupload.put("ImageName", "timetable");

                // Create a column named "ImageFile" and insert the image
                imgupload.put("ImageFile", file);

                // Create the class and the columns
                imgupload.saveInBackground();

                // Show a simple toast message
                Toast.makeText(Timetable.this, "Timetable Uploaded",
                        Toast.LENGTH_SHORT).show();


            }
        });*/
    }
//      *********************************************************************
//      *********************************************************************

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Timetable.this);
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

//    nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_upload_timetable) {
            selectImage();
        } else if (id == R.id.action_save_timetable) {

            Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();

//            if (bitmap == null) {
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("Timetable.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();
                // Create a New Class called "ImageUpload" in Parse
                ParseObject imgupload = new ParseObject("ImageUpload");
                // Create a column named "ImageName" and set the string
                imgupload.put("ImageName", "timetable");
                //get the current user and assign the timetable to it.
                imgupload.put("belongsTo", ParseUser.getCurrentUser());

                // Create a column named "ImageFile" and insert the image
                imgupload.put("ImageFile", file);

                // Create the class and the columns
                imgupload.saveInBackground();
                // Show a simple toast message
                Toast.makeText(Timetable.this, "Timetable Saved",
                        Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(getApplicationContext(), "Sorry upload timetable", Toast.LENGTH_SHORT).show();
//            }


        }


        return super.onOptionsItemSelected(item);
    }

//    ****************************************************************************************


    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Show an event in the LogCat view, for debugging
     */
    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }


//    ****************************************************************************************
}
/*
references
http://www.androidbegin.com/tutorial/android-parse-com-image-upload-tutorial/
http://www.androidbegin.com/tutorial/android-parse-com-image-download-tutorial/
http://stackoverflow.com/questions/6650398/android-imageview-zoom-in-and-zoom-out
 */