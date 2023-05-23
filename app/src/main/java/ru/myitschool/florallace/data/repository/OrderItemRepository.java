package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.orderitem.OrderItemApiService;
import ru.myitschool.florallace.domain.model.OrderItem;
import ru.myitschool.florallace.feature.ordermaking.entity.InsertReqBody;

public class OrderItemRepository {

    public static Call<List<OrderItem>> getAll(){
        return OrderItemApiService.instance().getAll();
    }

    public static Call<OrderItem> insert(Long orderId,
                           Long productId,
                           Integer quantity){
        return OrderItemApiService.instance().insert(orderId,
                productId,
                quantity);
    }

    public static Call<List<OrderItem>> insertByListProducts(InsertReqBody reqBody,
                                                             Long orderId){
        return OrderItemApiService.instance().insertByListProductId(orderId,
                reqBody);
    }

    public static Call<OrderItem> getById(Long orderId){
        return OrderItemApiService.instance().getById(orderId);
    }

    public static Call<List<OrderItem>> getByUserId (Long userId){
        return OrderItemApiService.instance().getByUserId(userId);
    }
}
