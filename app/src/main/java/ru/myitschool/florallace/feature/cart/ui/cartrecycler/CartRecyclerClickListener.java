package ru.myitschool.florallace.feature.cart.ui.cartrecycler;

public interface CartRecyclerClickListener {
    void onClick(Long id);

    void onDeleteClick(int id);

    void onFavClick(int id);
}
