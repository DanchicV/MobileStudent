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

    public void profile(String login, String password) {
        AuthUserParam authUserParam = new AuthUserParam(login, password);
        view.showProgress(true);
        repository.profile(authUserParam, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                view.showProgress(false);
                if (response.code() == 200) {

                    return;
                }
                view.showError(response.message());
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
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
    public void removeToken() {
        MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .setToken(null);
    }

    @Override
    public String getLogin() {
        return MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .getLogin();
    }

    @Override
    public void setLogin(String login) {
        MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .setLogin(login);
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
