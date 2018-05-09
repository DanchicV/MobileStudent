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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String REGISTRATION = "api/registration";
    String AUTHORIZATION = "api/authorization";
    String PROFILE = "api/profile";
    String SUBSCRIPTION = "api/subscriptions";
    String COURSES = "api/courses";
    String COURSE = "api/course";
    String SUBSCRIBE = "api/subscribe/{courseId}";
    String UNSUBSCRIBE = "api/unsubscribe/{courseId}";
    String TOKEN = "X-Auth-key";
    String QUERY_ID = "id";
    String PATH_ID_COURSE = "courseId";

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

    @GET(COURSE)
    Call<List<Course>> course(@Header(TOKEN) String token,
                        @Query(QUERY_ID) long courseId);

    @PUT(SUBSCRIBE)
    Call<Void> subscribe(@Header(TOKEN) String token,
                         @Path(PATH_ID_COURSE) long courseId);

    @PUT(UNSUBSCRIBE)
    Call<Void> unsubscribe(@Header(TOKEN) String token,
                         @Path(PATH_ID_COURSE) long courseId);
}
