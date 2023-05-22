package ru.myitschool.florallace.feature.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.florallace.data.repository.CartItemRepository;
import ru.myitschool.florallace.data.repository.FavItemRepository;
import ru.myitschool.florallace.data.repository.ProductsRepository;
import ru.myitschool.florallace.databinding.DialogBottomshetProductBinding;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.FavItem;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.catalog.presentation.dialog.ProductDialogStatus;

public class ProductBottomSheetDialog extends BottomSheetDialogFragment {

    private static final String API_TAG = "API_TAG_PR_DIALOG";
    private DialogBottomshetProductBinding binding;
    private final long id;
    private Product item;
    private ProductDialogStatus status;
    private Long userId;
    private int quantity = 1;

    public ProductBottomSheetDialog(long id) {
        this.id = id;
    }

    public ProductBottomSheetDialog(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DialogBottomshetProductBinding.inflate(inflater);
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        load(id);
        binding.buttonDialogCancel.setOnClickListener(v -> dismiss());
        binding.btnPlus.setOnClickListener(v -> {
            if (quantity < item.getCountLast()) {
                if (quantity == 1) {
                    quantity = item.getCountStart() + 1;
                } else {
                    quantity++;
                }
            } else {
                Toast.makeText(context, "Больше нельзя добавить ", Toast.LENGTH_SHORT).show();
            }
            binding.countStart.setText(Integer.toString(quantity));
        });

        binding.btnMinus.setOnClickListener(v -> {
            if (quantity == 0 || quantity == item.getCountStart()) {
                Toast.makeText(context, "Нельзя добавить меньше", Toast.LENGTH_SHORT).show();
            } else {
                quantity--;
            }
            binding.countStart.setText(Integer.toString(quantity));
        });

        binding.addCartItem.setOnClickListener(v -> insertInCartItem(id,
                quantity,
                userId));

        binding.buttonDialogFavourite.setOnClickListener(v -> insertInFavItem(id,
                userId));

    }

    private void load(long id) {
        status = ProductDialogStatus.LOADING;
        ProductsRepository.getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                status = ProductDialogStatus.LOADED;
                item = response.body();
                renderStatus(status);
                render(item);
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                status = ProductDialogStatus.FAILURE;
                renderStatus(status);
                t.printStackTrace();
            }
        });
    }

    //fixme костыль
    private void insertInCartItem(long productId,
                                  int quantity,
                                  long userId) {

        if (quantity == 0) quantity = item.getCountStart();

        int finalQuantity = quantity;
        CartItemRepository.getCartItemByUserIdAndProductId(userId, productId).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(@NonNull Call<CartItem> call, @NonNull Response<CartItem> response) {
                if (response.body() != null) {
                    Toast.makeText(getContext(), "Товар уже есть в корзине", Toast.LENGTH_SHORT).show();
                } else {
                    CartItemRepository.insertCartItem(userId, productId, finalQuantity).enqueue(new Callback<CartItem>() {
                        @Override
                        public void onResponse(@NonNull Call<CartItem> call, @NonNull Response<CartItem> response) {
                            Log.d(API_TAG, "suc post cart item");
                            dismiss();
                            Toast.makeText(getContext(), "Товар в корзине", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<CartItem> call, @NonNull Throwable t) {
                            Log.d(API_TAG, "suc post cart item");
                            Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }

            @Override
            public void onFailure(@NonNull Call<CartItem> call, @NonNull Throwable t) {

            }
        });

    }

    private void insertInFavItem(long productId, long userId){
        FavItemRepository.getFavItemByUserIdAndProductId(userId, productId).enqueue(new Callback<FavItem>() {
            @Override
            public void onResponse(@NonNull Call<FavItem> call, @NonNull Response<FavItem> response) {
                if (response.body() != null) {
                    Toast.makeText(getContext(), "Товар уже есть в избранных", Toast.LENGTH_SHORT).show();
                } else {
                    FavItemRepository.insertFavItem(userId, productId).enqueue(new Callback<FavItem>() {
                        @Override
                        public void onResponse(@NonNull Call<FavItem> call, @NonNull Response<FavItem> response) {
                            Log.d(API_TAG, "suc post fav item");
                            Toast.makeText(getContext(), "Товар в избранных", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<FavItem> call, @NonNull Throwable t) {
                            Log.d(API_TAG, "suc post fav item");
                            Toast.makeText(getContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<FavItem> call, @NonNull Throwable t) {

            }
        });

    }


    private void renderStatus(ProductDialogStatus status) {
        switch (status) {
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
    public void render(Product item) {
        Glide.with(binding.getRoot()).load(item.getPhotoUrl()).into(binding.photo);
        binding.nameProduct.setText(item.getName());
        binding.price.setText(Long.toString(item.getPrice()));
        if (item.getCountLast() < 20) {
            binding.countLast.setTextColor(Color.RED);
            binding.nameCountLast.setTextColor(Color.RED);
            binding.nameCount.setTextColor(Color.RED);
        }
        binding.countLast.setText(Long.toString(item.getCountLast()));
        binding.countStart.setText(Long.toString(item.getCountStart()));
        binding.description.setText(item.getDescription());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
