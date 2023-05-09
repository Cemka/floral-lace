package ru.myitschool.florallace.domain.model;

import androidx.annotation.NonNull;

public class Product {

    private Long id;
    private String name;

    private String description;

    private String photoUrl;

    private Integer price;

    private Integer countLast;

    private Integer countStart;

    private String color;

    public Product(Long id,
                   String name,
                   String description,
                   String photoUrl,
                   Integer price,
                   Integer countLast,
                   Integer countStart,
                   String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.price = price;
        this.countLast = countLast;
        this.countStart = countStart;
        this.color = color;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCountLast() {
        return countLast;
    }

    public Integer getCountStart() {
        return countStart;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", price=" + price +
                ", countLast=" + countLast +
                ", countStart=" + countStart +
                ", color='" + color + '\'' +
                '}';
    }
}
