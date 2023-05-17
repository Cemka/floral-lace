package ru.myitschool.florallace.data.api.user;

import ru.myitschool.florallace.data.api.RetroFitService;

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
