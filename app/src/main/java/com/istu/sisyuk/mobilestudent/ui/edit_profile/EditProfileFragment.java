package com.istu.sisyuk.mobilestudent.ui.edit_profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class EditProfileFragment extends BaseFragment implements EditProfileContract.View {

    @BindView(R.id.login_edit_text)
    AutoCompleteTextView loginEditText;

    @BindView(R.id.email_edit_text)
    AutoCompleteTextView emailEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.retry_password_edit_text)
    EditText retryPasswordEditText;

    @BindView(R.id.edit_button)
    Button editButton;

    @BindView(R.id.cancel_button)
    Button cancelButton;

    @BindView(R.id.edit_progress)
    ProgressBar editProgress;

    @BindView(R.id.password_input_layout)
    TextInputLayout passwordInputLayout;

    @BindView(R.id.retry_password_input_layout)
    TextInputLayout retryPasswordInputLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;
    private EditProfilePresenter presenter;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new EditProfilePresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startNewTask(getContext());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String login = presenter.getLogin();
        loginEditText.setText(login);
        String password = presenter.getLogin();
        passwordEditText.setText(password);
        retryPasswordEditText.setText(password);

        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle(R.string.editing);
        }

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String password = passwordEditText.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_GO && TextUtils.isEmpty(password)) {
                    passwordInputLayout.setErrorEnabled(true);
                    passwordInputLayout.setError(getString(R.string.empty_passwords));
                } else {
                    passwordInputLayout.setErrorEnabled(false);
                }
                return true;
            }
        });
        retryPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String password = passwordEditText.getText().toString();
                String retryPassword = retryPasswordEditText.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_GO && !password.equals(retryPassword)) {
                    retryPasswordInputLayout.setErrorEnabled(true);
                    retryPasswordInputLayout.setError(getString(R.string.passwords_do_not_match));
                } else {
                    retryPasswordInputLayout.setErrorEnabled(false);
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void setPresenter(EditProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        editProgress.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        if (isAdded()) {
            MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.warning)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .show();
            materialDialog.getActionButton(DialogAction.POSITIVE).requestFocus();
        }
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

    @OnClick(R.id.edit_button)
    public void onEditButtonClicked() {
        String email = emailEditText.getText().toString();
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String retryPassword = retryPasswordEditText.getText().toString();
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(login)
                && !TextUtils.isEmpty(password)
                && TextUtils.equals(password, retryPassword)) {
            presenter.editProfile(email, login, password);
        }
    }

    @OnClick(R.id.cancel_button)
    public void onCancelButtonClicked() {
        finish();
    }

    @Override
    public void profileChanged() {
        finish();
    }

    private void finish() {
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }
}
