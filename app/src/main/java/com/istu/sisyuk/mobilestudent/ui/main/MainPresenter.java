package com.istu.sisyuk.mobilestudent.ui.main;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public void setView(MainContract.View view) {
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
    public void unsubscribe() {
        view = null;
    }
}
