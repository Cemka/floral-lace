package ru.myitschool.florallace.feature.registration.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ru.myitschool.florallace.R;
import ru.myitschool.florallace.databinding.ActivityRegBinding;
import ru.myitschool.florallace.feature.main.ui.MainActivity;

public class RegActivity extends AppCompatActivity {

    ActivityRegBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_reg);

    }

    public void changeAct(){
        Intent intent = new Intent(RegActivity.this, MainActivity.class);
        startActivity(intent);

    }


}
