package com.istu.sisyuk.mobilestudent.ui.main;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;

public class MainContract {

    public interface View extends BaseView<MainPresenter> {

    }

    public interface Presenter extends BasePresenter {

        String getToken();
    }
}
