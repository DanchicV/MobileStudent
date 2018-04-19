package com.istu.sisyuk.mobilestudent.data.api;

import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.EditUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    String REGISTRATION = "api/registration";
    String AUTHORIZATION = "api/authorization";
    String PROFILE = "api/profile";
    String TOKEN = "X-Auth-key";

    @POST(REGISTRATION)
    Call<Void> signIn(@Body SignInUserParam user);

    @POST(AUTHORIZATION)
    Call<AuthResponse> login(@Body AuthUserParam user);

    @POST(PROFILE)
    Call<Void> profile(@Header(TOKEN) String token,
                       @Body EditUserParam user);
}
