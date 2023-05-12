package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.product.ProductApiService;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class ProductsRepository {


    public static Call<List<Product>> getProducts(){
        return ProductApiService.getInstance().getProducts();
    }

    public static Call<Product> getProduct(Long id){
        return ProductApiService.getInstance().getProduct(id);
    }

    public static Call<List<Product>> getProductsByColor(String color){
        return ProductApiService.getInstance().getProductsByColor(color);
    }

    public static Call<Product> getProductByName(String name){
        return ProductApiService.getInstance().getProductByName(name);
    }
}
