package ru.myitschool.florallace.feature.ordermaking.ui.cartitemsrecycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.databinding.ItemOrderCartBinding;
import ru.myitschool.florallace.domain.model.CartItem;

public class CartItemRecyclerAdapter extends Adapter<CartItemRecyclerViewHolder> {

    private List<CartItem> items = new ArrayList<>();

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
        holder.bind(items.get(position));
    }

    public void setProducts(List<CartItem> items){
        int count = getItemCount();
        this.items = items;
        notifyItemRangeChanged(0, max(count, getItemCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
