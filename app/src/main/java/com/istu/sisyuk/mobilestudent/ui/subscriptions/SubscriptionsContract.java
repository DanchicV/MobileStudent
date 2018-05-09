package com.istu.sisyuk.mobilestudent.ui.subscriptions;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;

import java.util.List;

public class SubscriptionsContract {

    public interface View extends BaseView<SubscriptionsPresenter> {

        void setData(List<Subscription> subscriptions);
    }

    public interface Presenter extends BasePresenter {

        void subscriptions();

        String getToken();

        void removeToken();

        String getLogin();

        void setLogin(String login);
    }
}
