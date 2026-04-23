# Student Record System

An Android application for managing student records and academic courses. Built with Java and SQLite, it provides a user-authenticated, offline-first experience for adding, viewing, editing, and deleting students and courses.

---

## Features

- **User Authentication**: Secure login system with per-user data isolation
- **Course Management**: Create and manage courses with full name, short name, and date
- **Student Management**: Add detailed student profiles including personal, contact, and academic information
- **Dashboard Overview**: View total counts of courses and students at a glance
- **Edit & Delete**: Full CRUD operations on both student and course records
- **Navigation Drawer**: Side menu for quick access to all sections
- **Offline Storage**: All data is stored locally using SQLite (no internet required)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Platform | Android (min SDK 24 / Android 7.0+, target SDK 34) |
| Database | SQLite via `SQLiteOpenHelper` |
| UI Components | AppCompat, Material Design, ConstraintLayout, DrawerLayout, CardView |
| Build System | Gradle (8.0) |

---

## Project Structure

```
app/src/main/java/com/example/studentrecordsystem/
├── MainActivity.java                  # Dashboard with navigation drawer
├── activity/
│   ├── SplashActivity.java            # Launch screen
│   ├── LoginActivity.java             # User login & session management
│   ├── AddCourseActivity.java         # Create a new course
│   ├── ManageCourseActivity.java      # View & delete courses
│   ├── AddStudentDetailsActivity.java # Add a new student
│   ├── ManageStudentDetailsActivity.java # View & delete students
│   └── EditStudentActivity.java       # Edit existing student records
├── adapter/
│   ├── CourseAdapter.java             # RecyclerView adapter for courses
│   └── StudentAdapter.java            # RecyclerView adapter for students
├── database/
│   └── DatabaseHelper.java            # SQLite schema and all DB operations
└── model/
    ├── Course.java                    # Course data model
    └── Student.java                   # Student data model
```

---

## Database Schema

**users** — Stores registered user accounts
| Column | Type |
|---|---|
| id | INTEGER (PK) |
| name | TEXT |
| password | TEXT |

**courses** — Courses linked to a user
| Column | Type |
|---|---|
| id | INTEGER (PK) |
| fname | TEXT (Full name) |
| sname | TEXT (Short name) |
| date | TEXT |
| course_user_id | INTEGER (FK → users.id) |

**student** — Student records linked to a user
| Column | Type |
|---|---|
| id | INTEGER (PK) |
| name, gender, session | TEXT |
| guardian, nationality | TEXT |
| mobile, email, address | TEXT |
| city, state, country | TEXT |
| physically | TEXT (physically challenged status) |
| course, date | TEXT |
| hscbord, hsccgpa, sscbord, ssccgpa | TEXT (Academic results) |
| student_user_id | INTEGER (FK → users.id) |

---

## Getting Started

### Prerequisites

- Android Studio (Hedgehog or later recommended)
- Android device or emulator running API 24+

### Installation

1. Clone the repository:
```bash
   git clone https://github.com/your-username/student-record-system.git
```
2. Open the project in **Android Studio**.
3. Let Gradle sync and download dependencies.
4. Run the app on an emulator or physical device via **Run > Run 'app'**.

> A pre-built debug APK is available at `app/build/outputs/apk/debug/app-debug.apk`.

---

## Usage

1. Launch the app — a splash screen will appear briefly.
2. **Register / Log in** with a username and password.
3. From the **Dashboard**, use the navigation drawer or cards to:
   - Add and manage **Courses**
   - Add, edit, and delete **Students**
4. All data is saved locally on the device and is tied to the logged-in user account.

---

## Dependencies

```groovy
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.5.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
```

---

## License

This project is open source. Add your preferred license here.
