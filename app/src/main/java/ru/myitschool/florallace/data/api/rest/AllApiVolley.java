package ru.myitschool.florallace.data.api.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.myitschool.florallace.data.api.nodb.NoDb;
import ru.myitschool.florallace.domain.mapper.ProductMapper;
import ru.myitschool.florallace.domain.mapper.UserMapper;
import ru.myitschool.florallace.domain.model.Product;
import ru.myitschool.florallace.domain.model.User;
import ru.myitschool.florallace.feature.catalog.presentation.catalog.CatalogViewModel;

public class AllApiVolley implements AllApi{

    public static final String API_TEST = "API_TEST";
    private final Context context;
    public static final String BASE_URL = "http://192.168.1.5:8080";

    private Response.ErrorListener errorListener;

    public AllApiVolley(Context context) {
        this.context = context;
        errorListener = error -> Log.d(API_TEST, error.toString());
    }

    @Override
    public void fillProduct() {

        String url = BASE_URL + "/product";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        NoDb.PRODUCT_LIST.clear();

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);
                                Product product = ProductMapper.productFromJson(jsonObject);
                                NoDb.PRODUCT_LIST.add(product);
                                Log.d(API_TEST, product.toString());
                            }

                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, errorListener
        );

        requestQueue.add(arrayRequest);

    }

    @Override
    public void fillUser() {
        String url = BASE_URL + "/product";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        NoDb.USER_LIST.clear();

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);
                                User user = UserMapper.userFromJson(jsonObject);
                                NoDb.USER_LIST.add(user);
                                Log.d(API_TEST, NoDb.USER_LIST.toString());
                            }

                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, errorListener
        );

        requestQueue.add(arrayRequest);

    }

    @Override
    public void fillOrder() {

    }
}
