package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final ArrayList<Course> courses = new ArrayList<>();
    private TrainerService trainerService;

    public CourseService() {
    }

    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) throws InvalidInputException {
        Course course = new Course(
                IdGenerator.getNextCourseId(),
                InputValidator.requireNonBlank(courseName, "Course name"),
                InputValidator.requireNonBlank(description, "Description"),
                durationInWeeks,
                true
        );
        courses.add(course);
        return course;
    }

    public Course updateCourse(int courseId, String courseName, String description, int durationInWeeks)
            throws EntityNotFoundException, InvalidInputException {
        Course course = findCourseById(courseId);
        course.setCourseName(InputValidator.requireNonBlank(courseName, "Course name"));
        course.setDescription(InputValidator.requireNonBlank(description, "Description"));
        course.setDurationInWeeks(durationInWeeks);
        return course;
    }

    public Course activateCourse(int courseId) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        course.setActive(true);
        return course;
    }

    public Course deactivateCourse(int courseId) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        course.setActive(false);
        return course;
    }

    public Course assignTrainerToCourse(int courseId, int trainerId) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        if (trainerService != null) {
            trainerService.findTrainerById(trainerId); // validate trainer exists
        }
        course.setTrainerId(trainerId);
        return course;
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }

    public Course findCourseById(int courseId) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        throw new EntityNotFoundException("Course with ID " + courseId + " was not found.");
    }

    Course findActiveCourseById(int courseId) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        if (!course.isActive()) {
            throw new EntityNotFoundException("Course with ID " + courseId + " is inactive.");
        }
        return course;
    }
}

