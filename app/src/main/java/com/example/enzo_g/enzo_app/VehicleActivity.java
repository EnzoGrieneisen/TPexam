package com.example.enzo_g.enzo_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.gson.Gson;

import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by enzo_g on 16/01/18.
 */

public class VehicleActivity extends AppCompatActivity {
    private final ApiService apiService = ApiService.Builder.getInstance();
    private Vehicles respond =null;
    private ArrayList<Vehicles> vehicleNames =new ArrayList();
    private int i;
    private VehicleAdapter vehicleAdapter;
    AlertDialog.Builder builder;

    public static Intent getStartIntent(final Context ctx) {
        return new Intent(ctx, VehicleActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        final ListView listvehicle= (ListView) findViewById(R.id.listvehicle);
        for ( i=1; i<=15; i++) {
            apiService.readVehicle(i).enqueue(new Callback<Vehicles>() {
                  @Override
                  public void onResponse(final Call<Vehicles> call, final Response<Vehicles> response) {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              handleResponse(response);
                          }
                      });
                  }

                  @Override
                  public void onFailure(final Call<Vehicles> call, final Throwable t) {
                      t.printStackTrace();
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(VehicleActivity.this, R.string.status_error, Toast.LENGTH_SHORT).show();
                          }
                      });
                  }
              });

        }
        //String[] values = new String[] { "Sand Crowler", "T-16 skyhopper",};
        ListView listView = (ListView) findViewById(R.id.listvehicle);
        vehicleAdapter = new VehicleAdapter(this,deviceSelectedListener ,vehicleNames);
        listView.setAdapter(vehicleAdapter);

    }
    private void handleResponse(final Response<Vehicles> response) {
        if (response.isSuccessful()) {
            vehicleNames.add(response.body());
            //Log.d("VEHICLEACTIV", vehicleNames.get(0).getName());
            vehicleAdapter.notifyDataSetChanged();

        } else { // error HTTP
            try {
                final HttpError error = new Gson().fromJson(response.errorBody().string(), HttpError.class);
                Toast.makeText(VehicleActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (final IOException e) {
                e.printStackTrace();
                Toast.makeText(VehicleActivity.this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private final VehicleAdapter.OnDeviceSelectedListener deviceSelectedListener = new VehicleAdapter.OnDeviceSelectedListener() {
        @Override
        public void handle(final Vehicles vehicles) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(VehicleActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(VehicleActivity.this);
            }
            builder.setTitle("Delete vehicle")
                    .setMessage("Are you sure you want to delete this Vehicle?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //vehicleNames.remove();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            finish();

        }
    };



}
