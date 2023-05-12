package ru.myitschool.florallace.data.api.order;

import ru.myitschool.florallace.data.api.RetroFitService;

public class OrderApiService {

    public static OrderApi orderApi;

    public static OrderApi create(){
        return RetroFitService.getInstance().create(OrderApi.class);
    }
    public static OrderApi instance(){
        if(orderApi == null) orderApi = create();
        return orderApi;
    }
}
