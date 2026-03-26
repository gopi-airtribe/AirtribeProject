package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import java.util.ArrayList;
import java.util.List;
public class LearnTrackServiceTest {
    public static void main(String[] args) throws Exception {
        LearnTrackServiceTest testHarness = new LearnTrackServiceTest();
        testHarness.shouldAddStudentUsingOverloadedConstructorPath();
        testHarness.shouldDeactivateStudent();
        testHarness.shouldEnrollStudentInActiveCourse();
        testHarness.shouldNotAllowDuplicateActiveEnrollment();
        testHarness.shouldUpdateEnrollmentStatus();
        testHarness.shouldCreateTrainerWithEmptyCourseList();
        testHarness.shouldUpdateTrainerCoursesAfterCoursesAdded();
        testHarness.shouldClearTrainerCourses();
        System.out.println("All LearnTrack service checks passed.");
    }

    void shouldAddStudentUsingOverloadedConstructorPath() throws InvalidInputException {
        StudentService studentService = new StudentService();
        Student student = studentService.addStudent("Asha", "Verma", "Batch A");

        assertTrue(student.getId() > 0, "Student ID should be generated.");
        assertEquals("Asha", student.getFirstName(), "First name should match.");
        assertEquals("Batch A", student.getBatch(), "Batch should match.");
        assertTrue(student.isActive(), "New student should be active.");
    }

    void shouldDeactivateStudent() throws InvalidInputException, EntityNotFoundException {
        StudentService studentService = new StudentService();
        Student student = studentService.addStudent("Ravi", "Kumar", "ravi@example.com", "Batch B");

        studentService.deactivateStudent(student.getId());

        assertFalse(studentService.findStudentById(student.getId()).isActive(), "Student should be inactive.");
    }

    void shouldEnrollStudentInActiveCourse() throws InvalidInputException, EntityNotFoundException {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
        Student student = studentService.addStudent("Isha", "Singh", "isha@example.com", "Batch C");
        Course course = courseService.addCourse("Core Java", "Java fundamentals", 8);

        Enrollment enrollment = enrollmentService.enrollStudent(student.getId(), course.getId());

        assertEquals(EnrollmentStatus.ACTIVE, enrollment.getStatus(), "Enrollment should start as ACTIVE.");
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(student.getId());
        assertEquals(1, enrollments.size(), "Student should have one enrollment.");
    }

    void shouldNotAllowDuplicateActiveEnrollment() throws InvalidInputException, EntityNotFoundException {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
        Student student = studentService.addStudent("Neha", "Patel", "neha@example.com", "Batch D");
        Course course = courseService.addCourse("DSA", "Data structures", 10);
        enrollmentService.enrollStudent(student.getId(), course.getId());

        try {
            enrollmentService.enrollStudent(student.getId(), course.getId());
            throw new AssertionError("Expected duplicate enrollment to fail.");
        } catch (InvalidInputException exception) {
            assertEquals(
                    "Student is already actively enrolled in this course.",
                    exception.getMessage(),
                    "Duplicate enrollment message should match."
            );
        }
    }

    void shouldUpdateEnrollmentStatus() throws InvalidInputException, EntityNotFoundException {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
        Student student = studentService.addStudent("Aman", "Gupta", "aman@example.com", "Batch E");
        Course course = courseService.addCourse("SQL", "Database basics", 6);
        Enrollment enrollment = enrollmentService.enrollStudent(student.getId(), course.getId());

        enrollmentService.updateEnrollmentStatus(enrollment.getId(), EnrollmentStatus.COMPLETED);

        assertEquals(
                EnrollmentStatus.COMPLETED,
                enrollmentService.findEnrollmentById(enrollment.getId()).getStatus(),
                "Enrollment status should update to COMPLETED."
        );
    }

    // ── Trainer-Course mapping tests ────────────────────────────────────

    void shouldCreateTrainerWithEmptyCourseList() throws InvalidInputException {
        CourseService courseService = new CourseService();
        TrainerService trainerService = new TrainerService();
        trainerService.setCourseService(courseService);

        // No courses exist yet — trainer should be created with empty course list
        Trainer trainer = trainerService.addTrainer("Raj", "Sharma", "raj@example.com");

        assertTrue(trainer.getId() > 0, "Trainer ID should be generated.");
        assertTrue(trainer.getCourses().isEmpty(), "Trainer should have no courses when none exist.");
    }

    void shouldUpdateTrainerCoursesAfterCoursesAdded() throws InvalidInputException, EntityNotFoundException {
        CourseService courseService = new CourseService();
        TrainerService trainerService = new TrainerService();
        trainerService.setCourseService(courseService);

        // Create trainer with no courses
        Trainer trainer = trainerService.addTrainer("Priya", "Reddy", "priya@example.com");
        assertTrue(trainer.getCourses().isEmpty(), "Trainer starts with empty course list.");

        // Add courses
        Course java = courseService.addCourse("Core Java", "Java fundamentals", 8);
        Course sql = courseService.addCourse("SQL", "Database basics", 6);

        // Map courses to trainer
        List<Integer> courseIds = new ArrayList<>();
        courseIds.add(java.getId());
        courseIds.add(sql.getId());
        trainer = trainerService.updateTrainerCourses(trainer.getId(), courseIds);

        assertEquals(2, trainer.getCourses().size(), "Trainer should now have 2 courses.");
        assertEquals("Core Java", trainer.getCourses().get(0).getCourseName(), "First course should be Core Java.");
        assertEquals("SQL", trainer.getCourses().get(1).getCourseName(), "Second course should be SQL.");
    }

    void shouldClearTrainerCourses() throws InvalidInputException, EntityNotFoundException {
        CourseService courseService = new CourseService();
        TrainerService trainerService = new TrainerService();
        trainerService.setCourseService(courseService);

        Course dsa = courseService.addCourse("DSA", "Data structures", 10);
        List<Integer> courseIds = new ArrayList<>();
        courseIds.add(dsa.getId());

        Trainer trainer = trainerService.addTrainer("Anil", "Kumar", "anil@example.com", courseIds);
        assertEquals(1, trainer.getCourses().size(), "Trainer should have 1 course after creation.");

        // Clear all courses by passing empty list
        trainer = trainerService.updateTrainerCourses(trainer.getId(), new ArrayList<>());
        assertTrue(trainer.getCourses().isEmpty(), "Trainer courses should be empty after clearing.");
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
        }
    }
}


