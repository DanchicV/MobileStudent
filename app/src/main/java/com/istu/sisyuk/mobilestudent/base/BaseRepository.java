package com.istu.sisyuk.mobilestudent.base;

import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.Course;
import com.istu.sisyuk.mobilestudent.data.models.EditUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;

import java.util.List;

import retrofit2.Callback;

public interface BaseRepository {

    void signIn(SignInUserParam signInUserParam, Callback<Void> callback);

    void login(AuthUserParam authUserParam, Callback<AuthResponse> callback);

    void profile(String token, EditUserParam editUserParam, Callback<Void> callback);

    void subscriptions(String token, Callback<List<Subscription>> callback);

    void courses(String token, Callback<List<Course>> callback);
}
