package ru.myitschool.florallace.feature.ordermaking.presentation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.R;
import ru.myitschool.florallace.data.repository.CartItemRepository;
import ru.myitschool.florallace.data.repository.OrderItemRepository;
import ru.myitschool.florallace.data.repository.OrdersRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.Order;
import ru.myitschool.florallace.domain.model.OrderItem;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.main.ui.MainActivity;
import ru.myitschool.florallace.feature.ordermaking.entity.DelReqBody;
import ru.myitschool.florallace.feature.ordermaking.entity.InsertReqBody;

public class OrderViewModel extends ViewModel {

    private boolean status;

    private static final Long USER_ID = MainActivity.USER_ID;

    private static final String API_TAG = "API_TAG_ORDER";
    private final MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    private MutableLiveData<List<CartItem>> _cartItems = new MutableLiveData<>();
    public LiveData<List<CartItem>> cartItems = _cartItems;

    private MutableLiveData<Long> _orderId = new MutableLiveData<>();

    private MutableLiveData<OrderStatus> _status = new MutableLiveData<>();
    public LiveData<OrderStatus> statusOr = _status;


    public void loadUser() {
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

    public void loadCartItems() {
        UsersRepository.getUserById(USER_ID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.body() != null) {
                    _cartItems.setValue(response.body().getCartItems());
                } else {
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

    public void createOrderAndAddItems(Integer price, String location, String time, Context context, Long userId, NavController navController) {

        List<CartItem> cartItems = _cartItems.getValue();
        if (cartItems.isEmpty()) {
            Toast.makeText(context, "Добавьте товары в корзину", Toast.LENGTH_SHORT).show();
        } else {
            insertOrder(userId, price, location, time, context);
            new Handler().postDelayed(() -> {
                // Получение списка элементов корзины

                // Добавление элементов в заказ
                if (cartItems != null && !cartItems.isEmpty()) {
                    insertOrderItemsByList(cartItems, USER_ID);

                    new Handler().postDelayed(() -> {
                        // Получение списка элементов корзины
                        // Удаление элементов из корзины
                        if (cartItems != null && !cartItems.isEmpty()) {
                            deleteCartItems(cartItems);
                        }
                    }, 1500);
                }
            }, 1000);

            Toast.makeText(context, "Заказ успешно создан", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.action_navigation_makingOrder_to_navigation_cart2);
        }


    }

    private void insertOrder(Long userId, Integer price, String location, String time, Context context) {

        OrdersRepository.getByUserId(userId).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (response.body() == null) {
                    status = true;
                    OrdersRepository.insert(new Order(userId, null, price, location, time)).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                            if (response.isSuccessful()) {
                                // Получение и сохранение ID созданного заказа
                                assert response.body() != null;
                                Long orderId = response.body().getId();
                                _orderId.postValue(orderId);
                                Log.d(API_TAG, "Successful post order");
                            } else {
                                // Обработка ошибки при создании заказа
                                Log.d(API_TAG, "Failed to post order");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                            // Обработка ошибки при выполнении запроса
                            Log.d(API_TAG, "Failed to post order: " + t.getMessage());
                        }
                    });
                } else {
                    status = false;
                    Toast.makeText(context, "У вас уже есть активный заказ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {

            }
        });

    }

    private void insertOrderItemsByList(List<CartItem> cartItems, Long userId) {
        List<Long> cartItemsId = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            cartItemsId.add(cartItem.getId());
        }
        InsertReqBody body = new InsertReqBody(cartItemsId);

        if (status) {
            OrdersRepository.getByUserId(userId).enqueue(new Callback<Order>() {
                @Override
                public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                    Long orderId = null;
                    if (response.body() != null) {
                        orderId = response.body().getId();
                    } else {
                        Log.d(API_TAG, "Order is null");
                    }

                    OrderItemRepository.insertByListProducts(body, orderId).enqueue(new Callback<List<OrderItem>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<OrderItem>> call, @NonNull Response<List<OrderItem>> response) {
                            if (response.isSuccessful()) {
                                Log.d(API_TAG, "Successful post order items");
                            } else {
                                Log.d(API_TAG, "Failed to post order items");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<OrderItem>> call, @NonNull Throwable t) {
                            Log.d(API_TAG, "Failed to post order items: " + t.getMessage());
                        }
                    });
                }

                @Override
                public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                    Log.d(API_TAG, "Failed to get order: " + t.getMessage());
                }
            });
        }

    }

    private void deleteCartItems(List<CartItem> cartItems){
        List<Long> ids = new ArrayList<>();
        cartItems.forEach(s -> ids.add(s.getId()));
        DelReqBody body = new DelReqBody(ids);
        CartItemRepository.deleteAllById(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(API_TAG, "onResponse: suc del");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(API_TAG, "onFailure: no suc del");
            }
        });
    }
}
