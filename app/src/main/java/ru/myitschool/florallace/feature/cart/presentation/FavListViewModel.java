package ru.myitschool.florallace.feature.cart.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.FavItemRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.FavItem;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class FavListViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> _favProducts = new MutableLiveData<>();
    public LiveData<List<Product>> favProducts = _favProducts;

    private static final String API_TAG = "API_TAG_FAV_LIST_API";

    public void load(Long userId) {
        UsersRepository.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if (response.body() == null) {
                    throw new RuntimeException("User not found");
                }

                List<Product> products = new ArrayList<>();
                for (FavItem item:
                     response.body().getFavouriteProducts()) {
                    products.add(item.getProduct());

                }

                _favProducts.setValue(products);

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
                Log.d("API", Arrays.toString(throwable.getStackTrace()));
            }
        });
    }

    public void deleteFromFavList(Long deleteItemId, Long userId) {
        FavItemRepository.getFavItemByUserIdAndProductId(userId, deleteItemId).enqueue(new Callback<FavItem>() {
            @Override
            public void onResponse(@NonNull Call<FavItem> call, @NonNull Response<FavItem> response) {

                Log.d(API_TAG, "user founded");

                long favId = -1;
                if (response.body() != null) {
                    favId = response.body().getId();
                }

                FavItemRepository.deleteById(favId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        Log.d(API_TAG, "suc del fav item");
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d(API_TAG, "no suc del fav item");
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<FavItem> call, @NonNull Throwable t) {
                Log.d(API_TAG, "user no founded");

            }
        });
    }


}
