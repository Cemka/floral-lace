package ru.myitschool.florallace.domain.mapper;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;

public class UserMapper {

    @NonNull
    public static User userFromJson(JSONObject jsonObject) throws JSONException {
        User user;

        List<Product> favProductsId = fromJsonArrayToProductArray("favouriteProducts", jsonObject);
        List<Product> cartProductsId = fromJsonArrayToProductArray("productsInCart", jsonObject);

        user = new User(
                jsonObject.getLong("id"),
                jsonObject.getString("phoneNumb"),
                jsonObject.getString("firstName"),
                jsonObject.getString("secondName"),
                jsonObject.getInt("countOfBonus"),
                favProductsId,
                cartProductsId,
                OrderMapper.orderFromJson(jsonObject.getJSONObject("userOrder")));
        return user;
    }

    @NonNull
    private static List<Product> fromJsonArrayToProductArray(String fieldName, @NonNull JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            productList.add(ProductMapper.productFromJson(jsonArray.getJSONObject(i)));
        }
        return productList;
    }
}
