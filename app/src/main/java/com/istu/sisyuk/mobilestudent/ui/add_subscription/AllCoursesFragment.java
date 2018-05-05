package com.istu.sisyuk.mobilestudent.ui.add_subscription;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

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

public class AllCoursesFragment extends BaseFragment implements AllCoursesContract.View {

    @BindView(R.id.profile_progress)
    ProgressBar profileProgress;

    @BindView(R.id.subscriptions_recycler_view)
    RecyclerView subscriptionsRecyclerView;

    @BindView(R.id.courses_spinner)
    Spinner coursesSpinner;

    @BindView(R.id.teachers_spinner)
    Spinner teachersSpinner;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Course> allCourses = new ArrayList<>();
    private List<Course> currentCourses = new ArrayList<>();
    private SubscriptionsAdapter adapter;
    private ArrayAdapter courseAdapter;
    private ArrayAdapter teacherAdapter;
    private Unbinder unbinder;
    private AllCoursesPresenter presenter;

    public static AllCoursesFragment newInstance() {
        return new AllCoursesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AllCoursesPresenter();
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

        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setTitle(R.string.add);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        subscriptionsRecyclerView.setLayoutManager(linearLayoutManager);
        subscriptionsRecyclerView.setAdapter(adapter);

        courseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{});
        coursesSpinner.setAdapter(courseAdapter);
        teacherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{});
        teachersSpinner.setAdapter(teacherAdapter);

        coursesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursesFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //empty method
            }
        });

        teachersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursesFilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //empty method
            }
        });
    }

    private void coursesFilter() {
        View coursesItemView = coursesSpinner.getSelectedView();
        View teachersItemView = teachersSpinner.getSelectedView();
        if (coursesItemView instanceof TextView
                && teachersItemView instanceof TextView) {
            String courseFilter = ((TextView) coursesItemView).getText().toString();
            String teacherFilter = ((TextView) teachersItemView).getText().toString();
            currentCourses = new ArrayList<>();
            for (Course course : allCourses) {
                if ((courseFilter.equals(getString(R.string.all)) || courseFilter.equals(course.getName()))
                        && (teacherFilter.equals(getString(R.string.all)) || teacherFilter.equals(course.getTeacherName()))) {
                    currentCourses.add(course);
                }
            }
            adapter.setCourses(currentCourses);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void setPresenter(AllCoursesPresenter presenter) {
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
        this.allCourses = courses;
        this.currentCourses = courses;
        adapter.setCourses(courses);

        List<String> names = new ArrayList<>();
        names.add(getString(R.string.all));
        List<String> teachers = new ArrayList<>();
        teachers.add(getString(R.string.all));
        for (Course course : courses) {
            names.add(course.getName());
            teachers.add(course.getTeacherName());
        }
        if (isAdded()) {
            courseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, names);
            coursesSpinner.setAdapter(courseAdapter);
            teacherAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, teachers);
            teachersSpinner.setAdapter(teacherAdapter);
        }
    }
}
