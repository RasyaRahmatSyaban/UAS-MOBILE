package com.rasya.uts;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<Fooditem> cartItems;

    private Cart() {
        cartItems = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(Fooditem item) {
        boolean itemExists = false;

        for (Fooditem cartItem : cartItems) {
            if (cartItem.getIdMeal().equals(item.getIdMeal())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItems.add(item);
        }
    }

    public List<Fooditem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
