package com.example.studentrecordsystem.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentrecordsystem.R;
import com.example.studentrecordsystem.activity.AddStudentDetailsActivity;
import com.example.studentrecordsystem.activity.EditStudentActivity;
import com.example.studentrecordsystem.database.DatabaseHelper;
import com.example.studentrecordsystem.model.Student;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;
    private List<Student> studentList;
    private DatabaseHelper dbHelper;

    public StudentAdapter(Context context, List<Student> studentList, DatabaseHelper dbHelper) {
        this.context = context;
        this.studentList = studentList;
        this.dbHelper = dbHelper;

    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.textViewStudentCourseName.setText("Course Name: " + student.getCourse());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        holder.textViewManageSugarDate.setText("Date: " + sdf.format(new Date(student.getDate())));
        holder.textViewStudentName.setText("Name:" + student.getStudentname());
        holder.textViewGender.setText("Gender: " + student.getGender());
        holder.textViewphysically.setText("Physically Challenged: " + student.getPhysically());

        holder.textViewStudentSession.setText("Session: " + student.getSession());
        holder.textViewGardianName.setText("Guardian Name: " + student.getGuardian());
        holder.textViewNationality.setText("Nationality: " + student.getNationality());
        holder.textViewMobile.setText("Mobile: " + student.getMobile());
        holder.textViewEmail.setText("Email: " + student.getEmail());
        holder.textViewAddress.setText("Address: " + student.getAddress());
        holder.textViewCity.setText("City: " + student.getCity());
        holder.textViewState.setText("State: " + student.getState());
        holder.textViewCountry.setText("Country: " + student.getCountry());

        holder.textViewHscBoard.setText("HSC Board: " + student.getHscboard());
        holder.textViewHscCgpa.setText("HSC CGPA: " + student.getHsccgpa());
        holder.textViewSscBoard.setText("SSC Board: " + student.getSscboard());
        holder.textViewSscCgpa.setText("SSC CGPA: " + student.getSsccgpa());

        holder.buttonStudentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Retrieve the current adapter position
                if (adapterPosition != RecyclerView.NO_POSITION) { // Check if position is valid
                    Student manageToDelete = studentList.get(adapterPosition);
                    dbHelper.deleteStudent(manageToDelete.getId());
                    studentList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, getItemCount()); // Use getItemCount() to get the updated count
                }
            }
        });

        holder.buttonStudentEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditStudentActivity.class);
            intent.putExtra("studentId", student.getId());
            Log.d("EditClick", "Student ID: " + student.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void updateData(List<Student> newStudentList) {
        this.studentList = newStudentList;
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStudentCourseName, textViewManageSugarDate, textViewStudentName, textViewGender, textViewphysically,
                textViewStudentSession, textViewGardianName, textViewNationality, textViewMobile, textViewEmail, textViewAddress, textViewCity, textViewState,
                textViewCountry, textViewHscBoard, textViewHscCgpa, textViewSscBoard, textViewSscCgpa
                ;
        ImageView buttonStudentDelete, buttonStudentEdit ;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStudentCourseName = itemView.findViewById(R.id.textViewStudentCourseName);
            textViewManageSugarDate = itemView.findViewById(R.id.textViewManageSugarDate);
            textViewStudentName = itemView.findViewById(R.id.textViewStudentName);
            textViewGender = itemView.findViewById(R.id.textViewGender);
            textViewphysically = itemView.findViewById(R.id.textViewphysically);

            textViewStudentSession = itemView.findViewById(R.id.textViewStudentSession);
            textViewGardianName = itemView.findViewById(R.id.textViewGardianName);
            textViewNationality = itemView.findViewById(R.id.textViewNationality);
            textViewMobile = itemView.findViewById(R.id.textViewMobile);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewState = itemView.findViewById(R.id.textViewState);
            textViewCountry = itemView.findViewById(R.id.textViewCountry);

            textViewHscBoard = itemView.findViewById(R.id.textViewHscBoard);
            textViewHscCgpa = itemView.findViewById(R.id.textViewHscCgpa);
            textViewSscBoard = itemView.findViewById(R.id.textViewSscBoard);
            textViewSscCgpa = itemView.findViewById(R.id.textViewSscCgpa);

            buttonStudentDelete = itemView.findViewById(R.id.buttonStudentDelete);
            buttonStudentEdit = itemView.findViewById(R.id.buttonStudentEdit);
        }
    }
}