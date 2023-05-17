package ru.myitschool.florallace.feature.cart.ui;

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
import java.util.stream.Collectors;

import ru.myitschool.florallace.databinding.FragmentCartBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.cart.presentation.CartStatus;
import ru.myitschool.florallace.feature.cart.presentation.CartViewModel;
import ru.myitschool.florallace.feature.cart.ui.cartrecycler.CartRecyclerAdapter;
import ru.myitschool.florallace.feature.catalog.ui.dialog.ProductBottomSheetDialog;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    private Long userId = 1L;

    private CartViewModel viewModel;

    private CartRecyclerAdapter adapter;

    private ProductBottomSheetDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding = FragmentCartBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CartRecyclerAdapter(id -> {
            dialog = new ProductBottomSheetDialog(id);
            dialog.show(requireActivity().getSupportFragmentManager(), "product");
        });

        binding.cartRecycler.setAdapter(adapter);
        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.productsHash.observe(getViewLifecycleOwner(), this::renderProducts);
        if(savedInstanceState == null) viewModel.load(userId);
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
        adapter.setProducts(products, productsMap);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
