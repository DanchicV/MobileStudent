package com.istu.sisyuk.mobilestudent.ui.subscriptions;

import android.support.annotation.NonNull;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;
import com.istu.sisyuk.mobilestudent.data.repository.RepositoryImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionsPresenter implements SubscriptionsContract.Presenter {

    private SubscriptionsContract.View view;
    private BaseRepository repository;

    public void setView(SubscriptionsContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void subscriptions() {
        view.showProgress(true);
        repository.subscriptions(getToken(), new Callback<List<Subscription>>() {
            @Override
            public void onResponse(@NonNull Call<List<Subscription>> call,
                                   @NonNull Response<List<Subscription>> response) {
                view.showProgress(false);
                List<Subscription> subscriptions = response.body();
                if (response.code() == 200
                        && subscriptions != null) {
                    view.setData(subscriptions);
                    return;
                }
                view.showError(R.string.error_load_subscriptions);
            }

            @Override
            public void onFailure(@NonNull Call<List<Subscription>> call,
                                  @NonNull Throwable t) {
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
