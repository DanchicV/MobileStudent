package com.istu.sisyuk.mobilestudent.ui.subscriptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseActivity;
import com.istu.sisyuk.mobilestudent.base.BaseFragment;
import com.istu.sisyuk.mobilestudent.base.ItemCourseClickListener;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;
import com.istu.sisyuk.mobilestudent.ui.all_courses.AllCoursesFragment;
import com.istu.sisyuk.mobilestudent.ui.auth.AuthActivity;
import com.istu.sisyuk.mobilestudent.ui.course_info.CourseInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubscriptionsFragment extends BaseFragment implements SubscriptionsContract.View {

    @BindView(R.id.profile_progress)
    ProgressBar profileProgress;

    @BindView(R.id.added_subscriptions_recycler_view)
    RecyclerView addedSubscriptionsRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_view)
    SearchView searchView;

    private AddedSubscriptionsAdapter adapter;

    private Unbinder unbinder;
    private SubscriptionsPresenter presenter;
    private List<Subscription> subscriptions;

    public static SubscriptionsFragment newInstance() {
        return new SubscriptionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SubscriptionsPresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startNewTask(getContext());
        }

        adapter = new AddedSubscriptionsAdapter(new ItemCourseClickListener() {
            @Override
            public void onClick(long courseId) {
                if (isAdded()) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, CourseInfoFragment.newInstance(courseId), CourseInfoFragment.class.getSimpleName())
                            .addToBackStack(CourseInfoFragment.class.getSimpleName())
                            .commit();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            ((BaseActivity) activity).setActionBarIcon(R.drawable.ic_subscription);
            ((BaseActivity) activity).setActionBarTitle(getString(R.string.subscriptions));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        addedSubscriptionsRecyclerView.setLayoutManager(linearLayoutManager);
        addedSubscriptionsRecyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return searchCourse(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return searchCourse(newText);
            }
        });
    }

    private boolean searchCourse(String query) {
        if (TextUtils.isEmpty(query)) {
            adapter.setSubscriptions(subscriptions);
            return false;
        }
        query = query.toLowerCase();
        List<Subscription> foundSubscriptions = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            String name = subscription.getName();
            String teacherName = subscription.getTeacherName();
            if ((!TextUtils.isEmpty(name) && name.toLowerCase().contains(query))
                    || (!TextUtils.isEmpty(teacherName) && teacherName.toLowerCase().contains(query))) {
                foundSubscriptions.add(subscription);
            }
        }
        adapter.setSubscriptions(foundSubscriptions);
        return !foundSubscriptions.isEmpty();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.activity_main_add, menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.subscriptions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isAdded() && item.getItemId() == R.id.add) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .add(android.R.id.content, AllCoursesFragment.newInstance(), AllCoursesFragment.class.getSimpleName())
                    .addToBackStack(AllCoursesFragment.class.getSimpleName())
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(SubscriptionsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        profileProgress.setVisibility(b ? View.VISIBLE : View.GONE);
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

    @Override
    public void setData(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        adapter.setSubscriptions(subscriptions);
        if (!subscriptions.isEmpty()) {
            searchView.setVisibility(View.VISIBLE);
        } else {
            searchView.setVisibility(View.GONE);
        }
    }
}
