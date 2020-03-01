package com.example.adelalsay.mycars.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adelalsay.mycars.R;
import com.example.adelalsay.mycars.database.DatabaseAccess;
import com.example.adelalsay.mycars.model.Car;
import com.google.android.material.textfield.TextInputEditText;

public class ViewCarActivity extends AppCompatActivity {
    private Toolbar tb ;
    private TextInputEditText et_model,et_color,et_dpl,et_description ;
    private ImageView iv_car ;
    private int carId = -1 ;
    private DatabaseAccess db ;
    private Uri imageUri ;
    private static final int PICK_IMAGE_REQ_CODE = 1 ;
    public static final int ADD_CAR_RESULT_CODE = -1 ;
    public static final int EDIT_CAR_RESULT_CODE = -1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);
        tb = findViewById(R.id.details_toolbar);
        setSupportActionBar(tb);
        iv_car = findViewById(R.id.details_iv) ;
        et_model = findViewById(R.id.et_details_model) ;
        et_color = findViewById(R.id.et_details_color) ;
        et_dpl = findViewById(R.id.et_details_dpl) ;
        et_description = findViewById(R.id.et_details_description) ;
        db = DatabaseAccess.getInstance(this) ;
        Intent intent = getIntent() ;
       carId = intent.getIntExtra(MainActivity.CAR_KEY,-1) ;
       if(carId== -1){
           //add item
           enableFieldes();
           clearFieldes();

       }else {
           // show item
           disableFieldes();
           db.open();
       Car c = db.getCar(carId) ;
       db.close();
       if(c != null){
           fillCarToFildes(c);

       }



       }
       iv_car.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
               startActivityForResult(i,PICK_IMAGE_REQ_CODE);
           }
       });



    }
    private void fillCarToFildes(Car c){
        iv_car.setImageURI(Uri.parse(c.getImage()+""));
        et_model.setText(c.getModel());
        et_color.setText(c.getColor());
        et_description.setText(c.getDescraption());
        et_dpl.setText(String.valueOf(c.getDpl()));
    }
    private void disableFieldes(){
        iv_car.setEnabled(false);
        et_model.setEnabled(false);
        et_color.setEnabled(false);
        et_description.setEnabled(false);
        et_dpl.setEnabled(false);

    }
    private void enableFieldes(){
        iv_car.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_description.setEnabled(true);
        et_dpl.setEnabled(true);

    }
    private void clearFieldes(){
        iv_car.setImageURI(null);
        et_model.setText("");
        et_color.setText("");
        et_description.setText("");
        et_dpl.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        MenuItem save = menu.findItem(R.id.details_menu_save) ;
        MenuItem edit = menu.findItem(R.id.details_menu_edit) ;
        MenuItem delete = menu.findItem(R.id.details_menu_delete) ;

        if(carId== -1){
            //add item
            save.setVisible(true) ;
            edit.setVisible(false) ;
            delete.setVisible(false) ;

        }else {
            // show item
            save.setVisible(false) ;
            edit.setVisible(true) ;
            delete.setVisible(true) ;



        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model,color,desc,image = "";
        Double dpl ;
        db.open();
        switch (item.getItemId()) {
            case R.id.details_menu_save:
                model =et_model.getText().toString();
                color =et_color.getText().toString();
                desc =et_description.getText().toString();
                dpl =Double.parseDouble(et_dpl.getText().toString());
                if(imageUri != null)
                    image = imageUri.toString() ;
                boolean res ;
                Car c = new Car(carId,model,color,desc,image,dpl);
                if(carId == -1) {
                     res = db.insertCar(c);
                    if(res) {
                        Toast.makeText(getBaseContext(), "Car Added Successfully", Toast.LENGTH_LONG).show();
                        setResult(ADD_CAR_RESULT_CODE,null);
                        finish();

                    }
                }else {
                     res = db.updateCar(c);
                    if (res) {
                        Toast.makeText(getBaseContext(), "Car modified Successfully", Toast.LENGTH_LONG).show();
                        setResult(EDIT_CAR_RESULT_CODE, null);
                        finish();
                    }
                }
                return true;
            case R.id.details_menu_edit:
                enableFieldes();
                MenuItem save = tb.getMenu().findItem(R.id.details_menu_save) ;
                MenuItem edit = tb.getMenu().findItem(R.id.details_menu_edit) ;
                MenuItem delete = tb.getMenu().findItem(R.id.details_menu_delete) ;
                save.setVisible(true) ;
                edit.setVisible(false) ;
                delete.setVisible(false) ;
                return true;
            case R.id.details_menu_delete:
                 c = new Car(carId,null,null,null,null,0.0);
                    res = db.deleteCar(c);
                    if (res) {
                        Toast.makeText(getBaseContext(), "Car deleted Successfully", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK,null);
                        finish();
                    }


                return true;

        }
        db.close();

            return false ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQ_CODE && requestCode == RESULT_OK ){
            if(data!= null ){
                imageUri = data.getData() ;

                iv_car.setImageURI(imageUri);
            }
        }



    }

}
