package ru.myitschool.florallace.feature.cart.ui.favouriterecycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.myitschool.florallace.databinding.ItemProductCartBinding;
import ru.myitschool.florallace.databinding.ItemProductFavouriteBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.cart.ui.cartrecycler.CartRecyclerViewHolder;

public class FavouriteRecyclerAdapter extends Adapter<FavouriteRecyclerViewHolder> {

    FavouriteRecyclerClickListener listener;

    List<Product> items = new ArrayList<>();

    public FavouriteRecyclerAdapter(FavouriteRecyclerClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public FavouriteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductFavouriteBinding binding = ItemProductFavouriteBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        );

        return new FavouriteRecyclerViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteRecyclerViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setProducts(List<Product> items){
        int count = getItemCount();
        this.items = new ArrayList<>(items);
        notifyItemRangeChanged(0, max(count, getItemCount()));
    }
}
