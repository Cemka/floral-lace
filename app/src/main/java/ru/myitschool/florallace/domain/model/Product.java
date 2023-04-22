package ru.myitschool.florallace.domain.model;

import androidx.annotation.NonNull;

public class Product {
    private long id;
    private String name;
    private String description;
    private String price;
    private String photoUrl;
    private long count;


    public Product(long id, String name, String description, String price, String photoUrl, long count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.photoUrl = photoUrl;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public long getCount() {
        return count;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
