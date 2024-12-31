package com.rasya.uts;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private CartAdapter cartAdapter;
    private List<Fooditem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Ambil data dari Cart Singleton
        cartItems = Cart.getInstance().getCartItems();
        cartAdapter = new CartAdapter(cartItems, () -> updateCartSummary(getView()));
        rvCart.setAdapter(cartAdapter);

        updateCartSummary(view);  // Update summary saat tampilan pertama kali dibuat

        return view;
    }

    private void updateCartSummary(View view) {
        int totalItems = 0;
        int totalPrice = 0;

        // Loop untuk menghitung total item dan total harga
        for (Fooditem item : cartItems) {
            // Ambil harga dan konversi menjadi angka
            String priceString = item.getPrice();
            priceString = priceString.replaceAll("[^\\d]", ""); // Hanya angka
            int price = Integer.parseInt(priceString);

            int quantity = item.getQuantity();
            totalItems += quantity;
            totalPrice += price * quantity;
        }

        // Menampilkan total jumlah dan harga di UI
        TextView tvTotalItems = view.findViewById(R.id.tvTotalItems);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        TextView tvDeliveryFee = view.findViewById(R.id.tvDeliveryFee);
        TextView tvFinalTotal = view.findViewById(R.id.tvFinalTotal);

        int deliveryFee = 10000;  // Biaya pengantaran tetap
        tvTotalItems.setText("Total Item: " + totalItems);
        tvTotalPrice.setText(String.format("Jumlah: Rp. %,d", totalPrice));
        tvDeliveryFee.setText("Biaya Pengantaran: Rp. " + deliveryFee);
        tvFinalTotal.setText(String.format("Total: Rp. %,d", (totalPrice + deliveryFee)));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCartSummary(getView()); // Update ketika fragment diresume
    }
}
