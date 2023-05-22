package ru.myitschool.florallace.feature.ordermaking.presentation;

import java.util.List;

import ru.myitschool.florallace.feature.ordermaking.entity.OrderTime;

public class NoDb {

    public static List<OrderTime> ORDER_TIME_ITEMS;

    public static void loadTime(){
        OrderTime time1 = new OrderTime("30", "minute");
        OrderTime time2 = new OrderTime("1", "hour");
        OrderTime time3 = new OrderTime("2", "hour");
        OrderTime time4 = new OrderTime("15", "minute");
        ORDER_TIME_ITEMS.add(time1);
        ORDER_TIME_ITEMS.add(time2);
        ORDER_TIME_ITEMS.add(time3);
        ORDER_TIME_ITEMS.add(time4);
    }
}
