package ru.myitschool.florallace.feature.main.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.R;
import ru.myitschool.florallace.data.repository.ProductsRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.databinding.ActivityMainBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.registration.reg.presentation.AuthManager;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static Long USER_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthManager manager = new AuthManager(getBaseContext());
        USER_ID = manager.getUserId();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_bar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.bottomNavBar, navController);

        // Загружаем айтемы времени
//        NoDb.loadTime();

        UsersRepository.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("REP_TEST", response.body().get(1).toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("REP_TEST", "Fail call");
            }
        });

        UsersRepository.getUserById(MainActivity.USER_ID).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if(response.body() == null) {
                    throw new RuntimeException("User not found");
                }
            Log.d("REP_TEST!", response.body().getCartItems().toString());
                Log.d("REP_TEST!!", response.body().getFavouriteProducts().toString());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
                Log.d("REP_TEST", "Fail cart call");
            }
        });

        ProductsRepository.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products = response.body();
                ProductsRepository.getProduct(2L).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Log.d("REP_TEST", response.body().toString());
                        boolean result = products.remove(response.body());
                        int index = products.indexOf(response.body());
                        Log.d("REP_TEST_AFTER", products.toString());
                        Log.d("REP_TEST_BOL", Boolean.toString(result));
                        Log.d("REP_TEST_INDEX", Integer.toString(index));
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.d("REP_TEST", "Fail product call");
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("REP_TEST", "Fail product2 call");
            }
        });

        /*CartItemRepository.getCartItemById(2L,2L).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                Log.d("REP_TEST_cartItem", response.body().toString());
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {
                Log.d("REP_TEST_cartItem", "fail cart item");
            }
        });
*/



    }
}