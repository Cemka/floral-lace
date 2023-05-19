package ru.myitschool.florallace.feature.cart.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class FavListViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> _favProducts = new MutableLiveData<>();
    public LiveData<List<Product>> favProducts = _favProducts;

    public void load(Long userId){
        UsersRepository.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if(response.body() == null) {
                    throw new RuntimeException("User not found");
                }

                _favProducts.setValue(response.body().getFavouriteProducts());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
                Log.d("API", Arrays.toString(throwable.getStackTrace()));
            }
        });
    }
}
