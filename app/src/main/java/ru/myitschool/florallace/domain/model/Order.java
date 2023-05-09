package ru.myitschool.florallace.domain.model;

import java.util.List;

public class Order {

    private Long id;

    private Long userId;

    private List<Product> productList;

    private Integer price;

    private String location;

    private String time;

    public Order(Long id,
                 Long userId,
                 List<Product> productList,
                 Integer price,
                 String location,
                 String time) {
        this.id = id;
        this.userId = userId;
        this.productList = productList;
        this.price = price;
        this.location = location;
        this.time = time;
    }

    public Order(Long userId,
                 List<Product> productList,
                 Integer price,
                 String location,
                 String time) {
        this.userId = userId;
        this.productList = productList;
        this.price = price;
        this.location = location;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Integer getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }
}
