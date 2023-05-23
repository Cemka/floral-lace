package ru.myitschool.florallace.data.api.cartitem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.feature.ordermaking.entity.DelReqBody;

public interface CartItemApi {

    @GET("cart_item/user/{userId}/{productId}")
    Call<CartItem> getCartItemByUserIdAndProductId(
            @Path("userId") Long userId,
            @Path("productId") Long productId
    );


    @DELETE("cart_item/{id}")
    Call<Void> deleteCartItem(@Path("id") Long id);

    @HTTP(method = "DELETE", path = "cart_item", hasBody = true)
    Call<Void> deleteAllById(@Body DelReqBody body);


    @POST("cart_item/{user_id}")
    Call<CartItem> insertCartItem(
            @Path("user_id") Long userId,
            @Query("product_id") Long productId,
            @Query("quantity") Integer quantity
    );

}
