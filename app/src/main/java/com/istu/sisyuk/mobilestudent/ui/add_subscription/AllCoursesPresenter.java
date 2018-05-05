package com.istu.sisyuk.mobilestudent.ui.add_subscription;

import android.support.annotation.NonNull;

import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseRepository;
import com.istu.sisyuk.mobilestudent.data.models.Course;
import com.istu.sisyuk.mobilestudent.data.repository.RepositoryImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCoursesPresenter implements AllCoursesContract.Presenter {

    private AllCoursesContract.View view;
    private BaseRepository repository;

    public void setView(AllCoursesContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void courses() {
        view.showProgress(true);
        repository.courses(getToken(), new Callback<List<Course>>() {
            @Override
            public void onResponse(@NonNull Call<List<Course>> call,
                                   @NonNull Response<List<Course>> response) {
                view.showProgress(false);
                List<Course> courses = response.body();
                if (response.code() == 200
                        && courses != null) {
                    view.setData(courses);
                    return;
                }
                view.showError(R.string.error_load_subscriptions);
            }

            @Override
            public void onFailure(@NonNull Call<List<Course>> call,
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
