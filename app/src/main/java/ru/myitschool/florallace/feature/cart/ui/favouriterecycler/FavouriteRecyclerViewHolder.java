package ru.myitschool.florallace.feature.cart.ui.favouriterecycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import ru.myitschool.florallace.databinding.ItemProductCartBinding;
import ru.myitschool.florallace.databinding.ItemProductFavouriteBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.cart.ui.cartrecycler.CartRecyclerClickListener;

public class FavouriteRecyclerViewHolder extends ViewHolder {

    private final ItemProductFavouriteBinding binding;

    private final FavouriteRecyclerClickListener listener;

    public FavouriteRecyclerViewHolder(ItemProductFavouriteBinding binding,
                                       FavouriteRecyclerClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(Product item){
        Glide.with(binding.getRoot()).load(item.getPhotoUrl()).into(binding.image);
        binding.name.setText(item.getName());
        binding.price.setText(Long.toString(item.getPrice()));
        binding.getRoot().setOnClickListener(v -> listener.onClick(item.getId()));
    }


}
