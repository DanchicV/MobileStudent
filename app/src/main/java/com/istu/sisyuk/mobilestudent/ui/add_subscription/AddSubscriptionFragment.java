package com.istu.sisyuk.mobilestudent.ui.add_subscription;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseFragment;
import com.istu.sisyuk.mobilestudent.data.models.Course;
import com.istu.sisyuk.mobilestudent.ui.auth.AuthActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddSubscriptionFragment extends BaseFragment implements AddSubscriptionContract.View {

    @BindView(R.id.profile_progress)
    ProgressBar profileProgress;

    @BindView(R.id.subscriptions_recycler_view)
    RecyclerView subscriptionsRecyclerView;

    @BindView(R.id.courses_spinner)
    Spinner coursesSpinner;

    @BindView(R.id.teachers_spinner)
    Spinner teachersSpinner;

    private SubscriptionsAdapter adapter;
    private ArrayAdapter courseAdapter;
    private ArrayAdapter teacherAdapter;

    private Unbinder unbinder;
    private AddSubscriptionPresenter presenter;

    public static AddSubscriptionFragment newInstance() {
        return new AddSubscriptionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AddSubscriptionPresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startNewTask(getContext());
        }

        adapter = new SubscriptionsAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.courses();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        subscriptionsRecyclerView.setLayoutManager(linearLayoutManager);
        subscriptionsRecyclerView.setAdapter(adapter);

        courseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{});
        teacherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{});

        coursesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        teachersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void setPresenter(AddSubscriptionPresenter presenter) {
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
    public void setData(List<Course> courses) {
        adapter.setCourses(courses);

        List<String> names = new ArrayList<>();
        List<String> teachers = new ArrayList<>();
        for (Course course : courses) {
            names.add(course.getName());
            teachers.add(course.getTeacherName());
        }
        if (isAdded()) {
            courseAdapter.clear();
            courseAdapter.addAll(names);
            courseAdapter.notifyDataSetChanged();
            teacherAdapter.clear();
            teacherAdapter.addAll(teachers);
            teacherAdapter.notifyDataSetChanged();
        }
    }
}
