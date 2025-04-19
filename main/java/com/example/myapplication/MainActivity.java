package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etStudentID, etStudentName, etSection, etThai, etScience, etMaths, etEnglish;
    private EditText etTotalMarks, etPercentage, etGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnClear = findViewById(R.id.btnClear);
        Button btnExit = findViewById(R.id.btnExit);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnShowStudents = findViewById(R.id.btnShowStudents);

        // Initialize EditTexts
        etStudentID = findViewById(R.id.etStudentID);
        etStudentName = findViewById(R.id.etStudentName);
        etSection = findViewById(R.id.etSection);
        etThai = findViewById(R.id.etThai);
        etScience = findViewById(R.id.etScience);
        etMaths = findViewById(R.id.etMaths);
        etEnglish = findViewById(R.id.etEnglish);
        etTotalMarks = findViewById(R.id.etTotalMarks);
        etPercentage = findViewById(R.id.etPercentage);
        etGrade = findViewById(R.id.etGrade);

        // Button Click Listeners (Lambda for simplicity)
        btnCalculate.setOnClickListener(v -> calculateGrades());
        btnClear.setOnClickListener(v -> clearFields());
        btnExit.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveData());

        btnShowStudents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(intent);
        });

        // Database operations with try-with-resources
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db != null) {
                System.out.println("Database opened successfully");
            }
        }

        btnSave.setOnClickListener(v -> {
            try {
                String name = etStudentName.getText().toString();
                String section = etSection.getText().toString();
                int thai = Integer.parseInt(etThai.getText().toString());
                int science = Integer.parseInt(etScience.getText().toString());
                int maths = Integer.parseInt(etMaths.getText().toString());
                int english = Integer.parseInt(etEnglish.getText().toString());
                int total = thai + science + maths + english;
                double percentage = total / 4.0;
                String grade = (percentage >= 90) ? "A+" : (percentage >= 80) ? "A" :
                        (percentage >= 70) ? "B" : (percentage >= 60) ? "C" :
                                (percentage >= 50) ? "D" : "F";

                try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
                    boolean inserted = dbHelper.insertStudent(name, section, thai, science, maths, english, total, percentage, grade);
                    Toast.makeText(MainActivity.this, inserted ? "Data Saved" : "Error Saving Data", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "กรุณากรอกคะแนนให้ครบทุกช่อง!", Toast.LENGTH_SHORT).show();
            }
        });
        // Load stored data
        loadData();
    }

    @SuppressLint("DefaultLocale")
    private void calculateGrades() {
        try {
            int thai = Integer.parseInt(etThai.getText().toString());
            int science = Integer.parseInt(etScience.getText().toString());
            int maths = Integer.parseInt(etMaths.getText().toString());
            int english = Integer.parseInt(etEnglish.getText().toString());

            int totalMarks = thai + science + maths + english;
            double percentage = (totalMarks / 4.0);
            String grade = (percentage >= 90) ? "A+" : (percentage >= 80) ? "A" :
                    (percentage >= 70) ? "B" : (percentage >= 60) ? "C" :
                            (percentage >= 50) ? "D" : "F";

            etTotalMarks.setText(String.valueOf(totalMarks));
            etPercentage.setText(String.format("%.2f", percentage));
            etGrade.setText(grade);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "กรุณากรอกคะแนนให้ครบทุกช่อง!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etStudentID.setText("");
        etStudentName.setText("");
        etSection.setText("");
        etThai.setText("");
        etScience.setText("");
        etMaths.setText("");
        etEnglish.setText("");
        etTotalMarks.setText("");
        etPercentage.setText("");
        etGrade.setText("");
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("StudentData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("studentID", etStudentID.getText().toString());
        editor.putString("studentName", etStudentName.getText().toString());
        editor.putString("section", etSection.getText().toString());
        editor.putString("thai", etThai.getText().toString());
        editor.putString("science", etScience.getText().toString());
        editor.putString("maths", etMaths.getText().toString());
        editor.putString("english", etEnglish.getText().toString());
        editor.putString("totalMarks", etTotalMarks.getText().toString());
        editor.putString("percentage", etPercentage.getText().toString());
        editor.putString("grade", etGrade.getText().toString());

        editor.apply();
        Toast.makeText(this, "ข้อมูลถูกบันทึกแล้ว!", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("StudentData", MODE_PRIVATE);
        etStudentID.setText(sharedPreferences.getString("studentID", ""));
        etStudentName.setText(sharedPreferences.getString("studentName", ""));
        etSection.setText(sharedPreferences.getString("section", ""));
        etThai.setText(sharedPreferences.getString("thai", ""));
        etScience.setText(sharedPreferences.getString("science", ""));
        etMaths.setText(sharedPreferences.getString("maths", ""));
        etEnglish.setText(sharedPreferences.getString("english", ""));
        etTotalMarks.setText(sharedPreferences.getString("totalMarks", ""));
        etPercentage.setText(sharedPreferences.getString("percentage", ""));
        etGrade.setText(sharedPreferences.getString("grade", ""));
    }
}
