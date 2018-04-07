package com.istu.sisyuk.mobilestudent.data.api;

import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    String REGISTRATION = "api/registration";
    String AUTHORIZATION = "api/authorization";

    @POST(REGISTRATION)
    Call<Object> signIn(@Body SignInUserParam device);

    @POST(AUTHORIZATION)
    Call<AuthResponse> login(@Body AuthUserParam device);
}
