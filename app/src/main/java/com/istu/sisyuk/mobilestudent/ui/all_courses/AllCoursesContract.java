package com.istu.sisyuk.mobilestudent.ui.all_courses;

import com.istu.sisyuk.mobilestudent.base.BasePresenter;
import com.istu.sisyuk.mobilestudent.base.BaseView;
import com.istu.sisyuk.mobilestudent.data.models.Course;

import java.util.List;

public class AllCoursesContract {

    public interface View extends BaseView<AllCoursesPresenter> {

        void setData(List<Course> courses);
    }

    public interface Presenter extends BasePresenter {

        void courses();

        String getToken();

        void removeToken();

        String getLogin();

        void setLogin(String login);
    }
}
