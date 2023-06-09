package ru.myitschool.florallace.data.repository;

import java.util.List;

import retrofit2.Call;
import ru.myitschool.florallace.data.api.user.UserApiService;
import ru.myitschool.florallace.domain.model.User;

public class UsersRepository {

    public static Call<List<User>> getUsers(){
        return UserApiService.getInstance().getUsers();
    }

    public static Call<User> getUserById(Long id){
        return UserApiService.getInstance().getUserById(id);
    }

    public static Call<User> getUsersByPhoneNumb(String phoneNumb){
        return UserApiService.getInstance().getUserByPhoneNum(phoneNumb);
    }

    public static Call<User> updateUser(Long id, User user){
        return UserApiService.getInstance().uploadUser(id, user);
    }

    public static Call<User> getUserByPasAndPhoneNumb(String password, String phoneNumb){
        return UserApiService.getInstance().getUserByPasAndPhoneNumb(password, phoneNumb);
    }

    public static Call<User> insert(String phoneNumb,
                                    String firstName,
                                    String secondName,
                                    String countBonus,
                                    String password){
        return UserApiService.getInstance().insert(phoneNumb,
                firstName, secondName,
                countBonus, password);
    }
}
