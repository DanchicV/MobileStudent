package com.istu.sisyuk.mobilestudent.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.istu.sisyuk.mobilestudent.MobileStudentApplication;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends BaseActivity implements AuthContract.View {

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    @BindView(R.id.login_edit_text)
    AutoCompleteTextView loginEditText;

    @BindView(R.id.email_edit_text)
    AutoCompleteTextView emailEditText;

    @BindView(R.id.email_input_layout)
    TextInputLayout emailInputLayout;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.password_input_layout)
    TextInputLayout passwordInputLayout;

    @BindView(R.id.retry_password_edit_text)
    EditText retryPasswordEditText;

    @BindView(R.id.retry_password_input_layout)
    TextInputLayout retryPasswordInputLayout;

    @BindView(R.id.sign_in_button)
    Button signInButton;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.auth_trigger)
    TextView authTrigger;

    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;

    @BindView(R.id.login_form)
    ScrollView loginForm;

    private AuthPresenter presenter;
    private boolean isSignIn;

    public static void start(Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        context.startActivity(intent);

        String token = MobileStudentApplication.getComponent().getPreferenceHelper().getToken();
        if (!TextUtils.isEmpty(token)) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        presenter = new AuthPresenter();
        presenter.setView(this);
        presenter.subscribe();

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

    @OnClick(R.id.sign_in_button)
    public void onSignInButtonClicked() {
        String email = emailEditText.getText().toString();
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String retryPassword = retryPasswordEditText.getText().toString();
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(login)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(retryPassword)
                && !passwordInputLayout.isErrorEnabled()
                && !retryPasswordInputLayout.isErrorEnabled()) {
            presenter.signIn(email, login, password);
        }
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
            presenter.login(login, password);
        }
    }

    @OnClick(R.id.auth_trigger)
    public void onAuthTriggerClicked() {
        switchSignIn(!isSignIn);
    }

    private void setViewVisible(android.view.View view, boolean visible) {
        view.setVisibility(visible ? android.view.View.VISIBLE : android.view.View.GONE);
    }

    @Override
    public void setPresenter(AuthPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        setViewVisible(loginProgress, b);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void switchSignIn(boolean isSignIn) {
        this.isSignIn = isSignIn;
        setViewVisible(signInButton, isSignIn);
        setViewVisible(loginButton, !isSignIn);
        setViewVisible(emailInputLayout, isSignIn);
        setViewVisible(retryPasswordInputLayout, isSignIn);
        authTrigger.setText(isSignIn ? R.string.login_trigger : R.string.sign_in_trigger);
    }
}

