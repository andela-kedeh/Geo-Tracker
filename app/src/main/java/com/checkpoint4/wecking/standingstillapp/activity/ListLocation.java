package com.checkpoint4.wecking.standingstillapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.checkpoint4.wecking.standingstillapp.DataModel.Location;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.adapter.LocationAdapter;

import java.util.List;

public class ListLocation extends AppCompatActivity implements View.OnClickListener {

    private Location location;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageView backToMainActivity;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location_by_date);
        initialise();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        loadAdater();
    }

    private void loadAdater() {
        Intent intent = getIntent();
        intent.getBooleanExtra("isDate", true);
        List locationData = null;
        if(intent.getBooleanExtra("isDate", true))
            locationData = location.getLocationDataByDate();
        else {
            locationData = location.getLocationDataLocation();
            title.setText("Locations By Address");
        }
        LocationAdapter adapter = new LocationAdapter(this, locationData);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initialise(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        backToMainActivity = (ImageView) findViewById(R.id.to_main_activity);
        backToMainActivity.setOnClickListener(this);
        location = new Location(this);
        title = (TextView) findViewById(R.id.title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_main_activity:
                Intent intent = new Intent(ListLocation.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
