package ru.myitschool.florallace.feature.catalog.ui.recycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.databinding.ItemProductBinding;
import ru.myitschool.florallace.domain.model.Product;

public class CatalogAdapter extends Adapter<CatalogViewHolder> {

    private final CatalogClickListener listener;
    private List<Product> items = new ArrayList<>();

    public CatalogAdapter(CatalogClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create item's binding  of cartRecycler
        ItemProductBinding binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new CatalogViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
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
