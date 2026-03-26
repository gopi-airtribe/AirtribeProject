package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final ArrayList<Student> students = new ArrayList<>();

    public Student addStudent(String firstName, String lastName, String email, String batch) throws InvalidInputException {
        Student student = new Student(
                IdGenerator.getNextStudentId(),
                InputValidator.requireNonBlank(firstName, "First name"),
                InputValidator.requireNonBlank(lastName, "Last name"),
                InputValidator.normalizeEmail(email),
                InputValidator.requireNonBlank(batch, "Batch")
        );
        students.add(student);
        return student;
    }

    public Student addStudent(String firstName, String lastName, String batch) throws InvalidInputException {
        Student student = new Student(
                IdGenerator.getNextStudentId(),
                InputValidator.requireNonBlank(firstName, "First name"),
                InputValidator.requireNonBlank(lastName, "Last name"),
                InputValidator.requireNonBlank(batch, "Batch")
        );
        students.add(student);
        return student;
    }

    public Student addStudent(Student student) throws InvalidInputException {
        if (student == null) {
            throw new InvalidInputException("Student cannot be null.");
        }
        student.setId(IdGenerator.getNextStudentId());
        student.setFirstName(InputValidator.requireNonBlank(student.getFirstName(), "First name"));
        student.setLastName(InputValidator.requireNonBlank(student.getLastName(), "Last name"));
        student.setBatch(InputValidator.requireNonBlank(student.getBatch(), "Batch"));
        student.setEmail(InputValidator.normalizeEmail(student.getEmail()));
        student.setActive(true);
        students.add(student);
        return student;
    }

    public Student updateStudent(int studentId, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(studentId);
        student.setFirstName(InputValidator.requireNonBlank(firstName, "First name"));
        student.setLastName(InputValidator.requireNonBlank(lastName, "Last name"));
        student.setEmail(InputValidator.normalizeEmail(email));
        student.setBatch(InputValidator.requireNonBlank(batch, "Batch"));
        return student;
    }

    public void removeStudent(int studentId) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        students.remove(student);
    }

    public Student deactivateStudent(int studentId) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        student.setActive(false);
        return student;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(int studentId) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student with ID " + studentId + " was not found.");
    }

    Student findActiveStudentById(int studentId) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        if (!student.isActive()) {
            throw new EntityNotFoundException("Student with ID " + studentId + " is inactive.");
        }
        return student;
    }
}

