package ru.myitschool.florallace.feature.catalog.ui.recycler;

import android.annotation.SuppressLint;
import android.graphics.Color;

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

    @SuppressLint("SetTextI18n")
    public void bind(Product item){
        Glide.with(binding.getRoot()).load(item.getPhotoUrl()).into(binding.image);
        binding.name.setText(item.getName());
        binding.price.setText(Long.toString(item.getPrice()));
        binding.countNum.setText(Long.toString(item.getCountStart()));
        if(item.getCountLast() < 20){
            binding.countLast.setTextColor(Color.RED);
            binding.countLastNum.setTextColor(Color.RED);
        }
        binding.countLastNum.setText(Long.toString(item.getCountLast()));
        binding.getRoot().setOnClickListener(v -> listener.onClick(item.getId()));
    }
}
