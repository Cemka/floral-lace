package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.product.ProductApiService;
import ru.myitschool.florallace.domain.model.Product;

public class ProductsRepository {


    public static Call<List<Product>> getProducts(){
        return ProductApiService.getInstance().getProducts();
    }

    public static Call<Product> getProduct(long id){
        return ProductApiService.getInstance().getProduct(id);
    }

    public static Call<Void> deleteProduct(long id){
        return ProductApiService.getInstance().deleteProduct(id);
    }


}
