package direction.com.vehiclespro.view.details;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import direction.com.vehiclespro.R;
import direction.com.vehiclespro.database.DatabaseHelper;
import direction.com.vehiclespro.utility.Utility;
import direction.com.vehiclespro.utility.Validations;
import direction.com.vehiclespro.view.history.HistoryActivity;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemSelectedListener {

    private Button btnSaveDetails;
    private EditText etVehicleNo;
    private EditText etMake;
    private EditText etModel;
    private EditText etVariant;
    private EditText etFuelType;
    private TextView tvSelect;
    private ImageView imgPic;
    private Spinner spnrFuelType;

    DatabaseHelper database;
    String dateTime, name, selectedFuelType;

    private static final String TAG = "DetailsActivity";

    private static final int REQUEST_WRITE_PERMISSION = 789;
    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //get intent data
        Intent intent = getIntent();
        dateTime = intent.getStringExtra("dateTime");
        name = intent.getStringExtra("name");

        initView();

        clickListeners();

        //initializing database helper class object
        database = new DatabaseHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Fuel Type");
        categories.add("Petrol");
        categories.add("Diesel");
        categories.add("Kerosene");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnrFuelType.setAdapter(dataAdapter);

    }

    private void initView() {
        btnSaveDetails = findViewById(R.id.btn_save_details);
        etVehicleNo = findViewById(R.id.et_vehicle_no_details);
        etMake = findViewById(R.id.et_make_details);
        etModel = findViewById(R.id.et_model_details);
        etVariant = findViewById(R.id.et_variant_details);
        etFuelType = findViewById(R.id.et_fuel_type_details);
        tvSelect = findViewById(R.id.tv_select);
        imgPic = findViewById(R.id.img_photo_details);
        spnrFuelType = findViewById(R.id.spnr_fuel_type_details);
    }

    private void clickListeners() {
        btnSaveDetails.setOnClickListener(this);
        tvSelect.setOnClickListener(this);
        spnrFuelType.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_details:
                if (validateDetails()) {

                    FileInputStream fis = null;
                    byte[] image = new byte[0];
                    try {
                        fis = new FileInputStream(String.valueOf(selectedImageUri));
                        image = new byte[fis.available()];
                        fis.read(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("IMAGE_DATA", String.valueOf(image));
                    // Insert data into database
                    database.insertValue(dateTime, name, etVehicleNo.getText().toString(), etMake.getText().toString(),
                            etModel.getText().toString(), etVariant.getText().toString(),
                            selectedFuelType );

                    Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.tv_select:
                selectImage();
                break;
        }
    }

    /*
     * dialog for image capture or galary selection
     * */
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (!checkPermission()) {
                        requestPermission();
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /*
     * check camera permission
     * */
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DetailsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * request permission for camera
     * */
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(DetailsActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }

    /*
     * write request permission for capturing image
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }


    /*
     * onActivityResult for select image and capture image
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : Objects.requireNonNull(f.listFiles())) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    imgPic.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imgPic.setImageURI(selectedImageUri);

                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /*
     * validate all necessary fields
     * */
    private boolean validateDetails() {
        if (etVehicleNo.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.detailsActivity), getString(R.string.enter_vehicle_number));
        } else if (Validations.isValidVehicleNo(etVehicleNo.getText().toString())) {
            Utility.errorSnackBar(findViewById(R.id.detailsActivity), getString(R.string.enter_valid_vehicle_number));
        } else if (etMake.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.detailsActivity), getString(R.string.enter_make));
        } else if (etModel.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.detailsActivity), getString(R.string.enter_model));
        } else if (etVariant.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.detailsActivity), getString(R.string.enter_variant));
        } else if (!selectedFuelType.toString().equals("Select Fuel Type")) {
            return true;
        } else {
            Utility.errorSnackBar(findViewById(R.id.detailsActivity), getString(R.string.enter_fuel_type));
        }
        return false;
    }

    /*
     * spinner item selected
     * */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        selectedFuelType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}