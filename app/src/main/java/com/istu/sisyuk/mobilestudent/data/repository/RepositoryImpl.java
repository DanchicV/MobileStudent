package com.istu.sisyuk.mobilestudent.data.repository;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.api.ApiService;
import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;

import retrofit2.Callback;

public class RepositoryImpl implements BaseRepository {

    private ApiService apiService;

    public RepositoryImpl() {
        this.apiService = MobileStudentApplication.getComponent().getApiService();
    }

    @Override
    public void signIn(SignInUserParam.User signInUserParam, Callback<Void> callback) {
        apiService.signIn(signInUserParam).enqueue(callback);
    }

    @Override
    public void login(AuthUserParam.User authUserParam, Callback<AuthResponse> callback) {
        apiService.login(authUserParam).enqueue(callback);
    }
}
