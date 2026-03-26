package com.airtribe.learntrack.entity;

import java.util.ArrayList;
import java.util.List;

public class Trainer extends Person {
    private List<Course> courses;

    public Trainer() {
        this.courses = new ArrayList<>();
    }

    public Trainer(int id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email);
        this.courses = new ArrayList<>();
    }

    public Trainer(int id, String firstName, String lastName, String email, List<Course> courses) {
        super(id, firstName, lastName, email);
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    private String getCourseNames() {
        if (courses == null || courses.isEmpty()) {
            return "none";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < courses.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(courses.get(i).getCourseName())
              .append("(ID:")
              .append(courses.get(i).getId())
              .append(")");
        }
        return sb.toString();
    }

    @Override
    public String getDisplayName() {
        return "Trainer " + super.getDisplayName() + " [Courses: " + getCourseNames() + "]";
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + getId() +
                ", name='" + getFirstName() + " " + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", courses=" + getCourseNames() +
                '}';
    }
}

