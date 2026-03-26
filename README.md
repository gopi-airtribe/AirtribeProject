# LearnTrack

LearnTrack is a console-based **Student & Course Management System** built with **Core Java** and **Maven**.

It helps admins manage:
- Students
- Courses
- Enrollments

The project is intentionally focused on Java fundamentals:
- classes and objects
- constructors and constructor overloading
- encapsulation
- inheritance and method overriding
- static members
- collections using `ArrayList`
- basic custom exception handling
- menu-driven console programming

## Package Structure

Base package: `com.airtribe.learntrack`

- `entity` - domain models like `Student`, `Course`, `Enrollment`
- `service` - business logic and in-memory storage using `ArrayList`
- `ui` - console menu handling
- `exception` - custom exceptions
- `util` - helper classes like `IdGenerator` and `InputValidator`

## Features

### Student Management
- Add new student
- View all students
- Search student by ID
- Update student details
- Deactivate student

### Course Management
- Add new course
- View all courses
- Search course by ID
- Update course details
- Activate course
- Deactivate course

### Enrollment Management
- Enroll a student in a course
- View enrollments for a student
- View all enrollments
- Mark enrollment as completed
- Mark enrollment as cancelled

## Requirements

- JDK 17
- Maven 3.8+

## Compile and Run

### Compile
```bash
mvn clean compile
```

### Run tests
```bash
mvn test
```

### Run the application
```bash
java -cp target/classes com.airtribe.learntrack.Main
```

If you want to use the compatibility wrapper, this also works:
```bash
java -cp target/classes org.example.Main
```

## Example Flow
1. Add a student
2. Add a course
3. Enroll the student in the course
4. View enrollments
5. Update enrollment status

## Documentation
- `docs/Setup_Instructions.md`
- `docs/JVM_Basics.md`
- `docs/Design_Notes.md`

