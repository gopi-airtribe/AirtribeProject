package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.TrainerService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final TrainerService trainerService = new TrainerService();
    private final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

    {
        courseService.setTrainerService(trainerService);
        trainerService.setCourseService(courseService);
    }

    public void start() {
        boolean running = true;
        printWelcome();

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> handleStudentMenu();
                    case "2" -> handleCourseMenu();
                    case "3" -> handleEnrollmentMenu();
                    case "4" -> handleTrainerMenu();
                    case "0" -> {
                        running = false;
                        System.out.println("Thank you for using LearnTrack.");
                    }
                    default -> System.out.println("Invalid option. Please choose a valid menu number.");
                }
            } catch (EntityNotFoundException | InvalidInputException exception) {
                System.out.println("Operation failed: " + exception.getMessage());
            } catch (Exception exception) {
                System.out.println("Unexpected error: " + exception.getMessage());
            }
        }
    }

    private void printWelcome() {
        System.out.println("========================================");
        System.out.println(" Welcome to LearnTrack");
        System.out.println(" Student & Course Management System");
        System.out.println("========================================");
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Trainer Management");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    //MainFlowMethods

    private void handleStudentMenu() throws EntityNotFoundException, InvalidInputException {
        boolean inStudentMenu = true;

        while (inStudentMenu) {
            System.out.println();
            System.out.println("Student Management");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student");
            System.out.println("5. Deactivate student");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addStudentFlow();
                case "2" -> viewStudentsFlow();
                case "3" -> searchStudentFlow();
                case "4" -> updateStudentFlow();
                case "5" -> deactivateStudentFlow();
                case "0" -> inStudentMenu = false;
                default -> System.out.println("Invalid option. Please choose a valid menu number.");
            }
        }
    }

    private void handleCourseMenu() throws EntityNotFoundException, InvalidInputException {
        boolean inCourseMenu = true;

        while (inCourseMenu) {
            System.out.println();
            System.out.println("Course Management");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. Search course by ID");
            System.out.println("4. Update course");
            System.out.println("5. Activate course");
            System.out.println("6. Deactivate course");
            System.out.println("7. Assign trainer to course");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addCourseFlow();
                case "2" -> viewCoursesFlow();
                case "3" -> searchCourseFlow();
                case "4" -> updateCourseFlow();
                case "5" -> activateCourseFlow();
                case "6" -> deactivateCourseFlow();
                case "7" -> assignTrainerToCourseFlow();
                case "0" -> inCourseMenu = false;
                default -> System.out.println("Invalid option. Please choose a valid menu number.");
            }
        }
    }
//change
    private void handleEnrollmentMenu() throws EntityNotFoundException, InvalidInputException {
        boolean inEnrollmentMenu = true;

        while (inEnrollmentMenu) {
            System.out.println();
            System.out.println("Enrollment Management");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. View all enrollments");
            System.out.println("4. Mark enrollment as completed");
            System.out.println("5. Mark enrollment as cancelled");
            System.out.println("6. Mark enrollment as Active");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> enrollStudentFlow();
                case "2" -> viewStudentEnrollmentsFlow();
                case "3" -> viewAllEnrollmentsFlow();
                case "4" -> updateEnrollmentStatusFlow(EnrollmentStatus.COMPLETED);
                case "5" -> updateEnrollmentStatusFlow(EnrollmentStatus.CANCELLED);
                case "6" ->  updateEnrollmentStatusFlow(EnrollmentStatus.ACTIVE);
                case "0" -> inEnrollmentMenu = false;
                default -> System.out.println("Invalid option. Please choose a valid menu number.");
            }
        }
    }

    private void handleTrainerMenu() throws EntityNotFoundException, InvalidInputException {
        boolean inTrainerMenu = true;

        while (inTrainerMenu) {
            System.out.println();
            System.out.println("Trainer Management");
            System.out.println("1. Add new trainer");
            System.out.println("2. View all trainers");
            System.out.println("3. Search trainer by ID");
            System.out.println("4. Update trainer details");
            System.out.println("5. Update trainer courses");
            System.out.println("6. Remove trainer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addTrainerFlow();
                case "2" -> viewTrainersFlow();
                case "3" -> searchTrainerFlow();
                case "4" -> updateTrainerDetailsFlow();
                case "5" -> updateTrainerCoursesFlow();
                case "6" -> removeTrainerFlow();
                case "0" -> inTrainerMenu = false;
                default -> System.out.println("Invalid option. Please choose a valid menu number.");
            }
        }
    }

    //StudentFlow Util Methods
    private void addStudentFlow() throws InvalidInputException {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email (optional): ");
        String email = scanner.nextLine();
        System.out.print("Batch: ");
        String batch = scanner.nextLine();

        Student student;
        if (email == null || email.trim().isEmpty()) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            student = studentService.addStudent(firstName, lastName, email, batch);
        }

        System.out.println("Student added successfully: " + student);
    }

    private void viewStudentsFlow() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void searchStudentFlow() throws EntityNotFoundException, InvalidInputException {
        int studentId = readInt("Enter student ID: ");
        Student student = studentService.findStudentById(studentId);
        System.out.println("Student found: " + student);
    }

    private void updateStudentFlow() throws EntityNotFoundException, InvalidInputException {
        int studentId = readInt("Enter student ID to update: ");
        System.out.print("New first name: ");
        String firstName = scanner.nextLine();
        System.out.print("New last name: ");
        String lastName = scanner.nextLine();
        System.out.print("New email (optional): ");
        String email = scanner.nextLine();
        System.out.print("New batch: ");
        String batch = scanner.nextLine();

        Student updatedStudent = studentService.updateStudent(studentId, firstName, lastName, email, batch);
        System.out.println("Student updated successfully: " + updatedStudent);
    }

    private void deactivateStudentFlow() throws EntityNotFoundException, InvalidInputException {
        int studentId = readInt("Enter student ID to deactivate: ");
        Student student = studentService.deactivateStudent(studentId);
        System.out.println("Student deactivated successfully: " + student);
    }

    //CourseFlow Util Methods
    private void addCourseFlow() throws InvalidInputException {
        System.out.print("Course name: ");
        String courseName = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        int durationInWeeks = readInt("Duration in weeks: ");

        Course course = courseService.addCourse(courseName, description, durationInWeeks);
        System.out.println("Course added successfully: " + course);
    }

    private void viewCoursesFlow() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        System.out.println("Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void searchCourseFlow() throws EntityNotFoundException, InvalidInputException {
        int courseId = readInt("Enter course ID: ");
        Course course = courseService.findCourseById(courseId);
        System.out.println("Course found: " + course);
    }

    private void updateCourseFlow() throws EntityNotFoundException, InvalidInputException {
        int courseId = readInt("Enter course ID to update: ");
        System.out.print("New course name: ");
        String courseName = scanner.nextLine();
        System.out.print("New description: ");
        String description = scanner.nextLine();
        int durationInWeeks = readInt("New duration in weeks: ");

        Course updatedCourse = courseService.updateCourse(courseId, courseName, description, durationInWeeks);
        System.out.println("Course updated successfully: " + updatedCourse);
    }

    private void activateCourseFlow() throws EntityNotFoundException, InvalidInputException {
        int courseId = readInt("Enter course ID to activate: ");
        Course course = courseService.activateCourse(courseId);
        System.out.println("Course activated successfully: " + course);
    }

    private void deactivateCourseFlow() throws EntityNotFoundException, InvalidInputException {
        int courseId = readInt("Enter course ID to deactivate: ");
        Course course = courseService.deactivateCourse(courseId);
        System.out.println("Course deactivated successfully: " + course);
    }

    //EnrolementFlow Util Methods
    private void enrollStudentFlow() throws EntityNotFoundException, InvalidInputException {
        int studentId = readInt("Enter student ID: ");
        int courseId = readInt("Enter course ID: ");

        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created successfully: " + enrollment);
    }

    private void viewStudentEnrollmentsFlow() throws EntityNotFoundException, InvalidInputException {
        int studentId = readInt("Enter student ID: ");
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found for this student.");
            return;
        }

        System.out.println("Enrollments for student ID " + studentId + ":");
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private void viewAllEnrollmentsFlow() {
        List<Enrollment> enrollments = enrollmentService.listAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        System.out.println("All enrollments:");
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private void updateEnrollmentStatusFlow(EnrollmentStatus status) throws EntityNotFoundException, InvalidInputException {
        int enrollmentId = readInt("Enter enrollment ID: ");
        Enrollment enrollment = enrollmentService.updateEnrollmentStatus(enrollmentId, status);
        System.out.println("Enrollment updated successfully: " + enrollment);
    }

    //TrinerFlow Util Methods

    private void addTrainerFlow() throws InvalidInputException, EntityNotFoundException {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Show available courses so user knows what can be mapped
        printAvailableCourses();

        System.out.print("Enter course IDs to map (comma-separated, or press Enter to skip): ");
        String courseInput = scanner.nextLine();
        List<Integer> courseIds = TrainerService.parseCourseIds(courseInput);

        Trainer trainer;
        if (courseIds.isEmpty()) {
            trainer = trainerService.addTrainer(firstName, lastName, email);
            System.out.println("Trainer added with no courses (can be updated later).");
        } else {
            trainer = trainerService.addTrainer(firstName, lastName, email, courseIds);
        }
        System.out.println("Trainer added successfully: " + trainer);
    }

    private void viewTrainersFlow() {
        List<Trainer> trainers = trainerService.listTrainers();
        if (trainers.isEmpty()) {
            System.out.println("No trainers found.");
            return;
        }

        System.out.println("Trainers:");
        for (Trainer trainer : trainers) {
            System.out.println(trainer);
        }
    }

    private void searchTrainerFlow() throws EntityNotFoundException, InvalidInputException {
        int trainerId = readInt("Enter trainer ID: ");
        Trainer trainer = trainerService.findTrainerById(trainerId);
        System.out.println("Trainer found: " + trainer);
    }

    private void updateTrainerDetailsFlow() throws EntityNotFoundException, InvalidInputException {
        int trainerId = readInt("Enter trainer ID to update: ");
        System.out.print("New first name: ");
        String firstName = scanner.nextLine();
        System.out.print("New last name: ");
        String lastName = scanner.nextLine();
        System.out.print("New email: ");
        String email = scanner.nextLine();

        Trainer trainer = trainerService.updateTrainer(trainerId, firstName, lastName, email);
        System.out.println("Trainer updated successfully: " + trainer);
    }

    private void updateTrainerCoursesFlow() throws EntityNotFoundException, InvalidInputException {
        int trainerId = readInt("Enter trainer ID: ");
        Trainer trainer = trainerService.findTrainerById(trainerId);
        System.out.println("Current trainer: " + trainer);

        printAvailableCourses();

        System.out.print("Enter new course IDs to map (comma-separated, or press Enter to clear all): ");
        String courseInput = scanner.nextLine();
        List<Integer> courseIds = TrainerService.parseCourseIds(courseInput);

        trainer = trainerService.updateTrainerCourses(trainerId, courseIds);
        if (courseIds.isEmpty()) {
            System.out.println("All courses removed from trainer.");
        }
        System.out.println("Trainer courses updated: " + trainer);
    }

    private void removeTrainerFlow() throws EntityNotFoundException, InvalidInputException {
        int trainerId = readInt("Enter trainer ID to remove: ");
        trainerService.removeTrainer(trainerId);
        System.out.println("Trainer with ID " + trainerId + " removed successfully.");
    }

    private void assignTrainerToCourseFlow() throws EntityNotFoundException, InvalidInputException {
        int courseId = readInt("Enter course ID: ");
        int trainerId = readInt("Enter trainer ID to assign: ");
        Course course = courseService.assignTrainerToCourse(courseId, trainerId);
        System.out.println("Trainer assigned to course successfully: " + course);
    }

    //HelperMehtods

    private void printAvailableCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("  (No courses available yet — you can add courses later and update the trainer.)");
        } else {
            System.out.println("  Available courses:");
            for (Course course : courses) {
                System.out.println("    " + course);
            }
        }
    }

    private int readInt(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String value = scanner.nextLine();
        return InputValidator.parsePositiveInt(value, prompt.replace(":", ""));
    }
}

