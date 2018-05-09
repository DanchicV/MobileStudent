package com.istu.sisyuk.mobilestudent.ui.subscriptions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.ItemCourseClickListener;
import com.istu.sisyuk.mobilestudent.data.models.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddedSubscriptionsAdapter extends RecyclerView.Adapter<AddedSubscriptionsAdapter.ViewHolder> {

    private List<Subscription> subscriptions = new ArrayList<>();
    private ItemCourseClickListener courseClickListener;

    public AddedSubscriptionsAdapter(ItemCourseClickListener courseClickListener) {
        this.courseClickListener = courseClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.added_subscriptions_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(subscriptions.get(position), courseClickListener);
    }

    @Override
    public int getItemCount() {
        return subscriptions.size();
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.course_name)
        TextView courseName;

        @BindView(R.id.teacher_name)
        TextView teacherName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void onBind(final Subscription subscription, final ItemCourseClickListener clickListener) {
            courseName.setText(subscription.getName());
            teacherName.setText(subscription.getTeacherName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(subscription.getId());
                }
            });
        }
    }
}
