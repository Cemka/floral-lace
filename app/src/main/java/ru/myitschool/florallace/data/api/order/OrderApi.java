package ru.myitschool.florallace.data.api.order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.myitschool.florallace.domain.model.Order;

public interface OrderApi {

    @GET("orders")
    Call<List<Order>> getOrders();

    @GET("orders/location/{location}")
    Call<List<Order>> getOrdersByLocation(
            @Path("location")
            String location
    );

    @GET("orders/time/{time}")
    Call<List<Order>> getOrdersByTime(
            @Path("time")
            String time
    );

    @GET("orders/{id}")
    Call<Order> getOrderById(
            @Path("id")
            Long id
    );

    @GET("orders/user/{user_id}")
    Call<Order> getByUserId(@Path("user_id") Long userId);

    @POST("orders")
    Call<Order> insert(@Body Order order);

    @PUT("orders/{id}")
    Call<Order> updateById(@Path("id") Long orderId,
                       @Body Order order);
}
