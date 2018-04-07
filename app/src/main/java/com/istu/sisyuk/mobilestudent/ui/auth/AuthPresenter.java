package com.istu.sisyuk.mobilestudent.ui.auth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.models.SignInUserParam;
import com.istu.sisyuk.mobilestudent.data.repository.RepositoryImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthPresenter implements AuthContract.Presenter {

    private AuthContract.View view;
    private BaseRepository repository;

    public void setView(AuthContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void signIn(String email, String login, String password) {
        SignInUserParam signInUserParam = new SignInUserParam(new SignInUserParam.User(email, login, password));
        view.showProgress(true);
        repository.signIn(signInUserParam, new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    view.switchSignIn(false);
                }
                view.showError(new Exception());
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                view.showProgress(false);
            }
        });
    }

    @Override
    public void login(String login, String password) {
        AuthUserParam authUserParam = new AuthUserParam(new AuthUserParam.User(login, password));
        view.showProgress(true);
        repository.login(authUserParam, new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                view.showProgress(false);
                AuthResponse authResponse = response.body();
                if (response.code() == 200
                        && authResponse != null
                        && TextUtils.isEmpty(authResponse.getToken())) {
                    saveToken(authResponse.getToken());
                }
                view.showError(new Exception());
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                view.showProgress(false);
                view.showError(t);
            }
        });
    }

    private void saveToken(String token) {
        // TODO: 07.04.2018 shared preferences
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
