package ru.myitschool.florallace.feature.ordermaking.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.R;
import ru.myitschool.florallace.databinding.FragmentMakingorderBinding;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.ordermaking.entity.OrderTime;
import ru.myitschool.florallace.feature.ordermaking.presentation.OrderViewModel;
import ru.myitschool.florallace.feature.ordermaking.ui.timerecycler.OrderTimeRecyclerAdapter;
import ru.myitschool.florallace.feature.ordermaking.ui.timerecycler.OrderTimeRecyclerClickListener;

public class OrderMakingFragment extends Fragment {

    private FragmentMakingorderBinding fragmentBinding;
    private OrderTimeRecyclerAdapter adapterTime;
    private OrderViewModel viewModel;
    private String time;

    private List<OrderTime> orderTimeList = new ArrayList<>();

    public void setTime(String time) {
        this.time = time;
    }

    public void setNameTime(String nameTime) {
        this.nameTime = nameTime;
    }

    private String nameTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = FragmentMakingorderBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        OrderTime time1 = new OrderTime("30", "minute");
        OrderTime time2 = new OrderTime("1", "hour");
        OrderTime time3 = new OrderTime("2", "hour");
        OrderTime time4 = new OrderTime("15", "minute");
        orderTimeList.add(time4);
        orderTimeList.add(time1);
        orderTimeList.add(time2);
        orderTimeList.add(time3);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentBinding.btnClose.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_navigation_makingOrder_to_navigation_cart2);
        });

        OrderTimeRecyclerClickListener listenerTime = new OrderTimeRecyclerClickListener() {
            @Override
            public void onClick(String time, String nameTime) {
                setTime(time);
                setNameTime(nameTime);

            }

            @Override
            public void onItemClick(int position) {
                adapterTime.onItemClick(position);
            }
        };

        adapterTime = new OrderTimeRecyclerAdapter(listenerTime);
        adapterTime.setDefaultColor(Color.parseColor("#ACAAA8"));
        adapterTime.setSelectedColor(0xFF131313);
        fragmentBinding.typeSec.setOnClickListener(v -> Toast.
                makeText(getContext(), "Coming soon...", Toast.LENGTH_SHORT)
                .show());
        fragmentBinding.recyclerOrderItems.setAdapter(adapterTime);
        renderItems();
        viewModel.user.observe(getViewLifecycleOwner(), this::renderUserData);
        viewModel.cartItems.observe(getViewLifecycleOwner(), this::renderCartItems);
        if(savedInstanceState == null) viewModel.loadUser();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentBinding = null;
    }

    private void renderItems(){
        adapterTime.setTimes(orderTimeList);
    }

    private void renderUserData(User user){
        String name = user.getFirstName() + " " + user.getSecondName();
        String phone = user.getPhoneNumb();
        fragmentBinding.dataRecName.setText(name);
        fragmentBinding.dataRecPhoneNum.setText(phone);
    }

    private void renderCartItems(List<CartItem> cartItems){

    }

}
