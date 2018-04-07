package com.istu.sisyuk.mobilestudent.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

    void showProgress(Boolean b);

    void showError(String message);

    void showError(int message);
}
