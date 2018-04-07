package com.istu.sisyuk.mobilestudent.ui.auth;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.R;
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
        repository.signIn(new SignInUserParam.User(email, login, password), new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    view.switchSignIn(false);
                }
                view.showError(R.string.sign_in_error);
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                view.showProgress(false);
                view.showError(R.string.unknown_error);
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
                view.showError(R.string.login_error);
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                view.showProgress(false);
                view.showError(R.string.unknown_error);
            }
        });
    }

    private void saveToken(String token) {
        MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .setToken(token);
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
