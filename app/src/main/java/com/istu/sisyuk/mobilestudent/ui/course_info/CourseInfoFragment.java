package com.istu.sisyuk.mobilestudent.ui.course_info;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseFragment;
import com.istu.sisyuk.mobilestudent.data.models.Course;
import com.istu.sisyuk.mobilestudent.data.models.Material;
import com.istu.sisyuk.mobilestudent.ui.auth.AuthActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CourseInfoFragment extends BaseFragment implements CourseInfoContract.View {

    private static final String KEY_COURSE_ID = "COURSE_ID";

    @BindView(R.id.profile_progress)
    ProgressBar profileProgress;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.materials_recycler_view)
    RecyclerView materialsRecyclerView;

    @BindView(R.id.lectures_recycler_view)
    RecyclerView lecturesRecyclerView;

    @BindView(R.id.materials_label)
    TextView materialsLabel;

    @BindView(R.id.lectures_label)
    TextView lecturesLabel;

    @BindView(R.id.subscribe_button)
    Button subscribeButton;

    @BindView(R.id.unsubscribe_button)
    Button unsubscribeButton;

    private CourseInfoAdapter materialsAdapter;
    private CourseInfoAdapter lecturesAdapter;
    private Unbinder unbinder;
    private CourseInfoPresenter presenter;
    private long courseId;

    public static CourseInfoFragment newInstance(long courseId) {
        CourseInfoFragment fragment = new CourseInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_COURSE_ID, courseId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new CourseInfoPresenter();
        presenter.setView(this);
        presenter.subscribe();

        String token = presenter.getToken();
        if (TextUtils.isEmpty(token)) {
            AuthActivity.startNewTask(getContext());
        }

        materialsAdapter = new CourseInfoAdapter();
        lecturesAdapter = new CourseInfoAdapter();

        Bundle arguments = getArguments();
        if (arguments != null) {
            courseId = arguments.getLong(KEY_COURSE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.course(courseId);

        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setTitle(R.string.add);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        materialsRecyclerView.setLayoutManager(linearLayoutManager);
        materialsRecyclerView.setAdapter(materialsAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lecturesRecyclerView.setLayoutManager(linearLayoutManager);
        lecturesRecyclerView.setAdapter(lecturesAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home
                && isAdded()) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(CourseInfoPresenter presenter) {
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
    public void setData(Course course) {
        List<Material> materials = course.getMaterials();
        List<Material> lectures = course.getLectures();

        if (materials != null && !materials.isEmpty()) {
            materialsAdapter.setMaterials(materials);
            materialsRecyclerView.setVisibility(View.VISIBLE);
            materialsLabel.setVisibility(View.VISIBLE);
        } else {
            materialsRecyclerView.setVisibility(View.GONE);
            materialsLabel.setVisibility(View.GONE);
        }

        if (lectures != null && !lectures.isEmpty()) {
            lecturesAdapter.setMaterials(lectures);
            lecturesRecyclerView.setVisibility(View.VISIBLE);
            lecturesLabel.setVisibility(View.VISIBLE);
        } else {
            lecturesRecyclerView.setVisibility(View.GONE);
            lecturesLabel.setVisibility(View.GONE);
        }

        if (course.isSubscribed()) {
            subscribeButton.setVisibility(View.GONE);
            unsubscribeButton.setVisibility(View.VISIBLE);
        } else {
            subscribeButton.setVisibility(View.VISIBLE);
            unsubscribeButton.setVisibility(View.GONE);
        }

        setSubscribe(course.isSubscribed());
    }

    @Override
    public void setSubscribe(boolean isSubscribe) {
        if (isSubscribe) {
            subscribeButton.setVisibility(View.GONE);
            unsubscribeButton.setVisibility(View.VISIBLE);
        } else {
            subscribeButton.setVisibility(View.VISIBLE);
            unsubscribeButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.subscribe_button)
    public void subscribeClicked() {
        presenter.subscribeToCourse(courseId);
    }

    @OnClick(R.id.unsubscribe_button)
    public void unsubscribeClicked() {
        presenter.unsubscribeToCourse(courseId);
    }
}
