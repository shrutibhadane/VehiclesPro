package direction.com.vehiclespro.view.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import direction.com.vehiclespro.R;
import direction.com.vehiclespro.model.DataModel;
import direction.com.vehiclespro.view.profile.ProfileActivity;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private List<DataModel> dataModelList;

    HistoryAdapter(Context context, List<DataModel> modelList) {
        this.context = context;
        this.dataModelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_history, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (!dataModelList.isEmpty()) {

            /*if (!dataModelList.get(position).getDateT().isEmpty()) {
                holder.tvDate.setText(" " + dataModelList.get(position).getDateT());
            } else {
                holder.tvDate.setText("No date");
            }*/

            if (!dataModelList.get(position).getName().isEmpty()) {
                holder.tvNameData.setText(" " + dataModelList.get(position).getName());
            } else {
                holder.tvNameData.setText("No name");
            }

            if (!dataModelList.get(position).getVehicleNo().isEmpty()) {
                holder.tvVehicleNoData.setText(" " + dataModelList.get(position).getVehicleNo());
            } else {
                holder.tvVehicleNoData.setText("No name");
            }

            if (!dataModelList.get(position).getMake().isEmpty()) {
                holder.tvMakeData.setText(" " + dataModelList.get(position).getMake());
            } else {
                holder.tvMakeData.setText("No name");
            }

            if (!dataModelList.get(position).getModel().isEmpty()) {
                holder.tvModelData.setText(" " + dataModelList.get(position).getModel());
            } else {
                holder.tvModelData.setText("No name");
            }

            if (!dataModelList.get(position).getVariant().isEmpty()) {
                holder.tvVariantData.setText(" " + dataModelList.get(position).getVariant());
            } else {
                holder.tvVariantData.setText("No name");
            }

            if (!dataModelList.get(position).getFuelType().isEmpty()) {
                holder.tvFuelTypeData.setText(" " + dataModelList.get(position).getFuelType());
            } else {
                holder.tvFuelTypeData.setText("No name");
            }

            holder.btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("id", dataModelList.get(position).getId());
                    intent.putExtra("name", dataModelList.get(position).getName());
                    intent.putExtra("vehicleNo", dataModelList.get(position).getVehicleNo());
                    intent.putExtra("make", dataModelList.get(position).getMake());
                    intent.putExtra("model", dataModelList.get(position).getModel());
                    intent.putExtra("variant", dataModelList.get(position).getVariant());
                    intent.putExtra("fuelType", dataModelList.get(position).getFuelType());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDate;
        private TextView tvNameData;
        private TextView tvVehicleNoData;
        private TextView tvMakeData;
        private TextView tvModelData;
        private TextView tvVariantData;
        private TextView tvFuelTypeData;
        private Button btnProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_date_data);
            tvNameData = itemView.findViewById(R.id.tv_name_data);
            tvVehicleNoData = itemView.findViewById(R.id.tv_vehicle_no_data);
            tvMakeData = itemView.findViewById(R.id.tv_make_data);
            tvModelData = itemView.findViewById(R.id.tv_model_data);
            tvVariantData = itemView.findViewById(R.id.tv_variant_data);
            tvFuelTypeData = itemView.findViewById(R.id.tv_fuel_type_data);
            btnProfile = itemView.findViewById(R.id.btn_profile);
        }
    }
}
