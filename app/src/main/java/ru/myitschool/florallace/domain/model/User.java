package ru.myitschool.florallace.domain.model;

import java.util.List;

public class User {

    private Long id;

    private String phoneNumb;

    private String firstName;

    private String secondName;

    private Integer countOfBonus;

    private List<Product> favouriteProducts;

    private List<Product> productsInCart;

    private Order userOrder;

    public User(Long id,
                String phoneNumb,
                String firstName,
                String secondName,
                Integer countOfBonus,
                List<Product> favouriteProducts,
                List<Product> productsInCart,
                Order userOrder) {
        this.id = id;
        this.phoneNumb = phoneNumb;
        this.firstName = firstName;
        this.secondName = secondName;
        this.countOfBonus = countOfBonus;
        this.favouriteProducts = favouriteProducts;
        this.productsInCart = productsInCart;
        this.userOrder = userOrder;
    }

    public User(String phoneNumb,
                String firstName,
                String secondName,
                Integer countOfBonus,
                List<Product> favouriteProducts,
                List<Product> productsInCart,
                Order userOrder) {
        this.phoneNumb = phoneNumb;
        this.firstName = firstName;
        this.secondName = secondName;
        this.countOfBonus = countOfBonus;
        this.favouriteProducts = favouriteProducts;
        this.productsInCart = productsInCart;
        this.userOrder = userOrder;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Integer getCountOfBonus() {
        return countOfBonus;
    }

    public List<Product> getFavouriteProducts() {
        return favouriteProducts;
    }

    public List<Product> getProductsInCart() {
        return productsInCart;
    }

    public Order getUserOrder() {
        return userOrder;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phoneNumb='" + phoneNumb + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", countOfBonus=" + countOfBonus +
                ", favouriteProducts=" + favouriteProducts +
                ", productsInCart=" + productsInCart +
                ", userOrder=" + userOrder +
                '}';
    }
}
