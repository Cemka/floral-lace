package ru.myitschool.florallace.data.api.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import ru.myitschool.florallace.data.api.RetroFitService;
import ru.myitschool.florallace.domain.model.User;

public class UserApiService {

    private static UserApi userApi;

    private static UserApi create(){
        return RetroFitService.getInstance().create(UserApi.class);
    }

    public static UserApi getInstance(){
        if(userApi == null) userApi = create();
        return userApi;
    }
}
