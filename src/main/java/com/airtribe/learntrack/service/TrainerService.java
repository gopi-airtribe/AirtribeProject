package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Trainer;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class TrainerService {
    private final ArrayList<Trainer> trainers = new ArrayList<>();
    private CourseService courseService;

    public TrainerService() {
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Adds a trainer. Courses list starts empty — use updateTrainerCourses() to map courses.
     */
    public Trainer addTrainer(String firstName, String lastName, String email) throws InvalidInputException {
        Trainer trainer = new Trainer(
                IdGenerator.getNextTrainerId(),
                InputValidator.requireNonBlank(firstName, "First name"),
                InputValidator.requireNonBlank(lastName, "Last name"),
                InputValidator.normalizeEmail(email)
        );
        trainers.add(trainer);
        return trainer;
    }

    /**
     * Adds a trainer and immediately maps the given course IDs.
     * If courseIds is empty, the trainer is created with no courses.
     */
    public Trainer addTrainer(String firstName, String lastName, String email, List<Integer> courseIds)
            throws InvalidInputException, EntityNotFoundException {
        Trainer trainer = addTrainer(firstName, lastName, email);
        if (courseIds != null && !courseIds.isEmpty()) {
            mapCoursesToTrainer(trainer, courseIds);
        }
        return trainer;
    }

    public Trainer updateTrainer(int trainerId, String firstName, String lastName, String email)
            throws EntityNotFoundException, InvalidInputException {
        Trainer trainer = findTrainerById(trainerId);
        trainer.setFirstName(InputValidator.requireNonBlank(firstName, "First name"));
        trainer.setLastName(InputValidator.requireNonBlank(lastName, "Last name"));
        trainer.setEmail(InputValidator.normalizeEmail(email));
        return trainer;
    }

    /**
     * Replaces the courses mapped to a trainer.
     * Pass an empty list to clear all course mappings.
     */
    public Trainer updateTrainerCourses(int trainerId, List<Integer> courseIds)
            throws EntityNotFoundException, InvalidInputException {
        Trainer trainer = findTrainerById(trainerId);
        if (courseIds == null || courseIds.isEmpty()) {
            trainer.setCourses(new ArrayList<>());
        } else {
            mapCoursesToTrainer(trainer, courseIds);
        }
        return trainer;
    }

    public void removeTrainer(int trainerId) throws EntityNotFoundException {
        Trainer trainer = findTrainerById(trainerId);
        trainers.remove(trainer);
    }

    public List<Trainer> listTrainers() {
        return new ArrayList<>(trainers);
    }

    public Trainer findTrainerById(int trainerId) throws EntityNotFoundException {
        for (Trainer trainer : trainers) {
            if (trainer.getId() == trainerId) {
                return trainer;
            }
        }
        throw new EntityNotFoundException("Trainer with ID " + trainerId + " was not found.");
    }

    /**
     * Parses a comma-separated string of course IDs into a list of integers.
     * Returns an empty list if the input is blank (no courses to map).
     */
    public static List<Integer> parseCourseIds(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = input.trim().split(",");
        ArrayList<Integer> ids = new ArrayList<>();
        for (String part : parts) {
            String s = part.trim();
            if (!s.isEmpty()) {
                try {
                    ids.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    // skip invalid entries silently
                }
            }
        }
        return ids;
    }

    // ── private helper ──────────────────────────────────────────────────

    private void mapCoursesToTrainer(Trainer trainer, List<Integer> courseIds)
            throws EntityNotFoundException, InvalidInputException {
        if (courseService == null) {
            throw new InvalidInputException("CourseService is not available. Cannot map courses.");
        }
        ArrayList<Course> courses = new ArrayList<>();
        for (int courseId : courseIds) {
            Course course = courseService.findCourseById(courseId);
            courses.add(course);
        }
        trainer.setCourses(courses);
    }
}

