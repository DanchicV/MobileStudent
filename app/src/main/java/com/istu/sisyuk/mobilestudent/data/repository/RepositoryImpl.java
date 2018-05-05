package com.istu.sisyuk.mobilestudent.data.repository;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.api.ApiService;
import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.Course;
import com.istu.sisyuk.mobilestudent.data.models.EditUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;

import java.util.List;

import retrofit2.Callback;

public class RepositoryImpl implements BaseRepository {

    private ApiService apiService;

    public RepositoryImpl() {
        this.apiService = MobileStudentApplication.getComponent().getApiService();
    }

    @Override
    public void signIn(SignInUserParam signInUserParam, Callback<Void> callback) {
        apiService.signIn(signInUserParam).enqueue(callback);
    }

    @Override
    public void login(AuthUserParam authUserParam, Callback<AuthResponse> callback) {
        apiService.login(authUserParam).enqueue(callback);
    }

    @Override
    public void profile(String token, EditUserParam editUserParam, Callback<Void> callback) {
        apiService.profile(token, editUserParam).enqueue(callback);
    }

    @Override
    public void subscriptions(String token, Callback<List<Subscription>> callback) {
        apiService.subscriptions(token).enqueue(callback);
    }

    @Override
    public void courses(String token, Callback<List<Course>> callback) {
        apiService.courses(token).enqueue(callback);
    }
}
