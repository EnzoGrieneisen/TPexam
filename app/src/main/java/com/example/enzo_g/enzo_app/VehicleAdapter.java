package com.example.enzo_g.enzo_app;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by enzo_g on 17/01/18.
 */

public class VehicleAdapter extends ArrayAdapter<Vehicles> {
    public interface OnDeviceSelectedListener {
    void handle(final Vehicles vehicles);
}

    private final OnDeviceSelectedListener onDeviceSelectedListener;

    VehicleAdapter(@NonNull final Context context, final OnDeviceSelectedListener listener , final List<Vehicles> vehicle) {
        super(context, R.layout.item_vehicle, vehicle);
        onDeviceSelectedListener = listener;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View holder = convertView;
        if (convertView == null) {
            final LayoutInflater vi = LayoutInflater.from(getContext());
            holder = vi.inflate(R.layout.item_vehicle, null);
        }

        final Vehicles vehicle = getItem(position);
        if (vehicle == null) {
            return holder;
        }


        // display the name
        final TextView vehicleName = holder.findViewById(R.id.vehicleItem);
        if (vehicleName != null) {
            vehicleName.setText(vehicle.getName());
        }
        // When this device item is clicked, trigger the listener
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (onDeviceSelectedListener != null) {
                    onDeviceSelectedListener.handle(vehicle);
                }
            }
        });
        return  holder;
    }

}