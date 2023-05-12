package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.order.OrderApiService;
import ru.myitschool.florallace.domain.model.Order;

public class OrdersRepository {

    public static Call<List<Order>> getOrders(){
        return OrderApiService.instance().getOrders();
    }

    // todo
}
