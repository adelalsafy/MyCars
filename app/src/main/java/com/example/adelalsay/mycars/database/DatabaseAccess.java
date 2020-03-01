package com.example.adelalsay.mycars.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.adelalsay.mycars.model.Car;

import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;

    public DatabaseAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;

    }

    public void open() {
        this.database = this.openHelper.getWritableDatabase();

    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }

    }

    //INSERT
    public boolean insertCar(Car car) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.CAR_CLN_DESCRAPTION, car.getDescraption());
        values.put(MyDatabase.CAR_CLN_MODEL, car.getModel());
        values.put(MyDatabase.CAR_CLN_COLOR, car.getColor());
        values.put(MyDatabase.CAR_CLN_IMAGE, car.getModel());
        values.put(MyDatabase.CAR_CLN_DPL, car.getDpl());
        long result = database.insert(MyDatabase.CAR_TB_NAME, null, values);
        return result != -1;

    }

    // UPDATE
    public boolean updateCar(Car car) {
        String args[] = {car.getId()+""};
        ContentValues values = new ContentValues();
        values.put(MyDatabase.CAR_CLN_DESCRAPTION, car.getDescraption());
        values.put(MyDatabase.CAR_CLN_MODEL, car.getModel());
        values.put(MyDatabase.CAR_CLN_COLOR, car.getColor());
        values.put(MyDatabase.CAR_CLN_IMAGE, car.getImage());
        values.put(MyDatabase.CAR_CLN_DPL, car.getDpl());
        long result = database.update(MyDatabase.CAR_TB_NAME, values, "id=?", args);
        return result > 0;

    }
    // get car count
    public long getCarCount() {
        return DatabaseUtils.queryNumEntries(database, MyDatabase.CAR_TB_NAME);


    }

    public boolean deleteCar(Car car) {
        String args[] = {String.valueOf(car.getId())};
        int result = database.delete(MyDatabase.CAR_TB_NAME, "id=?", args);
        return  result > 0 ;
    }
    public ArrayList<Car>getAllCars(){
        ArrayList<Car>cars =new ArrayList<>();
        Cursor cursor =database.rawQuery("SELECT * FROM "+MyDatabase.CAR_TB_NAME,null);

        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.CAR_CLN_ID));
                String descraption = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_DESCRAPTION));
                String model = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_COLOR));
                String image = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_IMAGE));
                double dpl = cursor.getDouble(cursor.getColumnIndex(MyDatabase.CAR_CLN_DPL));
                Car c = new Car(id,model,color,descraption,image,dpl);
                cars.add(c) ;
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars ;
    }
    //Search

    public ArrayList<Car>getCars(String modelSearch){
        String args[] = {modelSearch +"%"};
        ArrayList<Car>cars =new ArrayList<>();
        Cursor cursor =database.rawQuery("SELECT * FROM "+MyDatabase.CAR_TB_NAME+
                " WHERE "+MyDatabase.CAR_CLN_MODEL+" LIKE ?",args);

        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.CAR_CLN_ID));
                String descraption = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_DESCRAPTION));
                String model = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_COLOR));
                String image = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_IMAGE));
                double dpl = cursor.getDouble(cursor.getColumnIndex(MyDatabase.CAR_CLN_DPL));
                Car c = new Car(id,model,color,descraption,image,dpl);
                cars.add(c) ;
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars ;
    }
    public Car getCar(int carId) {
        String args[] = {String.valueOf(carId)};

        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME +
                " WHERE " + MyDatabase.CAR_CLN_ID + "=?", args);

        if (cursor != null && cursor.moveToFirst()) {

            int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.CAR_CLN_ID));
            String descraption = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_DESCRAPTION));
            String model = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_MODEL));
            String color = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_COLOR));
            String image = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_IMAGE));
            double dpl = cursor.getDouble(cursor.getColumnIndex(MyDatabase.CAR_CLN_DPL));
            Car c = new Car(id, model, color, descraption, image, dpl);
            cursor.close();
            return c;
        }
        return null;

    }
}


