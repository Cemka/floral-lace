package ru.myitschool.florallace.feature.cart.ui.cartrecycler;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import ru.myitschool.florallace.databinding.ItemProductCartBinding;
import ru.myitschool.florallace.domain.model.Product;

public class CartRecyclerViewHolder extends ViewHolder {

    private final ItemProductCartBinding binding;

    private final CartRecyclerClickListener listener;


    public CartRecyclerViewHolder(ItemProductCartBinding binding, CartRecyclerClickListener listener) {
       super(binding.getRoot());
       this.binding = binding;
       this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    public void bind(Product item, HashMap<Product, Integer> hashMapItems){
        Integer count = hashMapItems.get(item);
        if (count != null) {
            binding.textCount.setText(Integer.toString(count));
        } else {
            binding.textCount.setText("0");
        }
        Glide.with(binding.getRoot()).load(item.getPhotoUrl()).into(binding.image);
        binding.name.setText(item.getName());
        binding.textCount.setText(Integer.toString(count));
        binding.price.setText(Long.toString(item.getPrice()));
        binding.getRoot().setOnClickListener(v -> listener.onClick(item.getId()));
    }
}
