package ru.myitschool.florallace.feature.ordermaking.ui.timerecycler;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.Objects;

import ru.myitschool.florallace.databinding.ItemOrderBinding;
import ru.myitschool.florallace.feature.ordermaking.entity.OrderTime;

public class OrderTimeRecyclerViewHolder extends ViewHolder {

    private ItemOrderBinding binding;
    private OrderTimeRecyclerClickListener listener;
    TextView textView;

    View root;

    public OrderTimeRecyclerViewHolder(ItemOrderBinding binding,
                                       OrderTimeRecyclerClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
        textView = binding.nameTime;
        root = binding.getRoot();
    }

    public void bind(OrderTime item){
        String nameTime = item.getTimeName();
        String time = item.getTime();
        String fullTime = "";
        if(Objects.equals(nameTime, "minute") && Objects.equals(time, "15")){
            fullTime = "Через 15 минут";
        }

        if(Objects.equals(nameTime, "minute") && Objects.equals(time, "30")){
            fullTime = "Через 30 минут";
        }

        if(Objects.equals(nameTime, "hour") && Objects.equals(time, "1")){
            fullTime = "Через 1 час";
        }

        if(Objects.equals(nameTime, "hour") && Objects.equals(time, "2")){
            fullTime = "Через 2 часа";
        }
        binding.nameTime.setText(fullTime);
        binding.nameTime.setOnClickListener(s -> {
            listener.onClick(item.getTime(), item.getTimeName());
        });
    }

}
