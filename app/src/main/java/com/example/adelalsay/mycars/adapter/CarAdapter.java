package com.example.adelalsay.mycars.adapter;


import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adelalsay.mycars.R;
import com.example.adelalsay.mycars.interfaces.OnRecyclerViewItemClickListener;
import com.example.adelalsay.mycars.model.Car;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.RecyclerViewCatViewHolder> {
    ArrayList<Car>cars ;
    private OnRecyclerViewItemClickListener listener ;

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    public CarAdapter(ArrayList<Car> cars , OnRecyclerViewItemClickListener listener) {
        this.cars = cars;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public RecyclerViewCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_car_layout,null,false);

       RecyclerViewCatViewHolder CatViewHolder = new RecyclerViewCatViewHolder(v) ;
        return CatViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCatViewHolder holder, int position) {
        Car car =cars.get(position);
        if (car.getImage() != null && ! car.getImage().isEmpty()) {
            holder.iv_car.setImageURI(Uri.parse(car.getImage()));
        }else {
            holder.iv_car.setImageResource(R.drawable.car_imge);
        }
        holder.tv_model.setText(car.getModel());
        holder.tv_color.setText(car.getColor());
        try {
            holder.tv_color.setTextColor(Color.parseColor(car.getColor()));
        }catch (Exception e){

        }

        holder.tv_dpl.setText(car.getDpl()+"");
        holder.carId = car.getId() ;



    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
    // Holder Class For RecyclerView
    class RecyclerViewCatViewHolder extends RecyclerView.ViewHolder{
        TextView tv_model,tv_color,tv_dpl ;
        ImageView iv_car ;
        int carId ;

        public RecyclerViewCatViewHolder(@NonNull View itemView) {
            super(itemView);
        iv_car = itemView.findViewById(R.id.custom_card_iv);
        tv_model = itemView.findViewById(R.id.custom_card_tv_model);
        tv_color = itemView.findViewById(R.id.custom_card_tv_color);
        tv_dpl = itemView.findViewById(R.id.custom_card_tv_dpl);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(carId);
            }
        });
        }

    }
}
