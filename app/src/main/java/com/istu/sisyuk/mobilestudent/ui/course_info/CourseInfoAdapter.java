package com.istu.sisyuk.mobilestudent.ui.course_info;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.data.models.Material;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseInfoAdapter extends RecyclerView.Adapter<CourseInfoAdapter.ViewHolder> {

    private List<Material> materials = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_info_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(materials.get(position));
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.info_name_text_view)
        TextView infoName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void onBind(Material material) {
            infoName.setText(material.getName());
        }
    }
}
