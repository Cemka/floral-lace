package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.cartitem.CartItemApi;
import ru.myitschool.florallace.data.api.cartitem.CartItemApiService;
import ru.myitschool.florallace.data.api.order.OrderApiService;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.Order;

public class CartItemRepository {

    public static Call<CartItem> getCartItemById(Long id, Long userId){
        return CartItemApiService.instance().getCartItem(id, userId);
    }

    public static Call<Void> deleteById(Long id){
        return CartItemApiService.instance().deleteCartItem(id);
    }

}
