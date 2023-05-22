package ru.myitschool.florallace.feature.ordermaking.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.OrderItemRepository;
import ru.myitschool.florallace.data.repository.OrdersRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.Order;
import ru.myitschool.florallace.domain.model.OrderItem;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.main.ui.MainActivity;
import ru.myitschool.florallace.feature.ordermaking.entity.InsertReqBody;

public class OrderViewModel extends ViewModel {

    private static final Long USER_ID = MainActivity.USER_ID;

    private static final String API_TAG = "API_TAG_ORDER";
    private final MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    private MutableLiveData<List<CartItem>> _cartItems = new MutableLiveData<>();
    public LiveData<List<CartItem>> cartItems = _cartItems;


    public void loadUser(){
        UsersRepository.getUserById(USER_ID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                _user.setValue(response.body());
                Log.d(API_TAG, "suc order get user");
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d(API_TAG, "no suc order get user");
            }
        });
    }

    public void loadCartItems(){
        UsersRepository.getUserById(USER_ID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.body() != null){
                    _cartItems.setValue(response.body().getCartItems());
                }
                else {
                    Log.d(API_TAG, "Response body is null");
                }
                Log.d(API_TAG, "Successful get user");
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d(API_TAG, "Fail get user");
            }
        });
    }

    public void insertOrder(Long userId,
                            Integer price,
                            String location,
                            String time){

        OrderItemRepository.getById(4L).enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(response.body());
                Order order = new Order(1L, orderItems, 123, "testloc", "testtime");
                List<Long> ids = new ArrayList<>();
                ids.add(3L);
                ids.add(4L);
                // fixme понять что по чем
                OrdersRepository.insert(order, ids).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                        Log.d(API_TAG, "suc post order");
                        if(order.getId() != null) Log.d(API_TAG, order.getId().toString());
                    }

                    @Override
                    public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                        Log.d(API_TAG, "no suc post order");
                    }
                });
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {

            }
        });

    }

    public void insertOrderItemsByList(List<CartItem> cartItems, Long orderId){
        List<Long> productsId = new ArrayList<>();
        for(CartItem cartItem : cartItems){
            productsId.add(cartItem.getProduct().getId());
        }
        InsertReqBody body = new InsertReqBody(productsId);

        OrderItemRepository.insertByListProducts(body, orderId).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderItem>> call, @NonNull Response<List<OrderItem>> response) {
                Log.d(API_TAG, "suc post orderitems");
            }

            @Override
            public void onFailure(@NonNull Call<List<OrderItem>> call, @NonNull Throwable t) {
                Log.d(API_TAG, "no suc post orderitems");
            }
        });
    }
}
