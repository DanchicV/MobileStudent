package com.istu.sisyuk.mobilestudent.ui.auth;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;

public class AuthContract {

    public interface View extends BaseView<AuthPresenter> {

        void switchSignIn(boolean isSignIn);

        void loginSuccess();
    }

    public interface Presenter extends BasePresenter {

        void signIn(String email, String login, String password);

        void login(String login, String password);
    }
}
