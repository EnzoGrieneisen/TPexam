package com.example.enzo_g.enzo_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by enzo_g on 16/01/18.
 */

public class ShipActivity extends AppCompatActivity {
    public static Intent getStartIntent(final Context ctx) {
        return new Intent(ctx, ShipActivity.class);
    }
    private final ApiService apiService = ApiService.Builder.getInstance();
    private Vehicles respond =null;
    private ArrayList<Ships> shipsNames =new ArrayList();
    private int i;
    private ShipsAdapter shipsAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);
        final ListView listShips= (ListView) findViewById(R.id.listShips);
        for ( i=1; i<=15; i++) {
            apiService.readShips(i).enqueue(new Callback<Ships>() {
                @Override
                public void onResponse(final Call<Ships> call, final Response<Ships> response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleResponse(response);
                        }
                    });
                }

                @Override
                public void onFailure(final Call<Ships> call, final Throwable t) {
                    t.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShipActivity.this, R.string.status_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
        //String[] values = new String[] { "Sand Crowler", "T-16 skyhopper",};
        ListView listView = (ListView) findViewById(R.id.listShips);
        shipsAdapter = new ShipsAdapter(this,deviceSelectedListener ,shipsNames);
        listView.setAdapter(shipsAdapter);

    }
    private void handleResponse(final Response<Ships> response) {
        if (response.isSuccessful()) {
            shipsNames.add(response.body());
            //Log.d("VEHICLEACTIV", vehicleNames.get(0).getName());
            shipsAdapter.notifyDataSetChanged();

        } else { // error HTTP
            try {
                final HttpError error = new Gson().fromJson(response.errorBody().string(), HttpError.class);
                Toast.makeText(ShipActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (final IOException e) {
                e.printStackTrace();
                Toast.makeText(ShipActivity.this, R.string.unknown_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private final ShipsAdapter.OnDeviceSelectedListener deviceSelectedListener = new ShipsAdapter.OnDeviceSelectedListener() {
        @Override
        public void handle(final Ships ships) {
            //aucune action pour le moment
            finish();
        }
    };

}
