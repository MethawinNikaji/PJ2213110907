package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name & Version
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;

    // Table & Column Names
    private static final String TABLE_NAME = "students";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_SECTION = "section";
    private static final String COL_THAI = "thai";
    private static final String COL_SCIENCE = "science";
    private static final String COL_MATHS = "maths";
    private static final String COL_ENGLISH = "english";
    private static final String COL_TOTAL = "total";
    private static final String COL_PERCENTAGE = "percentage";
    private static final String COL_GRADE = "grade";

    // SQL Create Table Statement
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NAME + " TEXT, " +
                    COL_SECTION + " TEXT, " +
                    COL_THAI + " INTEGER, " +
                    COL_SCIENCE + " INTEGER, " +
                    COL_MATHS + " INTEGER, " +
                    COL_ENGLISH + " INTEGER, " +
                    COL_TOTAL + " INTEGER, " +
                    COL_PERCENTAGE + " REAL, " +
                    COL_GRADE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert Data into Database
    public boolean insertStudent(String name, String section, int thai, int science, int maths, int english, int total, double percentage, String grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Remove "StudentID" since ID is auto-incremented and not passed
        values.put(COL_NAME, name);
        values.put(COL_SECTION, section);
        values.put(COL_THAI, thai);
        values.put(COL_SCIENCE, science);
        values.put(COL_MATHS, maths);
        values.put(COL_ENGLISH, english);
        values.put(COL_TOTAL, total);
        values.put(COL_PERCENTAGE, percentage);
        values.put(COL_GRADE, grade);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }


    // Fetch Data
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setStudentID(cursor.getInt(0)); // StudentID
                student.setName(cursor.getString(1)); // Name
                student.setSection(cursor.getString(2)); // Section
                student.setThai(cursor.getInt(3)); // Thai Score
                student.setScience(cursor.getInt(4)); // Science Score
                student.setMaths(cursor.getInt(5)); // Maths Score
                student.setEnglish(cursor.getInt(6)); // English Score
                student.setTotal(cursor.getInt(7)); // Total Score
                student.setPercentage(cursor.getDouble(8)); // Percentage
                student.setGrade(cursor.getString(9)); // Grade

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    // Clear All Students
    public void clearAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
