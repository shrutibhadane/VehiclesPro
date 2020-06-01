package direction.com.vehiclespro.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import direction.com.vehiclespro.R;
import direction.com.vehiclespro.utility.Utility;
import direction.com.vehiclespro.view.details.DetailsActivity;
import direction.com.vehiclespro.view.history.HistoryActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edNameDashboard;
    private Button btnNextDashboard;
    private Button btnHistoryDashboard;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization of view
        initView();

        //click listerners
        clickListeners();
    }

    /*
     * initialization of views
     * */
    private void initView() {
        edNameDashboard = findViewById(R.id.et_name_dashboard);
        btnNextDashboard = findViewById(R.id.btn_next_dashboard);
        btnHistoryDashboard = findViewById(R.id.btn_history_dashboard);
    }

    /*
     * set click listerners
     * */
    private void clickListeners() {
        btnNextDashboard.setOnClickListener(this);
        btnNextDashboard.setOnClickListener(this);
        btnHistoryDashboard.setOnClickListener(this);
    }

    /*
     * overridden method for clicks
     * */
    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btn_next_dashboard:

                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm a");
                Date date = new Date();

                if (!edNameDashboard.getText().toString().isEmpty()) {
                    Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                    i.putExtra("datetime", dateFormat.format(date));
                    i.putExtra("name", edNameDashboard.getText().toString());
                    startActivity(i);
                    finish();
                } else {
                    Utility.errorSnackBar(findViewById(R.id.mainActivity),getString(R.string.enter_your_name));
                }
                break;

            case R.id.btn_history_dashboard:
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
                //finish();
                break;
        }
    }

    //back pressed Method
    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.click_back_again, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }


}