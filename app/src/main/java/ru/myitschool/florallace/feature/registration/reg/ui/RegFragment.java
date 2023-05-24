package ru.myitschool.florallace.feature.registration.reg.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import ru.myitschool.florallace.databinding.FragmentRegBinding;
import ru.myitschool.florallace.domain.model.User;

public class RegFragment extends Fragment {
    FragmentRegBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();

        binding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                isValidPassword(password);
            }
        });

        binding.btnReg.setOnClickListener(v -> {
            String phoneNumb = "+" + binding.editTextPhoneNumb.getText();
            String pas = String.valueOf(binding.editTextPassword.getText());
            String firName = String.valueOf(binding.editTextFirName.getText());
            String secName = String.valueOf(binding.editTextSecName.getText());
            String countBonus = "0";
            if(isValidPhoneNumber(phoneNumb) && isValidPasswordBol(pas)){
                UsersRepository.insert(phoneNumb, firName, secName, countBonus, pas).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        Toast.makeText(context, "Пользователь успешно создан", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_navigation_reg_reg_to_navigation_reg_auth);
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                    }
                });
            }else{
                Toast.makeText(getContext(), "Пароль или " +
                        "телефон не " +
                        "соответсвуют стандарту", Toast.LENGTH_SHORT).show();
            }
        });

    }
    // Метод для проверки пароля
    private void isValidPassword(String password) {
        // Проверка наличия не менее двух латинских букв
        int latinLetterCount = password.replaceAll("[^a-zA-Z]", "").length();
        if (latinLetterCount > 2) {
            binding.containsLetters.setTextColor(Color.parseColor("#054441"));
        }

        // Проверка наличия хотя бы одной большой латинской буквы
        boolean containsUpperCase = containsUpperCaseLetter(password);
        if (containsUpperCase) {
            binding.containsCaps.setTextColor(Color.parseColor("#054441"));
        }

        // Проверка наличия хотя бы одной латинской буквы и общей длины пароля более 8 символов
        if(password.length() > 8) binding.eightNum.setTextColor(Color.parseColor("#054441"));;
    }

    private boolean isValidPasswordBol(String password) {
        // Проверка наличия не менее двух латинских букв
        int latinLetterCount = password.replaceAll("[^a-zA-Z]", "").length();
        if (latinLetterCount < 2) {
            return false;
        }

        // Проверка наличия хотя бы одной большой латинской буквы
        boolean containsUpperCase = containsUpperCaseLetter(password);
        if (!containsUpperCase) {
            return false;
        }

        // Проверка наличия хотя бы одной латинской буквы и общей длины пароля более 8 символов
        return password.matches(".*[a-zA-Z].*") && password.length() > 8;
    }

    // Метод для проверки наличия хотя бы одной большой латинской буквы
    private boolean containsUpperCaseLetter(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;

            }
        }
        return false;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Удаляем пробелы и символы дефиса из номера телефона
        String normalizedPhoneNumber = phoneNumber.replaceAll("[\\s-]+", "");

        // Проверяем, соответствует ли номер телефона российскому формату
        return normalizedPhoneNumber.matches("^\\+7\\d{10}$") ||
                normalizedPhoneNumber.matches("^\\+8\\d{10}$");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
