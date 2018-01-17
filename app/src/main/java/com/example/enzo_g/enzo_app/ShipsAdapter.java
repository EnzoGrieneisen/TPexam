package com.example.enzo_g.enzo_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by enzo_g on 17/01/18.
 */

public class ShipsAdapter extends ArrayAdapter<Ships> {
    public interface OnDeviceSelectedListener {
        void handle(final Ships ships);
    }

    private final ShipsAdapter.OnDeviceSelectedListener onDeviceSelectedListener;

    ShipsAdapter(@NonNull final Context context, final ShipsAdapter.OnDeviceSelectedListener listener , final List<Ships> ships) {
        super(context, R.layout.item_ships, ships);
        onDeviceSelectedListener = listener;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View holder = convertView;
        if (convertView == null) {
            final LayoutInflater vi = LayoutInflater.from(getContext());
            holder = vi.inflate(R.layout.item_ships, null);
        }

        final Ships ship = getItem(position);
        if (ship == null) {
            return holder;
        }


        // display the name
        final TextView shipsName = holder.findViewById(R.id.shipsItem);
        if (shipsName != null) {
            shipsName.setText(ship.getName());
        }

        // When this device item is clicked, trigger the listener
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (onDeviceSelectedListener != null) {
                    onDeviceSelectedListener.handle(ship);
                }
            }
        });
        return  holder;
    }

}
