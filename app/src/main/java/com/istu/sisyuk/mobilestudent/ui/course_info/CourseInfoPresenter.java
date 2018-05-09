package com.istu.sisyuk.mobilestudent.ui.course_info;

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

public class CourseInfoPresenter implements CourseInfoContract.Presenter {

    private CourseInfoContract.View view;
    private BaseRepository repository;

    public void setView(CourseInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void course(long courseId) {
        view.showProgress(true);
        repository.course(getToken(), courseId, new Callback<List<Course>>() {
            @Override
            public void onResponse(@NonNull Call<List<Course>> call,
                                   @NonNull Response<List<Course>> response) {
                view.showProgress(false);
                List<Course> courses = response.body();
                if (response.code() == 200
                        && courses != null
                        && !courses.isEmpty()) {
                    view.setData(courses.get(0));
                    return;
                }
                view.showError(R.string.error_load_course);
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
    public void subscribeToCourse(long courseId) {
        view.showProgress(true);
        repository.subscribe(getToken(), courseId, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    view.setSubscribe(true);
                    return;
                }
                view.showError(R.string.error_load_course);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
                                  @NonNull Throwable t) {
                view.showProgress(false);
                view.showError(R.string.unknown_error);
            }
        });
    }

    @Override
    public void unsubscribeToCourse(long courseId) {
        view.showProgress(true);
        repository.unsubscribe(getToken(), courseId, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call,
                                   @NonNull Response<Void> response) {
                view.showProgress(false);
                if (response.code() == 200) {
                    view.setSubscribe(false);
                    return;
                }
                view.showError(R.string.error_load_course);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call,
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
