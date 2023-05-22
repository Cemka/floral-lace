package ru.myitschool.florallace.domain.model;

public class OrderItem {

    private Long id;

    private Product product;
    private Long orderId;
    private Integer quantity;

    public OrderItem(Product product, Long orderId, Integer quantity) {
        this.product = product;
        this.orderId = orderId;
        this.quantity = quantity;
    }
}
