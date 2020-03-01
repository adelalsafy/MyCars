package com.example.adelalsay.mycars.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.adelalsay.mycars.R;
import com.example.adelalsay.mycars.adapter.CarAdapter;
import com.example.adelalsay.mycars.database.DatabaseAccess;
import com.example.adelalsay.mycars.interfaces.OnRecyclerViewItemClickListener;
import com.example.adelalsay.mycars.model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar ;
    private RecyclerView rv ;
    private FloatingActionButton fab ;
    private DatabaseAccess db ;
    private CarAdapter carAdapter ;
    private static final int ADD_CAR_RQS_CODE = 1;
    private static final int EDIT_CAR_RQS_CODE = 1;
    public static final String CAR_KEY = "carkey" ;
    private static final int PERMISSION_REQ_CODE = 1 ;
    private String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,permissions,PERMISSION_REQ_CODE);

        }


        toolbar = findViewById(R.id.main_toolbar) ;
        setSupportActionBar(toolbar) ;
        rv = findViewById(R.id.main_rv) ;
        db = new DatabaseAccess(this);
        setDataInRecyclerView();
        fab =findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),ViewCarActivity.class);
                startActivityForResult(i,ADD_CAR_RQS_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.open();
                ArrayList<Car> cars=new ArrayList<>();
                cars = db.getCars(query);
                db.close();
                carAdapter.setCars(cars);
                carAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open();
                ArrayList<Car> cars=new ArrayList<>();
                cars = db.getCars(newText);
                db.close();
                carAdapter.setCars(cars);
                carAdapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ArrayList<Car> cars=new ArrayList<>();
                cars = db.getAllCars();
                db.close();
                carAdapter.setCars(cars);
                carAdapter.notifyDataSetChanged();

                return false;
            }
        });
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void setDataInRecyclerView(){
        db.open();
        ArrayList<Car> cars=new ArrayList<>();
        cars = db.getAllCars();
        db.close();
        carAdapter = new CarAdapter(cars, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int carId) {
                Intent i = new Intent(getBaseContext(),ViewCarActivity.class);
                i.putExtra(CAR_KEY,carId);
                startActivityForResult(i,EDIT_CAR_RQS_CODE);
            }
        });
        RecyclerView.LayoutManager lm = new GridLayoutManager(this,2
        );
        rv.setHasFixedSize(true);
        rv.setLayoutManager(lm);
        rv.setAdapter(carAdapter);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CAR_RQS_CODE){
            db.open();
            ArrayList<Car> cars= new ArrayList<>();
            cars = db.getAllCars();
            db.close();
            carAdapter.setCars(cars);
            carAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQ_CODE :
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {

                }else {

                }

                }

    }
}
