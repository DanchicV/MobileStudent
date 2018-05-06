package com.istu.sisyuk.mobilestudent.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseActivity;
import com.istu.sisyuk.mobilestudent.ui.auth.AuthActivity;
import com.istu.sisyuk.mobilestudent.ui.profile.ProfileFragment;
import com.istu.sisyuk.mobilestudent.ui.subscriptions.SubscriptionsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainPresenter presenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startNewTask(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        presenter = new MainPresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startNewTask(this);
        }

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subscriptions:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, SubscriptionsFragment.newInstance(), SubscriptionsFragment.class.getSimpleName())
                        .commit();
                setActionBarTitle(getString(R.string.subscriptions));
                setActionBarIcon(R.drawable.ic_subscription);
                break;
            case R.id.profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName())
                        .commit();
                setActionBarTitle(getString(R.string.profile));
                setActionBarIcon(R.drawable.ic_profile);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setPresenter(MainPresenter presenter) {

    }

    @Override
    public void showProgress(Boolean b) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showError(int message) {

    }
}
