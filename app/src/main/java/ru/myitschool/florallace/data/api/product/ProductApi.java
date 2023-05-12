package ru.myitschool.florallace.data.api.product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public interface ProductApi {

    @GET("product")
    Call<List<Product>> getProducts();

    @GET("product/{id}")
    Call<Product> getProduct(
            @Path("id")
            long id
    );

    @GET("product/color/{color}")
    Call<List<Product>> getProductsByColor(
            @Path("color")
            String color
    );

    @GET("product/name/{name}")
    Call<Product> getProductByName(
            @Path("name")
            String name
    );


}