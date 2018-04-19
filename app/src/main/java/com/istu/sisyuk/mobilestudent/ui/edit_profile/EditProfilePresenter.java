package com.istu.sisyuk.mobilestudent.ui.edit_profile;

import android.support.annotation.NonNull;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.models.EditUserParam;
import com.istu.sisyuk.mobilestudent.data.repository.RepositoryImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfilePresenter implements EditProfileContract.Presenter {

    private EditProfileContract.View view;
    private BaseRepository repository;

    public void setView(EditProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void editProfile(String email, final String login, final String password) {
        EditUserParam editUserParam = new EditUserParam(email, login, password);
        view.showProgress(true);
        repository.profile(getToken(), editUserParam, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    setLogin(login);
                    setPassword(password);
                    view.profileChanged();
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
    public String getPassword() {
        return MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .getPassword();
    }

    @Override
    public void setPassword(String password) {
        MobileStudentApplication
                .getComponent()
                .getPreferenceHelper()
                .setPassword(password);
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
