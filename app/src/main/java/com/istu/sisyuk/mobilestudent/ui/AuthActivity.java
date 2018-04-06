package com.istu.sisyuk.mobilestudent.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.istu.sisyuk.mobilestudent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.login)
    AutoCompleteTextView login;
    @BindView(R.id.email)
    AutoCompleteTextView email;
    @BindView(R.id.email_input_layout)
    TextInputLayout emailInputLayout;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.retry_password)
    EditText retryPassword;
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

    private boolean isSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInButtonClicked() {
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
    }

    @OnClick(R.id.auth_trigger)
    public void onAuthTriggerClicked() {
        isSignIn = !isSignIn;
    }

    private void setViewVisible() {

    }
}

