package ru.myitschool.florallace.feature.catalog.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.databinding.FragmentCatalogBinding;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.feature.catalog.presentation.catalog.CatalogStatus;
import ru.myitschool.florallace.feature.catalog.presentation.catalog.CatalogViewModel;
import ru.myitschool.florallace.feature.dialog.ProductBottomSheetDialog;
import ru.myitschool.florallace.feature.catalog.ui.recycler.CatalogAdapter;
import ru.myitschool.florallace.feature.main.ui.MainActivity;
import ru.myitschool.florallace.feature.ordermaking.entity.NoDb;

public class CatalogFragment extends Fragment {

    CatalogViewModel viewModel;
    private FragmentCatalogBinding binding;
    private CatalogAdapter adapter;
    private ProductBottomSheetDialog dialog;
    private List<Product> allProducts;
    private final static Long USER_ID = MainActivity.USER_ID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class);
        binding = FragmentCatalogBinding.inflate(inflater);
        NoDb.TIME = null;
        NoDb.NAME_TIME = null;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CatalogAdapter(id -> {
            dialog = new ProductBottomSheetDialog(id, USER_ID);
            dialog.show(requireActivity().getSupportFragmentManager(), "product");

        });

        binding.search.setOnQueryTextListener(new OnQueryTextListener() {
                                                  @Override
                                                  public boolean onQueryTextSubmit(String query) {
                                                      performSearch(query);
                                                      return true;
                                                  }

                                                  @Override
                                                  public boolean onQueryTextChange(String newText) {
                                                      performSearch(newText);
                                                      return true;
                                                  }
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
        allProducts = products;
        binding.empty.setVisibility(products.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        adapter.setProducts(products);
    }

    private void performSearch(String query) {
        if (allProducts == null || allProducts.isEmpty()) {
            return;
        }

        List<Product> searchResults = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(product);
            }
        }

        adapter.setProducts(searchResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
