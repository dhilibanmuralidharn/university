package com.example.university.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int studentId;
    @Column(name ="name")
    private String studentName;
    @Column(name = "email")
    private String email;
    @ManyToMany (mappedBy = "students")
    @JsonIgnoreProperties("students")
    private List<Course> courses;

    public Student(){}

    public Student(int studentId, String studentName, String email, List<Course> courses){
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courses = courses;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
