package com.istu.sisyuk.mobilestudent.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseFragment;
import com.istu.sisyuk.mobilestudent.ui.auth.AuthActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    @BindView(R.id.user_name_label)
    TextView userNameLabel;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_direction_label)
    TextView userDirectionLabel;

    @BindView(R.id.user_direction)
    TextView userDirection;

    @BindView(R.id.logout_button)
    Button logoutButton;

    @BindView(R.id.profile_progress)
    ProgressBar profileProgress;

    private Unbinder unbinder;
    private ProfilePresenter presenter;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ProfilePresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startClearTop(getContext());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_main_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String login = presenter.getLogin();
        userName.setText(login);
    }

    @Override
    public void setPresenter(ProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        profileProgress.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.logout_button)
    public void onViewClicked() {
        presenter.removeToken();
        AuthActivity.startClearTop(getContext());
    }
}
