package direction.com.vehiclespro.view.history;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import direction.com.vehiclespro.R;
import direction.com.vehiclespro.database.DatabaseHelper;
import direction.com.vehiclespro.model.DataModel;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvNoData;
    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;
    LinearLayoutManager mLayoutManager;
    private List<DataModel> dataModelList;

    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        database = new DatabaseHelper(getApplicationContext());
        dataModelList = new ArrayList<>();
        dataModelList = database.getData();

        //initialization of view
        initView();

        //recyclerview setup
        setUp();
    }

    /*
     * initialization of views
     * using findviewById method
     * */
    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_history);
        tvNoData = findViewById(R.id.tv_no_data_history);
    }

    /*
     * setting data to recyclerview adapter
     * */
    private void setUp() {
        if (!dataModelList.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);

            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            adapter = new HistoryAdapter(this, dataModelList);
            mRecyclerView.setAdapter(adapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }
}