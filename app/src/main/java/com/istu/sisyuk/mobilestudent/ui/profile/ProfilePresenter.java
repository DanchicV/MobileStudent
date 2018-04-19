package com.istu.sisyuk.mobilestudent.ui.profile;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;

    public void setView(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
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
