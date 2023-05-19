package ru.myitschool.florallace.feature.cart.ui.cartrecycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.myitschool.florallace.databinding.ItemProductCartBinding;
import ru.myitschool.florallace.domain.model.Product;

public class CartRecyclerAdapter extends Adapter<CartRecyclerViewHolder> {

    private final CartRecyclerClickListener listener;

    private  List<Product> items = new ArrayList<>();

    private HashMap<Product, Integer> hashMapItems = new HashMap<>();


    public CartRecyclerAdapter(CartRecyclerClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductCartBinding binding = ItemProductCartBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        );

        return new CartRecyclerViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewHolder holder, int position) {
        holder.bind(items.get(position), hashMapItems);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setProducts(List<Product> items, HashMap<Product, Integer> hashMapItems){
        int count = getItemCount();
        this.items = new ArrayList<>(items);
        this.hashMapItems = new HashMap<>(hashMapItems);
        notifyItemRangeChanged(0, max(count, getItemCount()));
    }

    public void removeProduct(int id) {
        int position = -1;
        for (int i = 0; i < items.size(); i++) {
            Product product = items.get(i);
            if (product.getId() == id) {
                position = i;
                break;
            }
        }
        if (position != -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }
}
