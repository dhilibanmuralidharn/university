package com.example.university.service;

import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.ProfessorJpaRepository;
import com.example.university.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorJpaService implements ProfessorRepository {
    @Autowired
    private ProfessorJpaRepository professorJpaRepository;
    @Autowired
    private CourseJpaRepository courseJpaRepository;
    @Override
    public ArrayList<Professor> getProfessors() {
        List<Professor> professors = professorJpaRepository.findAll();
        ArrayList<Professor> professors1 = new ArrayList<>(professors);
        return professors1;
    }

    @Override
    public Professor getProfessorById(int professorId) {
        try{
            Professor professor = professorJpaRepository.findById(professorId).get();
            return professor;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor addProfessor(Professor professor) {
        return professorJpaRepository.save(professor);
    }

    @Override
    public Professor updateProfessor(int professorId, Professor professor) {
        try {
            Professor newProfessor = professorJpaRepository.findById(professorId).get();
            if (professor.getProfessorName() != null){
                newProfessor.setProfessorName(professor.getProfessorName());
            }
            if (professor.getDepartment() != null){
                newProfessor.setDepartment(professor.getDepartment());
            }
            return professorJpaRepository.save(newProfessor);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProfessor(int professorId) {
        try{
            Professor professor = professorJpaRepository.findById(professorId).get();
            List<Course> courses = courseJpaRepository.findByprofessor(professor);
            for (Course course : courses){
                course.setProfessor(null);
                courseJpaRepository.save(course);
            }
            professorJpaRepository.deleteById(professorId);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getProfessorCourse(int professorId) {
        Professor newProfessor = professorJpaRepository.findById(professorId).get();
        try{
            List <Course> courses = courseJpaRepository.findByprofessor(newProfessor);
            return courses;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
