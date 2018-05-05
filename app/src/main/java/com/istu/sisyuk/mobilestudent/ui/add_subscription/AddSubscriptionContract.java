package com.istu.sisyuk.mobilestudent.ui.add_subscription;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;
import com.istu.sisyuk.mobilestudent.data.models.Course;

import java.util.List;

public class AddSubscriptionContract {

    public interface View extends BaseView<AddSubscriptionPresenter> {

        void setData(List<Course> subscriptions);
    }

    public interface Presenter extends BasePresenter {

        void courses();

        String getToken();

        void removeToken();

        String getLogin();

        void setLogin(String login);
    }
}
