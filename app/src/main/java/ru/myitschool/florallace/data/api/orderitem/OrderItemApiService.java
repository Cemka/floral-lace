package ru.myitschool.florallace.data.api.orderitem;

import ru.myitschool.florallace.data.api.RetroFitService;

public class OrderItemApiService {

    public static OrderItemApi orderItemApi;

    public static OrderItemApi create(){
        return RetroFitService.getInstance().create(OrderItemApi.class);
    }
    public static OrderItemApi instance(){
        if(orderItemApi == null) orderItemApi = create();
        return orderItemApi;
    }

}
