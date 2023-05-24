package ru.myitschool.florallace.feature.profile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.myitschool.florallace.databinding.FragmentProfileBinding;
import ru.myitschool.florallace.feature.main.ui.MainActivity;
import ru.myitschool.florallace.feature.registration.activity.RegActivity;
import ru.myitschool.florallace.feature.registration.reg.presentation.AuthManager;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        Intent intent = new Intent(requireActivity(), RegActivity.class);
        AuthManager manager = new AuthManager(context);
        binding.exit.setOnClickListener(v -> {
                    manager.setLoggedIn(false);
                    startActivity(intent);
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
