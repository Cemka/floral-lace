package ru.myitschool.florallace.feature.cart.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<CartStatus> _status = new MutableLiveData<>();
    public LiveData<CartStatus> status = _status;

    private final MutableLiveData<HashMap<Product, Integer>> _productsHash = new MutableLiveData<>();
    public LiveData<HashMap<Product, Integer>> productsHash = _productsHash;


    public void load(Long userId){
        _status.setValue(CartStatus.LOADING);
        UsersRepository.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                _status.setValue(CartStatus.LOADED);
                if(response.body() == null) {
                    throw new RuntimeException("User not found");
                }

                List<CartItem> tempProductList = response.body().getCartItems();
                HashMap<Product, Integer> productIntegerHashMap = new HashMap<>();
                for (CartItem cartItem : tempProductList) {
                    productIntegerHashMap.put(cartItem.getProduct(), cartItem.getQuantity());
                }

                _productsHash.setValue(productIntegerHashMap);

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
                _status.setValue(CartStatus.FAILURE);
            }
        });
    }
}
