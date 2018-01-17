package com.example.enzo_g.enzo_app;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  StarWarsApi.init();


        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Starships list", "vehicles list",};
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(itemsAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                if (position==0){
                    startActivity(ShipActivity.getStartIntent(MainActivity.this)); // start config activity

                }
                else {
                    startActivity(VehicleActivity.getStartIntent(MainActivity.this)); // start config activity

                }

        };
    });

    }


}
