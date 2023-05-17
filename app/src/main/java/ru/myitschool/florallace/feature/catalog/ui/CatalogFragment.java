package ru.myitschool.florallace.feature.catalog.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.myitschool.florallace.databinding.FragmentCatalogBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.catalog.presentation.catalog.CatalogStatus;
import ru.myitschool.florallace.feature.catalog.presentation.catalog.CatalogViewModel;
import ru.myitschool.florallace.feature.catalog.ui.dialog.ProductBottomSheetDialog;
import ru.myitschool.florallace.feature.catalog.ui.recycler.CatalogAdapter;
import ru.myitschool.florallace.feature.catalog.ui.recycler.CatalogClickListener;

public class CatalogFragment extends Fragment {

    CatalogViewModel viewModel;
    private FragmentCatalogBinding binding;
    private CatalogAdapter adapter;
    private ProductBottomSheetDialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class);
        binding = FragmentCatalogBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CatalogAdapter(id -> {
            dialog = new ProductBottomSheetDialog(id);
            dialog.show(requireActivity().getSupportFragmentManager(), "product");

        });


        binding.recyclerCatalog.setAdapter(adapter);
        viewModel.status.observe(getViewLifecycleOwner(), this::renderStatus);
        viewModel.products.observe(getViewLifecycleOwner(), this::renderProducts);
        if(savedInstanceState == null) viewModel.load();

    }

    private void renderStatus(CatalogStatus status){
        switch (status) {
            case LOADING:
                binding.recyclerCatalog.setVisibility(View.INVISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.VISIBLE);
                break;
            case LOADED:
                binding.recyclerCatalog.setVisibility(View.VISIBLE);
                binding.error.setVisibility(View.INVISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
            case FAILURE:
                binding.recyclerCatalog.setVisibility(View.INVISIBLE);
                binding.empty.setVisibility(View.INVISIBLE);
                binding.error.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void renderProducts(List<Product> products){
        binding.empty.setVisibility(products.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        adapter.setProducts(products);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
