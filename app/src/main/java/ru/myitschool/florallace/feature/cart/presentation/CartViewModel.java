package ru.myitschool.florallace.feature.cart.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.myitschool.florallace.domain.model.User;

public class CartViewModel extends ViewModel {
    private final MutableLiveData<CartStatus> _status = new MutableLiveData<>();
    public LiveData<CartStatus> status = _status;

    private final MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;


}
