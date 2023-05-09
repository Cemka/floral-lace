package ru.myitschool.florallace.feature.catalog.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.ProductsRepository;
import ru.myitschool.florallace.databinding.DialogBottomshetProductBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.catalog.presentation.dialog.ProductDialogStatus;

public class ProductBottomSheetDialog extends BottomSheetDialogFragment {

    private DialogBottomshetProductBinding binding;
    private long id;
    private Product item;
    private ProductDialogStatus status;

    public ProductBottomSheetDialog(long id){
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DialogBottomshetProductBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load(id);
        binding.buttonDialogCancel.setOnClickListener(v -> dismiss());
    }

    public void load(long id){
        status = ProductDialogStatus.LOADING;
       /* ProductsRepository.getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                status = ProductDialogStatus.LOADED;
                item = response.body();
                renderStatus(status);
                render(item);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                status = ProductDialogStatus.FAILURE;
                renderStatus(status);
                t.printStackTrace();
            }
        });*/
    }


    private void renderStatus(ProductDialogStatus status){
        switch (status){
            case LOADING:
                binding.layoutPhoto.setVisibility(View.INVISIBLE);
                binding.bottomPart.setVisibility(View.INVISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.VISIBLE);
                break;
            case LOADED:
                binding.layoutPhoto.setVisibility(View.VISIBLE);
                binding.bottomPart.setVisibility(View.VISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
            case FAILURE:
                binding.layoutPhoto.setVisibility(View.INVISIBLE);
                binding.bottomPart.setVisibility(View.INVISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void render(Product item){
        Glide.with(binding.getRoot()).load(item.getPhotoUrl()).into(binding.photo);
        binding.nameProduct.setText(item.getName());
        binding.price.setText(item.getPrice());
        binding.countLast.setText(Long.toString(item.getCountLast()));
        binding.description.setText(item.getDescription());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}
