package ru.myitschool.florallace.data.api.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.myitschool.florallace.domain.model.User;

public interface UserApi {

    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> insert(@Query("phone_numb") String phoneNumber,
                      @Query("first_name") String firstName,
                      @Query("second_name") String secondName,
                      @Query("count_of_bonus") String bonusCount,
                      @Query("password") String password);

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

    @GET("users/password")
    Call<User> getUserByPasAndPhoneNumb(@Query("password") String password,
                                        @Query("phone_numb") String phoneNumb);


}
