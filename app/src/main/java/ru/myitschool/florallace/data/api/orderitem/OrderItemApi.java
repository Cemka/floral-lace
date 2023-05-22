package ru.myitschool.florallace.data.api.orderitem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.myitschool.florallace.domain.model.OrderItem;
import ru.myitschool.florallace.feature.ordermaking.entity.InsertReqBody;

public interface OrderItemApi {

    @GET("order_item")
    Call<List<OrderItem>> getAll();

    @GET("order_item/{id}")
    Call<OrderItem> getById(@Path("id") Long orderId);

    @POST("order_item/{order_id}/{product_id}/{quantity}")
    Call<OrderItem> insert(@Path("order_id") Long orderId,
                           @Query("product_id") Long productId,
                           @Query("quantity") Integer quantity);

    @POST("order_item/product/{order_id}")
    Call<List<OrderItem>> insertByListProductId(@Path("order_id") Long oderId,
                                                @Body InsertReqBody insertReqBody);

}
