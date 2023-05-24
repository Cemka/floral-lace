package ru.myitschool.florallace.feature.catalog.presentation.catalog;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.ProductsRepository;
import ru.myitschool.florallace.data.repository.UsersRepository;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.cart.presentation.CartStatus;

public class CatalogViewModel extends ViewModel {

    public final MutableLiveData<CatalogStatus> _status = new MutableLiveData<>();
    public LiveData<CatalogStatus> status = _status;

    public final MutableLiveData<List<Product>> _products = new MutableLiveData<>();
    public LiveData<List<Product>> products = _products;

    public void load(){
        _status.setValue(CatalogStatus.LOADING);
        ProductsRepository.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                _status.setValue(CatalogStatus.LOADED);
                _products.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable throwable) {
                _status.setValue(CatalogStatus.FAILURE);
                throwable.printStackTrace();
            }
        });




    }
}
