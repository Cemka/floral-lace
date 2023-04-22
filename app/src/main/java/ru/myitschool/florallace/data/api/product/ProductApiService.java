package ru.myitschool.florallace.data.api.product;

import ru.myitschool.florallace.data.api.RetroFitService;

public class ProductApiService {

    private static ProductApi productApi;

    private static ProductApi create(){
        return RetroFitService.getInstance().create(ProductApi.class);
    }

    public static ProductApi getInstance(){
        if(productApi == null) productApi = create();
        return productApi;
    }
}
