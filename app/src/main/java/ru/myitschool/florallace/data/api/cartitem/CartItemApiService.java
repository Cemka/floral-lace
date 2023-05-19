package ru.myitschool.florallace.data.api.cartitem;

import ru.myitschool.florallace.data.api.RetroFitService;

public class CartItemApiService {

    public static CartItemApi cartItemApi;

    public static CartItemApi create(){
        return RetroFitService.getInstance().create(CartItemApi.class);
    }
    public static CartItemApi instance(){
        if(cartItemApi == null) cartItemApi = create();
        return cartItemApi;
    }

}
