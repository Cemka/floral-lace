package ru.myitschool.florallace.data.api.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.myitschool.florallace.domain.model.User;

public interface UserApi {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<User> getUserById(
            @Path("id")
            Long id
    );

    @GET("users/phoneNumb/{phoneNumb}")
    Call<User> getUserByPhoneNum(
            @Path("phoneNumb")
            String phoneNumb
    );

    @PUT("users/{id}")
    Call<User> uploadUser(
            @Path("id")
            Long id,
            @Body User user
    );
}
