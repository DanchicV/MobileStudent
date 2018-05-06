package com.istu.sisyuk.mobilestudent.ui.course_info;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;
import com.istu.sisyuk.mobilestudent.data.models.Course;

public class CourseInfoContract {

    public interface View extends BaseView<CourseInfoPresenter> {

        void setData(Course course);

        void setSubscribe(boolean isSubscribe);
    }

    public interface Presenter extends BasePresenter {

        void course(long courseId);

        void subscribeToCourse(long courseId);

        void unsubscribeToCourse(long courseId);

        String getToken();

        void removeToken();

        String getLogin();

        void setLogin(String login);
    }
}
