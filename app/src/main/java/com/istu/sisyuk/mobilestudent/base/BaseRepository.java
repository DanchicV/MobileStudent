package com.istu.sisyuk.mobilestudent.base;

import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;

import retrofit2.Callback;

public interface BaseRepository {

    void signIn(SignInUserParam signInUserParam, Callback<Object> callback);

    void login(AuthUserParam authUserParam, Callback<AuthResponse> callback);
}
