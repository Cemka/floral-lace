package ru.myitschool.florallace.feature.cart.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.api.order.OrderApiService;
import ru.myitschool.florallace.data.repository.CartItemRepository;
import ru.myitschool.florallace.data.repository.OrdersRepository;
import ru.myitschool.florallace.data.repository.ProductsRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.CartItem;
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

    public void deleteProductFromCart(Long userId, int deleteItemId) {
        UsersRepository.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        CartItemRepository.getCartItemById(userId, (long) deleteItemId).enqueue(new Callback<CartItem>() {
                            @Override
                            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                                List<CartItem> cartItems = user.getCartItems();
                                Log.d(API_TAG, "before delete: " + cartItems.toString());
                                Log.d(API_TAG, "User before delete: " + user.toString());
                                boolean temp = cartItems.remove(response.body());
                                user.setCartItems(cartItems);
                                Log.d(API_TAG, "onResponse: " + response.body());
                                Log.d(API_TAG, "bol: " + Boolean.toString(temp));
                                Log.d(API_TAG, "after delete: " + cartItems.toString());
                                Log.d(API_TAG, "after before delete: " + user.toString());

                                UsersRepository.updateUser(userId, user).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        if (response.isSuccessful()) {
                                            Log.d(API_TAG, "Product deleted successfully from cart");
                                        } else {
                                            Log.d(API_TAG, "Failed to delete product from cart");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Log.d(API_TAG, "Failed to delete product from cart: " + t.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<CartItem> call, Throwable t) {

                            }
                        });
                    } else {
                        Log.d(API_TAG, "User not found");
                    }
                } else {
                    Log.d(API_TAG, "Failed to retrieve user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(API_TAG, "Failed to retrieve user data: " + t.getMessage());
            }
        });
    }

    public void deleteCartItem(int deleteItemId, Long userId) {
        Log.d(API_TAG, "deleteItemId" + deleteItemId);
        Log.d(API_TAG, "userId" + userId);
        CartItemRepository.getCartItemById(userId, (long) deleteItemId).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
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
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d(API_TAG, "suc del ");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d(API_TAG, "no suc del ");
                    }
                });


            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {
                Log.d(API_TAG, "no suc get cartIt");
            }
        });

    }


}
