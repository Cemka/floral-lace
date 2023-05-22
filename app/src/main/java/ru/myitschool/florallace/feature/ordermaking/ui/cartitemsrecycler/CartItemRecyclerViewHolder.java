package ru.myitschool.florallace.feature.ordermaking.ui.cartitemsrecycler;


import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;


import ru.myitschool.florallace.databinding.ItemOrderCartBinding;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.Product;

public class CartItemRecyclerViewHolder extends ViewHolder {

    ItemOrderCartBinding binding;

    public CartItemRecyclerViewHolder(ItemOrderCartBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(CartItem item){
        Product product = item.getProduct();
        Glide.with(binding.getRoot()).load(product.getPhotoUrl()).into(binding.image);
        binding.name.setText(product.getName());
        binding.textCount.setText(Integer.toString(item.getQuantity()));
        int price = item.getQuantity() * product.getPrice();
        binding.price.setText(Integer.toString(price));

    }
}
