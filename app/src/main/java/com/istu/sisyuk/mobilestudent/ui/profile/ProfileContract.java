package com.istu.sisyuk.mobilestudent.ui.profile;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;

public class ProfileContract {

    public interface View extends BaseView<ProfilePresenter> {

    }

    public interface Presenter extends BasePresenter {

        String getToken();
    }
}
