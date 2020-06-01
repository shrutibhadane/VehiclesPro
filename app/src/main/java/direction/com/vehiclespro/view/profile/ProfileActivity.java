package direction.com.vehiclespro.view.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import direction.com.vehiclespro.R;
import direction.com.vehiclespro.database.DatabaseHelper;
import direction.com.vehiclespro.utility.Utility;
import direction.com.vehiclespro.utility.Validations;
import direction.com.vehiclespro.view.history.HistoryActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button btnHistoryProfile;
    private Button btnUpdateProfile;
    private Button btnSaveProfile;
    private EditText etVehicleNo;
    private EditText etMake;
    private EditText etModel;
    private EditText etVariant;
    private Spinner spnrFuelType;
    private TextView tvVehicleNo;
    private TextView tvMake;
    private TextView tvModel;
    private TextView tvVariant;
    private TextView tvFuelType;
    private TextView tvSelectProfile;
    private ImageView imgPic;
    private View viewProfile;

    String dateTime, name, vehicleNo, make, model, variant, fuelType;
    String updatedVehicleNo, updatedMake, updatedModel, updatedVariant, updatedFuelType;

    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();

        clickListeners();

        //initializing database helper class object
        database = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        vehicleNo = intent.getStringExtra("vehicleNo");
        make = intent.getStringExtra("make");
        model = intent.getStringExtra("model");
        variant = intent.getStringExtra("variant");
        fuelType = intent.getStringExtra("fuelType");

        setData();

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Fuel Type");
        categories.add("Petrol");
        categories.add("Diesel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spnrFuelType.setAdapter(dataAdapter);

        if (fuelType != null) {
            int spinnerPosition = dataAdapter.getPosition(fuelType);
            spnrFuelType.setSelection(spinnerPosition);
        }
    }

    private void initView() {
        btnHistoryProfile = findViewById(R.id.btn_history_profile);
        btnUpdateProfile = findViewById(R.id.btn_update_profile);
        btnSaveProfile = findViewById(R.id.btn_save_profile);
        etVehicleNo = findViewById(R.id.et_vehicle_no_profile);
        etMake = findViewById(R.id.et_make_profile);
        etModel = findViewById(R.id.et_model_profile);
        etVariant = findViewById(R.id.et_variant_profile);
        spnrFuelType = findViewById(R.id.spnr_fuel_type_profile);
        tvVehicleNo = findViewById(R.id.tv_vehicle_no_data_profile);
        tvMake = findViewById(R.id.tv_make_data_profile);
        tvModel = findViewById(R.id.tv_model_data_profile);
        tvVariant = findViewById(R.id.tv_variant_data_profile);
        tvFuelType = findViewById(R.id.tv_fuel_type_data_profile);
        imgPic = findViewById(R.id.img_photo_profile);
        tvSelectProfile = findViewById(R.id.tv_select_profile);
        viewProfile = findViewById(R.id.view_profile);
    }

    private void clickListeners() {
        btnHistoryProfile.setOnClickListener(this);
        btnUpdateProfile.setOnClickListener(this);
        btnSaveProfile.setOnClickListener(this);
        spnrFuelType.setOnItemSelectedListener(this);
    }

    private void setData() {
        tvVehicleNo.setText(vehicleNo);
        tvMake.setText(make);
        tvModel.setText(model);
        tvVariant.setText(variant);
        tvFuelType.setText(fuelType);
        etVehicleNo.setText(vehicleNo);
        etMake.setText(make);
        etModel.setText(model);
        etVariant.setText(variant);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_update_profile:
                ableDisableUpdate();
                break;

            case R.id.btn_save_profile:
                ableDisableSave();

                if (validateDetails()) {
                    updatedVehicleNo = etVehicleNo.getText().toString();
                    updatedMake = etMake.getText().toString();
                    updatedModel = etModel.getText().toString();
                    updatedVariant = etVariant.getText().toString();

                    tvVehicleNo.setText(updatedVehicleNo);
                    tvMake.setText(updatedMake);
                    tvModel.setText(updatedModel);
                    tvVariant.setText(updatedVariant);
                    tvFuelType.setText(updatedFuelType);
                    etVehicleNo.setText(updatedVehicleNo);
                    etMake.setText(updatedMake);
                    etModel.setText(updatedModel);
                    etVariant.setText(updatedVariant);

                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm a");
                    Date date = new Date();
                    dateTime = dateFormat.format(date);

                    // Insert data into database
                    database.updateValue(dateTime, name, updatedVehicleNo, updatedMake,
                            updatedModel, updatedVariant,
                            updatedFuelType);
                }
                break;

            case R.id.btn_history_profile:
                Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void ableDisableUpdate() {

        etVehicleNo.setVisibility(View.VISIBLE);
        etMake.setVisibility(View.VISIBLE);
        etModel.setVisibility(View.VISIBLE);
        etVariant.setVisibility(View.VISIBLE);
        spnrFuelType.setVisibility(View.VISIBLE);
        tvSelectProfile.setVisibility(View.VISIBLE);
        viewProfile.setVisibility(View.VISIBLE);
        btnSaveProfile.setVisibility(View.VISIBLE);
        btnHistoryProfile.setVisibility(View.GONE);
        btnUpdateProfile.setVisibility(View.GONE);
        tvVehicleNo.setVisibility(View.GONE);
        tvMake.setVisibility(View.GONE);
        tvModel.setVisibility(View.GONE);
        tvVariant.setVisibility(View.GONE);
        tvFuelType.setVisibility(View.GONE);
    }

    private void ableDisableSave() {

        etVehicleNo.setVisibility(View.GONE);
        etMake.setVisibility(View.GONE);
        etModel.setVisibility(View.GONE);
        etVariant.setVisibility(View.GONE);
        spnrFuelType.setVisibility(View.GONE);
        tvSelectProfile.setVisibility(View.GONE);
        viewProfile.setVisibility(View.GONE);
        btnSaveProfile.setVisibility(View.GONE);
        btnHistoryProfile.setVisibility(View.VISIBLE);
        btnUpdateProfile.setVisibility(View.VISIBLE);
        tvVehicleNo.setVisibility(View.VISIBLE);
        tvMake.setVisibility(View.VISIBLE);
        tvModel.setVisibility(View.VISIBLE);
        tvVariant.setVisibility(View.VISIBLE);
        tvFuelType.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updatedFuelType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
     * validate all necessary fields
     * */
    private boolean validateDetails() {
        if (etVehicleNo.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.profileActivity), getString(R.string.enter_vehicle_number));
        } else if (!Validations.isValidVehicleNo(etVehicleNo.getText().toString())) {
            Utility.errorSnackBar(findViewById(R.id.profileActivity), getString(R.string.enter_valid_vehicle_number));
        } else if (etMake.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.profileActivity), getString(R.string.enter_make));
        } else if (etModel.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.profileActivity), getString(R.string.enter_model));
        } else if (etVariant.getText().toString().equals("")) {
            Utility.errorSnackBar(findViewById(R.id.profileActivity), getString(R.string.enter_variant));
        } else if (!updatedFuelType.toString().equals("Select Fuel Type")) {
            return true;
        } else {
            Utility.errorSnackBar(findViewById(R.id.profileActivity), getString(R.string.enter_fuel_type));
        }
        return false;
    }
}