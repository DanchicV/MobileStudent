package com.istu.sisyuk.mobilestudent.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseActivity;
import com.istu.sisyuk.mobilestudent.ui.auth.AuthActivity;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    private ProfilePresenter presenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ProfilePresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startClearTop(this);
        }
    }

    @Override
    public void setPresenter(ProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {

    }

    @Override
    public void showError(String message) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .title(R.string.warning)
                .content(message)
                .positiveText(android.R.string.ok)
                .show();
        materialDialog.getActionButton(DialogAction.POSITIVE).requestFocus();
    }

    @Override
    public void showError(int message) {
        showError(getString(message));
    }
}
