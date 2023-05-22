package ru.myitschool.florallace.data.api.favitem;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.FavItem;

public interface FavItemApi {

    @GET("fav_item/user/{userId}/{productId}")
    Call<FavItem> getFavItemByUserIdAndProductId(
            @Path("userId") Long userId,
            @Path("productId") Long productId
    );

    @DELETE("fav_item/{id}")
    Call<Void> deleteFavItem(@Path("id") Long id);

    @POST("fav_item/{user_id}")
    Call<FavItem> insertFavItem(
            @Path("user_id") Long userId,
            @Query("product_id") Long productId
    );
}
