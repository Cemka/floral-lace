package ru.myitschool.florallace.feature.ordermaking.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.main.ui.MainActivity;

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
}
