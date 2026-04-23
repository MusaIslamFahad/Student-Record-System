package com.example.studentrecordsystem.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.studentrecordsystem.model.Course;
import com.example.studentrecordsystem.model.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";

    // Course table
    private static final String TABLE_COURSES = "courses";
    private static final String COLUMN_COURSE_ID = "id";
    private static final String COLUMN_COURSE_FNAME = "fname";
    private static final String COLUMN_COURSE_SNAME = "sname";
    private static final String COLUMN_COURSE_DATE = "date";
    private static final String COLUMN_COURSE_USER_ID = "course_user_id"; // Foreign key

    // Student Details table
    private static final String TABLE_STUDENT = "student";
    private static final String COLUMN_STUDENT_ID = "id";
    private static final String COLUMN_STUDENT_COURSE = "course";
    private static final String COLUMN_STUDENT_DATE = "date";

    private static final String COLUMN_STUDENT_NAME = "name";
    private static final String COLUMN_STUDENT_GENDER = "gender";
    private static final String COLUMN_STUDENT_PHYSICALLY = "physically";
    private static final String COLUMN_STUDENT_SESSION = "session";
    private static final String COLUMN_STUDENT_GUARDIAN = "guardian";
    private static final String COLUMN_STUDENT_NATIONALITY = "nationality";
    private static final String COLUMN_STUDENT_MOBILE = "mobile";
    private static final String COLUMN_STUDENT_EMAIL = "email";
    private static final String COLUMN_STUDENT_ADDRESS = "address";
    private static final String COLUMN_STUDENT_CITY = "city";
    private static final String COLUMN_STUDENT_STATE = "state";
    private static final String COLUMN_STUDENT_COUNTRY = "country";

    private static final String COLUMN_STUDENT_HSC_BOARD = "hscbord";
    private static final String COLUMN_STUDENT_HSC_CGPA = "hsccgpa";
    private static final String COLUMN_STUDENT_SSC_BOARD = "sscbord";
    private static final String COLUMN_STUDENT_SSC_CGPA = "ssccgpa";
    private static final String COLUMN_STUDENT_USER_ID = "student_user_id"; // Foreign key

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_COURSES_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
                + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_COURSE_FNAME + " TEXT,"
                + COLUMN_COURSE_SNAME + " TEXT,"
                + COLUMN_COURSE_DATE + " TEXT,"
                + COLUMN_COURSE_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_COURSE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_COURSES_TABLE);

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_STUDENT_COURSE + " TEXT,"
                + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_DATE + " TEXT,"
                + COLUMN_STUDENT_NAME + " TEXT,"
                + COLUMN_STUDENT_GENDER + " TEXT,"
                + COLUMN_STUDENT_PHYSICALLY + " TEXT,"
                + COLUMN_STUDENT_SESSION + " TEXT,"
                + COLUMN_STUDENT_GUARDIAN + " TEXT,"
                + COLUMN_STUDENT_NATIONALITY + " TEXT,"
                + COLUMN_STUDENT_MOBILE + " TEXT,"
                + COLUMN_STUDENT_EMAIL + " TEXT,"
                + COLUMN_STUDENT_ADDRESS + " TEXT,"
                + COLUMN_STUDENT_CITY + " TEXT,"
                + COLUMN_STUDENT_STATE + " TEXT,"
                + COLUMN_STUDENT_COUNTRY + " TEXT,"
                + COLUMN_STUDENT_HSC_BOARD + " TEXT,"
                + COLUMN_STUDENT_HSC_CGPA + " TEXT,"
                + COLUMN_STUDENT_SSC_BOARD + " TEXT,"
                + COLUMN_STUDENT_SSC_CGPA + " TEXT,"
                + COLUMN_STUDENT_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_STUDENT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_STUDENT_TABLE);

        insertFixedUsers(db);
    }

    private void insertFixedUsers(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COLUMN_NAME + ", " + COLUMN_PASSWORD + ") VALUES ('admin', 'admin')");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COLUMN_NAME + ", " + COLUMN_PASSWORD + ") VALUES ('user1', 'pass1')");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COLUMN_NAME + ", " + COLUMN_PASSWORD + ") VALUES ('user2', 'pass2')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public boolean checkUser(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{phone, password}, null, null, null);
        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }

    public int getUserId(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_NAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{phone, password}, null, null, null);
        int userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            if (idIndex != -1) {
                userId = cursor.getInt(idIndex);
            }
            cursor.close();
        }
        db.close();
        return userId;
    }

///////add course/////
    public boolean addCourse(String fname, String sname, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long timestamp = System.currentTimeMillis();
        values.put(COLUMN_COURSE_FNAME, fname);
        values.put(COLUMN_COURSE_SNAME, sname);
        values.put(COLUMN_COURSE_DATE, timestamp);
        values.put(COLUMN_COURSE_USER_ID, userId);

        Log.d("DATE_INSERT", "Timestamp inserted: " + timestamp);

        long result = db.insert(TABLE_COURSES, null, values);
        db.close();
        return result != -1;
    }

    // Get all Course
    public List<Course> getAllCourses(int userId) {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_COURSE_USER_ID  + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        Cursor cursor = db.query(TABLE_COURSES, null, selection, selectionArgs, null, null, COLUMN_COURSE_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_COURSE_ID));
                String fname = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_FNAME));
                String sname = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_SNAME));
                long date = cursor.getLong(cursor.getColumnIndex(COLUMN_COURSE_DATE));
                Log.d("DATE_FETCH", "Timestamp fetched: " + date);
                courses.add(new Course(id, fname, sname, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courses;
    }

    public boolean deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_COURSES, COLUMN_COURSE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }


    /////add student///
    public boolean addStudent(String course, String name, String gender, String physically, String session, String guardian, String nationality, String mobile, String email, String address, String city, String state, String country,
                              String hscboard, String hsccgpa, String sscboard, String ssccgpa, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long timestamp = System.currentTimeMillis();
        values.put(COLUMN_STUDENT_COURSE, course);
        values.put(COLUMN_STUDENT_DATE, timestamp);
        values.put(COLUMN_STUDENT_NAME, name);
        values.put(COLUMN_STUDENT_GENDER, gender);
        values.put(COLUMN_STUDENT_PHYSICALLY, physically);
        values.put(COLUMN_STUDENT_SESSION, session);
        values.put(COLUMN_STUDENT_GUARDIAN, guardian);
        values.put(COLUMN_STUDENT_NATIONALITY, nationality);
        values.put(COLUMN_STUDENT_MOBILE, mobile);
        values.put(COLUMN_STUDENT_EMAIL, email);
        values.put(COLUMN_STUDENT_ADDRESS, address);
        values.put(COLUMN_STUDENT_CITY, city);
        values.put(COLUMN_STUDENT_STATE, state);
        values.put(COLUMN_STUDENT_COUNTRY, country);
        values.put(COLUMN_STUDENT_HSC_BOARD, hscboard);
        values.put(COLUMN_STUDENT_HSC_CGPA, hsccgpa);
        values.put(COLUMN_STUDENT_SSC_BOARD, sscboard);
        values.put(COLUMN_STUDENT_SSC_CGPA, ssccgpa);
        values.put(COLUMN_STUDENT_USER_ID, userId); // use correct column name here

        Log.d("DATE_INSERT", "Timestamp inserted: " + timestamp);

        long result = db.insert(TABLE_STUDENT, null, values); // insert into table
        db.close();
        return result != -1;
    }

    // Get all student Monitors
    public List<Student> getAllStudent(int userId) {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_STUDENT_USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };
        Cursor cursor = db.query(TABLE_STUDENT, null, selection, selectionArgs, null, null, COLUMN_STUDENT_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_COURSE));
                long date = cursor.getLong(cursor.getColumnIndex(COLUMN_STUDENT_DATE));
                String studentname = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_GENDER));
                String physically = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PHYSICALLY));

                String session = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SESSION));
                String guardian = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_GUARDIAN));
                String nationality = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NATIONALITY));
                String mobile = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_MOBILE));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ADDRESS));
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_CITY));
                String state = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_STATE));
                String country = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_COUNTRY));
                String hscboard = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_HSC_BOARD));
                String hsccgpa = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_HSC_CGPA));
                String sscboard = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SSC_BOARD));
                String ssccgpa = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SSC_CGPA));

                Log.d("DATE_FETCH", "Timestamp fetched: " + date);
                students.add(new Student(id, name, date, studentname, gender, physically, session, guardian, nationality, mobile, email, address, city, state, country, hscboard, hsccgpa, sscboard, ssccgpa));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    ///delete student
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_STUDENT, COLUMN_STUDENT_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }


    public boolean updateStudent(int id, String course, String name, String gender, String physically, String session, String guardian,
                                 String nationality, String mobile, String email, String address, String city, String state, String country,
                                 String hscboard, String hsccgpa, String sscboard, String ssccgpa) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENT_COURSE, course);
        values.put(COLUMN_STUDENT_NAME, name);
        values.put(COLUMN_STUDENT_GENDER, gender);
        values.put(COLUMN_STUDENT_PHYSICALLY, physically);
        values.put(COLUMN_STUDENT_SESSION, session);
        values.put(COLUMN_STUDENT_GUARDIAN, guardian);
        values.put(COLUMN_STUDENT_NATIONALITY, nationality);
        values.put(COLUMN_STUDENT_MOBILE, mobile);
        values.put(COLUMN_STUDENT_EMAIL, email);
        values.put(COLUMN_STUDENT_ADDRESS, address);
        values.put(COLUMN_STUDENT_CITY, city);
        values.put(COLUMN_STUDENT_STATE, state);
        values.put(COLUMN_STUDENT_COUNTRY, country);
        values.put(COLUMN_STUDENT_HSC_BOARD, hscboard);
        values.put(COLUMN_STUDENT_HSC_CGPA, hsccgpa);
        values.put(COLUMN_STUDENT_SSC_BOARD, sscboard);
        values.put(COLUMN_STUDENT_SSC_CGPA, ssccgpa);

        int result = db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }


    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, null, COLUMN_STUDENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int studentId = cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID));
            String course = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_COURSE));
            long date = cursor.getLong(cursor.getColumnIndex(COLUMN_STUDENT_DATE));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME));
            String gender = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_GENDER));
            String physically = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PHYSICALLY));
            String session = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SESSION));
            String guardian = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_GUARDIAN));
            String nationality = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NATIONALITY));
            String mobile = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_MOBILE));
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL));
            String address = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ADDRESS));
            String city = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_CITY));
            String state = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_STATE));
            String country = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_COUNTRY));
            String hscboard = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_HSC_BOARD));
            String hsccgpa = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_HSC_CGPA));
            String sscboard = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SSC_BOARD));
            String ssccgpa = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SSC_CGPA));

            cursor.close();
            db.close();

            return new Student(studentId, course, date, name, gender, physically, session,
                    guardian, nationality, mobile, email, address, city, state, country,
                    hscboard, hsccgpa, sscboard, ssccgpa);
        }

        if (cursor != null) cursor.close();
        db.close();
        return null; // Student not found
    }


    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        int rows = db.update(TABLE_USERS, values, COLUMN_NAME + "=?", new String[]{username});
        db.close();
        return rows > 0;
    }

    public int getTotalCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_COURSES, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public int getTotalStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_STUDENT, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
}


