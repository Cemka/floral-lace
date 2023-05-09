package ru.myitschool.florallace.data.api.nodb;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.domain.model.Order;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class NoDb {
    private NoDb(){}

    public static final List<Product> PRODUCT_LIST = new ArrayList<>();
    public static final List<User> USER_LIST = new ArrayList<>();
    public static final List<Order> ORDER_LIST = new ArrayList<>();
    
}
