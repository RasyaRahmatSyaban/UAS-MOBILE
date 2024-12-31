package com.rasya.uts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Fooditem> cartItems;
    private OnQuantityChangeListener listener;

    public CartAdapter(List<Fooditem> cartItems, OnQuantityChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Fooditem foodItem = cartItems.get(position);

        // Gunakan Picasso untuk memuat gambar makanan dari URL
        Picasso.get().load(foodItem.getStrMealThumb()).into(holder.ivFoodImage);

        // Set data makanan ke TextView
        holder.tvFoodName.setText(foodItem.getStrMeal());
        holder.tvFoodPrice.setText("Rp. " + foodItem.getPrice()); // Menampilkan harga makanan
        holder.tvQuantity.setText(String.valueOf(foodItem.getQuantity()));

        // Tombol penambahan jumlah
        holder.tvPlus.setOnClickListener(v -> {
            foodItem.setQuantity(foodItem.getQuantity() + 1);
            notifyItemChanged(position);
            listener.onQuantityChanged();
        });

        // Tombol pengurangan jumlah atau penghapusan item
        holder.tvMinus.setOnClickListener(v -> {
            if (foodItem.getQuantity() > 1) {
                foodItem.setQuantity(foodItem.getQuantity() - 1);
                notifyItemChanged(position);
            } else if (foodItem.getQuantity() == 1) {
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            }

            if(listener != null){
                listener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoodImage;
        TextView tvFoodName, tvFoodPrice, tvQuantity, tvPlus, tvMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodImage = itemView.findViewById(R.id.ivCartFoodImage);
            tvFoodName = itemView.findViewById(R.id.tvCartFoodName);
            tvFoodPrice = itemView.findViewById(R.id.tvCartFoodPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPlus = itemView.findViewById(R.id.tvPlus);
            tvMinus = itemView.findViewById(R.id.tvMinus);
        }
    }
}
