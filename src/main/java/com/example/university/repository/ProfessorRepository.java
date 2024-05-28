package com.example.university.repository;

import com.example.university.model.Course;
import com.example.university.model.Professor;

import java.util.ArrayList;
import java.util.List;

public interface ProfessorRepository {
    ArrayList<Professor> getProfessors();
    Professor getProfessorById(int professorId);
    Professor addProfessor (Professor professor);
    Professor updateProfessor (int professorId, Professor professor);
    void deleteProfessor (int professorId);
    List<Course> getProfessorCourse(int professorId);
}
