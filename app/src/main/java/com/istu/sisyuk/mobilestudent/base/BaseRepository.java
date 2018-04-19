package com.istu.sisyuk.mobilestudent.base;

import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.EditUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;

import retrofit2.Callback;

public interface BaseRepository {

    void signIn(SignInUserParam signInUserParam, Callback<Void> callback);

    void login(AuthUserParam authUserParam, Callback<AuthResponse> callback);

    void profile(String token, EditUserParam editUserParam, Callback<Void> callback);
}
