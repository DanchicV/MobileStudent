package com.istu.sisyuk.mobilestudent.data.api;

import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.Course;
import com.istu.sisyuk.mobilestudent.data.models.EditUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    String REGISTRATION = "api/registration";
    String AUTHORIZATION = "api/authorization";
    String PROFILE = "api/profile";
    String SUBSCRIPTION = "api/subscriptions";
    String COURSES = "api/courses";
    String SUBSCRIBE = "api/subscribe/{courseId}";
    String TOKEN = "X-Auth-key";

    @POST(REGISTRATION)
    Call<Void> signIn(@Body SignInUserParam user);

    @POST(AUTHORIZATION)
    Call<AuthResponse> login(@Body AuthUserParam user);

    @POST(PROFILE)
    Call<Void> profile(@Header(TOKEN) String token,
                       @Body EditUserParam user);

    @GET(SUBSCRIPTION)
    Call<List<Subscription>> subscriptions(@Header(TOKEN) String token);

    @GET(COURSES)
    Call<List<Course>> courses(@Header(TOKEN) String token);

    @POST(SUBSCRIBE)
    Call<Void> subscribe(@Header(TOKEN) String token,
                         @Path("courseId") long courseId);
}
