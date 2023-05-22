package ru.myitschool.florallace.feature.ordermaking.ui.cartitemsrecycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import ru.myitschool.florallace.databinding.ItemOrderCartBinding;

public class CartItemRecyclerAdapter extends Adapter<CartItemRecyclerViewHolder> {

    @NonNull
    @Override
    public CartItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderCartBinding binding = ItemOrderCartBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        );
        return new CartItemRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
