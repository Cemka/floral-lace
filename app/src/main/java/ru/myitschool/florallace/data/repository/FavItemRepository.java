package ru.myitschool.florallace.data.repository;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.cartitem.CartItemApiService;
import ru.myitschool.florallace.data.api.favitem.FavItemApiService;
import ru.myitschool.florallace.domain.model.CartItem;
import ru.myitschool.florallace.domain.model.FavItem;

public class FavItemRepository {

    public static Call<FavItem> getFavItemByUserIdAndProductId(Long userId, Long productId){
        return FavItemApiService
                .instance()
                .getFavItemByUserIdAndProductId(userId, productId);
    }

    public static Call<Void> deleteById(Long id){
        return FavItemApiService.instance().deleteFavItem(id);
    }

    public static Call<FavItem> insertFavItem(Long userId,
                                                Long productId) {
        return FavItemApiService.create().insertFavItem(userId,
                productId);
    }
}
