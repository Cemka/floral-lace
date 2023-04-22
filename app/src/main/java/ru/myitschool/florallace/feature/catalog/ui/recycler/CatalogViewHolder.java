package ru.myitschool.florallace.feature.catalog.ui.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import ru.myitschool.florallace.databinding.ItemProductBinding;
import ru.myitschool.florallace.domain.model.Product;

public class CatalogViewHolder extends ViewHolder {

    private final ItemProductBinding binding;
    private final CatalogClickListener listener;

    public CatalogViewHolder(ItemProductBinding binding, CatalogClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(Product item){
        Glide.with(binding.getRoot()).load(item.getPhotoUrl()).into(binding.image);
        binding.name.setText(item.getName());
        binding.price.setText(item.getPrice());
        binding.getRoot().setOnClickListener(v -> listener.onClick(item.getId()));
    }
}
