package ru.myitschool.florallace.feature.main.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.R;
import ru.myitschool.florallace.data.api.product.ProductApiService;
import ru.myitschool.florallace.data.repository.ProductsRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.databinding.ActivityMainBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_bar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.bottomNavBar, navController);

        UsersRepository.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("REP_TEST", response.body().get(1).getFavouriteProducts().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("REP_TEST", "Fail call");
            }
        });

    }
}