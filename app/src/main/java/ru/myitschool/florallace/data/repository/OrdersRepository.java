package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.order.OrderApiService;
import ru.myitschool.florallace.domain.model.Order;

public class OrdersRepository {

    public static Call<List<Order>> getOrders(){
        return OrderApiService.instance().getOrders();
    }

    public static Call<List<Order>> getOrdersByLocation(String location){
        return OrderApiService.instance().getOrdersByLocation(location);
    }

    public static Call<List<Order>> getOrdersByTime(String time){
        return OrderApiService.instance().getOrdersByTime(time);
    }

    public static Call<Order> getOrderById(Long id){
        return OrderApiService.instance().getOrderById(id);
    }

    public static Call<Order> insert(Order order){
        return OrderApiService.instance().insert(order);
    }

    public static Call<Order> updateById(Long orderId,
                                         Order order){
        return OrderApiService.instance().updateById(orderId, order);
    }

    public static Call<Order> getByUserId(Long userId){
        return OrderApiService.instance().getByUserId(userId);
    }
}
