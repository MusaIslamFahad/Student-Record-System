package com.example.studentrecordsystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrecordsystem.R;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.model.Course;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courseList;
    private DatabaseHelper dbHelper;

    public CourseAdapter(List<Course> courseList, DatabaseHelper dbHelper) {
        this.courseList = courseList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.textViewCourseFName.setText("Full Name: " + course.getFname());
        holder.textViewCourseSName.setText("Short Name: " + course.getSname());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        holder.textViewCourseDate.setText("Date: " + sdf.format(new Date(course.getDate())));

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Retrieve the current adapter position
                if (adapterPosition != RecyclerView.NO_POSITION) { // Check if position is valid
                    Course manageToDelete = courseList.get(adapterPosition);
                    dbHelper.deleteCourse(manageToDelete.getId());
                    courseList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, getItemCount()); // Use getItemCount() to get the updated count
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCourseFName, textViewCourseSName, textViewCourseDate;
        ImageView buttonDelete;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseFName = itemView.findViewById(R.id.textViewManageCourseFName);
            textViewCourseSName = itemView.findViewById(R.id.textViewManageCourseSName);
            textViewCourseDate = itemView.findViewById(R.id.textViewManageCourseDate);


            buttonDelete = itemView.findViewById(R.id.buttonDeleteCourse);
        }
    }
}