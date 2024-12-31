package com.rasya.uts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Fooditem> foodList;
    private OnAddClickListener onAddClickListener;

    public interface OnAddClickListener {
        void onAddClick(Fooditem foodItem);
    }

    public FoodAdapter(Context context, List<Fooditem> foodList, OnAddClickListener onAddClickListener) {
        this.context = context;
        this.foodList = foodList;
        this.onAddClickListener = onAddClickListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_popular, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Fooditem foodItem = foodList.get(position);

        // Set data dari API
        Picasso.get().load(foodItem.getStrMealThumb()).into(holder.ivFood);
        holder.tvFoodName.setText(foodItem.getStrMeal());
        holder.tvFoodDes.setText(foodItem.getStrInstructions());
        holder.tvFoodPrice.setText("Price Rp."+ foodItem.getPrice());

        holder.btnAdd.setOnClickListener(v -> {
            onAddClickListener.onAddClick(foodItem);
            foodItem.setQuantity(1); // Set quantity menjadi 1 jika ditambahkan
            Cart.getInstance().addItem(foodItem);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFood;
        TextView tvFoodName, tvFoodDes, tvFoodPrice;
        Button btnAdd;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDes = itemView.findViewById(R.id.tvFoodDes);
            tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
