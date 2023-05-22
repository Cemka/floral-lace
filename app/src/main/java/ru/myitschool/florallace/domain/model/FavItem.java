package ru.myitschool.florallace.domain.model;

import java.util.Objects;

public class FavItem {

    private Long id;

    private Product product;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavItem favItem = (FavItem) o;
        return Objects.equals(id, favItem.id) && Objects.equals(product, favItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
