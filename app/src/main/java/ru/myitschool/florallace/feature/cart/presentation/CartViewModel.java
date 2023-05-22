package ru.myitschool.florallace.feature.cart.presentation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.CartItemRepository;
import ru.myitschool.florallace.data.repository.FavItemRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.FavItem;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<CartStatus> _status = new MutableLiveData<>();
    public LiveData<CartStatus> status = _status;

    private final MutableLiveData<HashMap<Product, Integer>> _productsHash = new MutableLiveData<>();
    public LiveData<HashMap<Product, Integer>> productsHash = _productsHash;

    private final MutableLiveData<Integer> _allPrice = new MutableLiveData<>();
    public LiveData<Integer> allPrice = _allPrice;

    private final static String API_TAG = "API";


    public void load(Long userId) {
        _status.setValue(CartStatus.LOADING);
        UsersRepository.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                _status.setValue(CartStatus.LOADED);
                if (response.body() == null) {
                    throw new RuntimeException("User not found");
                }

                List<CartItem> tempProductList = response.body().getCartItems();
                HashMap<Product, Integer> productIntegerHashMap = new HashMap<>();
                for (CartItem cartItem : tempProductList) {
                    productIntegerHashMap.put(cartItem.getProduct(), cartItem.getQuantity());
                }
                int sum = 0;

                for (CartItem cartItem : tempProductList) {
                    int onePr = cartItem.getQuantity() * cartItem.getProduct().getPrice();
                    sum += onePr;
                }

                _allPrice.setValue(sum);
                _productsHash.setValue(productIntegerHashMap);

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
                _status.setValue(CartStatus.FAILURE);
            }
        });
    }

    public void deleteCartItem(int deleteItemId, Long userId) {
        Log.d(API_TAG, "deleteItemId" + deleteItemId);
        Log.d(API_TAG, "userId" + userId);
        CartItemRepository.getCartItemByUserIdAndProductId(userId, (long) deleteItemId).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(@NonNull Call<CartItem> call, @NonNull Response<CartItem> response) {
                Log.d(API_TAG, "deleteItemId!" + deleteItemId);
                Log.d(API_TAG, "userId!" + userId);
                Log.d(API_TAG, "suc get cartIt");
                Log.d(API_TAG, response.body() + " ");
                long idCart = -1;
                if (response.body() != null) {
                    idCart = response.body().getId();
                }

                CartItemRepository.deleteById(idCart).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        Log.d(API_TAG, "suc del ");
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d(API_TAG, "no suc del ");
                        Log.d(API_TAG, Arrays.toString(t.getStackTrace()));
                    }
                });


            }

            @Override
            public void onFailure(@NonNull Call<CartItem> call, @NonNull Throwable t) {
                Log.d(API_TAG, "no suc get cartIt");
            }
        });
    }

    public void postFavItem(long userId, long productId, Context context){

        FavItemRepository.getFavItemByUserIdAndProductId(userId, productId).enqueue(new Callback<FavItem>() {
            @Override
            public void onResponse(@NonNull Call<FavItem> call, @NonNull Response<FavItem> response) {
                if (response.body() != null) {
                    Toast.makeText(context, "Товар уже есть в избранных", Toast.LENGTH_SHORT).show();
                } else {
                    FavItemRepository.insertFavItem(userId, productId).enqueue(new Callback<FavItem>() {
                        @Override
                        public void onResponse(@NonNull Call<FavItem> call, @NonNull Response<FavItem> response) {
                            Log.d(API_TAG, "suc post fav item");
                            Toast.makeText(context, "Товар в избранных", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<FavItem> call, @NonNull Throwable t) {
                            Log.d(API_TAG, "suc post fav item");
                            Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavItem> call, @NonNull Throwable t) {

            }
        });
    }



}
