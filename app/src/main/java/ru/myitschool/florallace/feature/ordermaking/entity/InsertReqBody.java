package ru.myitschool.florallace.feature.ordermaking.entity;

import java.util.List;

public class InsertReqBody{
    List<Long> cartItemsId;

    public InsertReqBody(List<Long> cartItemsId) {
        this.cartItemsId = cartItemsId;
    }
}
