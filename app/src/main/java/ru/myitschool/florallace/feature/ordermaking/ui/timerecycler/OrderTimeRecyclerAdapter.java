package ru.myitschool.florallace.feature.ordermaking.ui.timerecycler;

import static java.lang.Math.max;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.databinding.ItemOrderBinding;
import ru.myitschool.florallace.feature.ordermaking.entity.OrderTime;

public class OrderTimeRecyclerAdapter extends Adapter<OrderTimeRecyclerViewHolder> {

    private final OrderTimeRecyclerClickListener listener;
    private List<OrderTime> items = new ArrayList<>();
    private int selectedItemIndex = -1;
    private int defaultColor;
    private int selectedColor;

    public void setDefaultColor(int color) {
        defaultColor = color;
    }

    public void setSelectedColor(int color) {
        selectedColor = color;
    }

    public void onItemClick(int position) {
        int previousSelectedIndex = selectedItemIndex;
        selectedItemIndex = position;
        notifyItemChanged(previousSelectedIndex);
        notifyItemChanged(selectedItemIndex);
    }

    public void clearSelection() {
        int previousSelectedIndex = selectedItemIndex;
        selectedItemIndex = -1;
        notifyItemChanged(previousSelectedIndex);
    }

    public OrderTimeRecyclerAdapter(OrderTimeRecyclerClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public OrderTimeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        );

        return new OrderTimeRecyclerViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderTimeRecyclerViewHolder holder, int position) {
        holder.bind(items.get(position));

        holder.root.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

        int textColor = (position == selectedItemIndex) ? selectedColor : defaultColor;
        holder.textView.setTextColor(textColor);
    }

    public void setTimes(List<OrderTime> items){
        int count = getItemCount();
        this.items = items;
        notifyItemRangeChanged(0, max(count, getItemCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
