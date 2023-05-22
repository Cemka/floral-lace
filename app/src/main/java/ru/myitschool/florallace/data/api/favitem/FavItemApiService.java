package ru.myitschool.florallace.data.api.favitem;

import ru.myitschool.florallace.data.api.RetroFitService;
import ru.myitschool.florallace.data.api.cartitem.CartItemApi;

public class FavItemApiService {
    public static FavItemApi favItemApi;

    public static FavItemApi create(){
        return RetroFitService.getInstance().create(FavItemApi.class);
    }
    public static FavItemApi instance(){
        if(favItemApi == null) favItemApi = create();
        return favItemApi;
    }
}
