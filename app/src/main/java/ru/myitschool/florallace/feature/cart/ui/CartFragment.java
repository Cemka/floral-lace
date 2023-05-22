package ru.myitschool.florallace.feature.cart.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.myitschool.florallace.databinding.FragmentCartBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.cart.presentation.CartStatus;
import ru.myitschool.florallace.feature.cart.presentation.CartViewModel;
import ru.myitschool.florallace.feature.cart.presentation.FavListViewModel;
import ru.myitschool.florallace.feature.cart.ui.cartrecycler.CartRecyclerAdapter;
import ru.myitschool.florallace.feature.cart.ui.cartrecycler.CartRecyclerClickListener;
import ru.myitschool.florallace.feature.cart.ui.favouriterecycler.FavouriteRecyclerAdapter;
import ru.myitschool.florallace.feature.cart.ui.favouriterecycler.FavouriteRecyclerClickListener;
import ru.myitschool.florallace.feature.dialog.ProductBottomSheetDialog;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private Long userId = 1L;
    private CartViewModel cartViewModel;
    private FavListViewModel favListViewModel;
    private CartRecyclerAdapter cartAdapter;
    private FavouriteRecyclerAdapter favAdapter;

    private ProductBottomSheetDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        favListViewModel = new ViewModelProvider(this).get(FavListViewModel.class);

        binding = FragmentCartBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CartRecyclerClickListener listener = new CartRecyclerClickListener() {
            @Override
            public void onClick(Long id) {
                dialog = new ProductBottomSheetDialog(id, userId);
                dialog.show(requireActivity().getSupportFragmentManager(), "product");
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDeleteClick(int id) {
                cartViewModel.deleteCartItem(id, userId);
                // Удалить элемент из списка данных
                cartAdapter.removeProduct(id);
                // Обновить список
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFavClick(int id) {
                cartViewModel.postFavItem(userId, id, getContext());
            }
        };
        cartAdapter = new CartRecyclerAdapter(listener);

        FavouriteRecyclerClickListener favouriteClickListener = new FavouriteRecyclerClickListener() {
            @Override
            public void onClick(Long id) {
                dialog = new ProductBottomSheetDialog(id, userId);
                dialog.show(requireActivity().getSupportFragmentManager(), "favProduct");
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDeleteClick(int id) {
                favListViewModel.deleteFromFavList((long) id, userId);
                favAdapter.removeProduct(id);
                favAdapter.notifyDataSetChanged();
            }
        };

        favAdapter = new FavouriteRecyclerAdapter(favouriteClickListener);

        binding.cartRecycler.setAdapter(cartAdapter);
        binding.favouriteRecycler.setAdapter(favAdapter);
        cartViewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        cartViewModel.productsHash.observe(getViewLifecycleOwner(), this::renderProducts);
        cartViewModel.allPrice.observe(getViewLifecycleOwner(), this::setAllPrice);
        favListViewModel.favProducts.observe(getViewLifecycleOwner(), this::renderFavProducts);
        cartViewModel.load(userId);
        favListViewModel.load(userId);
    }

    @SuppressLint("SetTextI18n")
    private void setAllPrice(Integer integer) {
        binding.sumEnd.setText(Integer.toString(integer));
    }

    private void renderStatus(CartStatus status){
        switch (status) {
            case LOADING:
                binding.cartRecycler.setVisibility(View.INVISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.VISIBLE);
                break;
            case LOADED:
                binding.cartRecycler.setVisibility(View.VISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
            case FAILURE:
                binding.cartRecycler.setVisibility(View.INVISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void renderProducts(HashMap<Product, Integer> productsMap){
        List<Product> products = new ArrayList<>(productsMap.keySet());
        binding.empty.setVisibility(products.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        cartAdapter.setProducts(products, productsMap);
    }

    private void renderFavProducts(List<Product> favProducts){
        favAdapter.setProducts(favProducts);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
