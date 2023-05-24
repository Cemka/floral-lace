package ru.myitschool.florallace.feature.registration.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.R;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.databinding.FragmentAuthBinding;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.main.ui.MainActivity;
import ru.myitschool.florallace.feature.registration.reg.presentation.AuthManager;

public class LoginFragment extends Fragment {

    FragmentAuthBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        AuthManager authManager = new AuthManager(context);
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        if(authManager.isLoggedIn()){
            startActivity(intent);
        }else {
            binding.btnLogin.setOnClickListener(v -> {
                String pas = String.valueOf(binding.editTextPassword.getText());
                String phoneNumb = "+" + binding.editTextPhoneNumb.getText();
                Log.d("API_TAG", pas);
                Log.d("API_TAG", phoneNumb);
                UsersRepository.getUserByPasAndPhoneNumb(pas, phoneNumb).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if(response.body() == null){
                            Log.d("API_TAG", "null!!!!");
                        }else{
                            Log.d("API_TAG", response.body().toString());
                        }

                        if (response.body() != null) {
                            authManager.setLoggedIn(true);
                            authManager.setUserId(response.body().getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Log.d("API_TAG_LOGIN", "onFailure:");
                    }
                });
            });
        }
        binding.btnReg.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_navigation_reg_auth_to_navigation_reg_reg);
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
