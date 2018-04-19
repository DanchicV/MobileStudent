package com.istu.sisyuk.mobilestudent.ui.edit_profile;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;

public class EditProfileContract {

    public interface View extends BaseView<EditProfilePresenter> {

        void profileChanged();
    }

    public interface Presenter extends BasePresenter {

        void editProfile(String email, String login, String password);

        String getToken();

        void removeToken();

        String getLogin();

        void setLogin(String login);

        String getPassword();

        void setPassword(String password);
    }
}
