package ru.myitschool.florallace.domain.mapper;

import org.json.JSONException;
import org.json.JSONObject;

import ru.myitschool.florallace.domain.model.Product;

public class ProductMapper {

    public static Product productFromJson(JSONObject jsonObject) {
        Product product = null;

        try {
            product = new Product(
                    jsonObject.getLong("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("description"),
                    jsonObject.getString("photoUrl"),
                    jsonObject.getInt("price"),
                    jsonObject.getInt("countLast"),
                    jsonObject.getInt("countStart"),
                    jsonObject.getString("color")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }
}
