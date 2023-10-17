package com.example.androiid;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androiid.models.CourseData;
import com.example.androiid.models.Courses;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    List<CourseData> courses;
    public CoursesAdapter(List<CourseData> courses){
        this.courses = courses;
    }
    @NonNull
    @NotNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: ");
        View layoutInflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_text, parent, false);
        return new CourseViewHolder(layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseViewHolder holder, int position) {
        CourseData course = courses.get(position);
        holder.passData(course.courseTitle, course.courseTitle);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        View linearLayout;
        public CourseViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            linearLayout = itemView;
        }

        public void passData(String title, String code){
            ((TextView)linearLayout.findViewById(R.id.code)).setText(code);
            ((TextView)linearLayout.findViewById(R.id.title)).setText(title);
        }
    }
}
