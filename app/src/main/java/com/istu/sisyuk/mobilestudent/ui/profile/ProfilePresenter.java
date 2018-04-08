package com.istu.sisyuk.mobilestudent.ui.profile;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.models.AuthResponse;
import com.istu.sisyuk.mobilestudent.data.models.AuthUserParam;
import com.istu.sisyuk.mobilestudent.data.repository.RepositoryImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;
    private BaseRepository repository;

    public void setView(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    public void login(String login, String password) {
        AuthUserParam authUserParam = new AuthUserParam(login, password);
        view.showProgress(true);
        repository.login(authUserParam, new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                view.showProgress(false);
                AuthResponse authResponse = response.body();
                if (response.code() == 200
                        && authResponse != null
                        && !TextUtils.isEmpty(authResponse.getToken())) {

                    return;
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

    @Override
    public String getToken() {
        return MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .getToken();
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
