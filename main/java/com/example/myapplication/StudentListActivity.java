package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        // Initialize UI elements
        listViewStudents = findViewById(R.id.listViewStudents);
        dbHelper = new DatabaseHelper(this);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnClearData = findViewById(R.id.btnClearData);

        // Set up button listeners
        btnBack.setOnClickListener(v -> finish()); // Go back to previous activity

        btnClearData.setOnClickListener(v -> {
            dbHelper.clearAllStudents(); // Clears student data from DB
            Toast.makeText(StudentListActivity.this, "All student data cleared!", Toast.LENGTH_SHORT).show();
            loadStudentData(); // Refresh the list
        });

        loadStudentData(); // Load student data initially
    }

    private void loadStudentData() {
        List<Student> studentList = dbHelper.getAllStudents(); // Now correctly returns a List<Student>
        ArrayList<String> studentStringList = new ArrayList<>();

        if (studentList.isEmpty()) {
            Toast.makeText(this, "No students found!", Toast.LENGTH_SHORT).show();
        } else {
            for (Student student : studentList) {
                String studentInfo = "Student ID: " + student.getStudentID() + "\n" +
                        "Name: " + student.getName() + "\n" +
                        "Section: " + student.getSection() + "\n" +
                        "Thai: " + student.getThai() + "\n" +  // Use getThai()
                        "Science: " + student.getScience() + "\n" +  // Use getScience()
                        "Maths: " + student.getMaths() + "\n" +  // Use getMaths()
                        "English: " + student.getEnglish() + "\n" +  // Use getEnglish()
                        "Total: " + student.getTotal() + "\n" +
                        "Percentage: " + student.getPercentage() + "%\n" +
                        "Grade: " + student.getGrade();
                studentStringList.add(studentInfo);
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentStringList);
            listViewStudents.setAdapter(adapter);
        }
    }

}
