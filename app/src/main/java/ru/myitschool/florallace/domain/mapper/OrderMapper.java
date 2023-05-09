package ru.myitschool.florallace.domain.mapper;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.myitschool.florallace.domain.model.Order;
import ru.myitschool.florallace.domain.model.Product;

public class OrderMapper {

    public static Order orderFromJson(JSONObject jsonObject) throws JSONException {
        Order order;

        List<Product> productList = fromJsonArrayToProductArray("productList", jsonObject);

        order = new Order(
                jsonObject.getLong("id"),
                jsonObject.getLong("userId"),
                productList,
                jsonObject.getInt("price"),
                jsonObject.getString("location"),
                jsonObject.getString("time"));

        return order;
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
