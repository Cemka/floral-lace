package ru.myitschool.florallace.data.api.cartitem;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.myitschool.florallace.domain.model.CartItem;

public interface CartItemApi {

    @GET("cart_item/user/{userId}/{productId}")
    Call<CartItem> getCartItem(
            @Path("userId") Long userId,
            @Path("productId") Long productId
    );

    @DELETE("cart_item/{id}")
    Call<Void> deleteCartItem(@Path("id") Long id);

}
