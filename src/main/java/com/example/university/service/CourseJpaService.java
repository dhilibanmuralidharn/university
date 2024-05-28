package com.example.university.service;

import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.model.Student;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.ProfessorJpaRepository;
import com.example.university.repository.StudentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseJpaService implements CourseRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;
    @Autowired
    private StudentJpaRepository studentJpaRepository;
    @Autowired
    private ProfessorJpaRepository professorJpaRepository;
    @Override
    public ArrayList<Course> getCourses() {
        List<Course> courseList = courseJpaRepository.findAll();
        ArrayList<Course> courses = new ArrayList<>(courseList);
        return courses;
    }

    @Override
    public Course getCourseById(int courseId) {
        try{
            Course course = courseJpaRepository.findById(courseId).get();
            return course;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course addCourse(Course course) {
        try {
            List<Integer> studentIds = new ArrayList<>();
            for (Student student : course.getStudents()){
                studentIds.add(student.getStudentId());
            }
            List<Student> students = studentJpaRepository.findAllById(studentIds);
            if (studentIds.size() != students.size()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            course.setStudents(students);

            Professor professor = course.getProfessor();
            int professorId = professor.getProfessorId();
            professor = professorJpaRepository.findById(professorId).get();
            course.setProfessor(professor);


            return courseJpaRepository.save(course);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Course updateCourse(int courseId, Course course) {
        try{
            Course newCourse = courseJpaRepository.findById(courseId).get();
            if (course.getCourseName() != null){
                newCourse.setCourseName(course.getCourseName());
            }
            if (course.getCredits() != 0){
                newCourse.setCredits(course.getCredits());
            }
            if (course.getProfessor() != null){
                Professor professor = course.getProfessor();
                int professorId = professor.getProfessorId();
                Professor newProfessor =professorJpaRepository.findById(professorId).get();
                newCourse.setProfessor(newProfessor);
            }
            if (course.getStudents() != null){
                List<Integer> studentIds = new ArrayList<>();
                for (Student student : course.getStudents()){
                    studentIds.add(student.getStudentId());
                }
                List<Student> students = studentJpaRepository.findAllById(studentIds);
                if (studentIds.size() != students.size()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                newCourse.setStudents(students);
            }
            return courseJpaRepository.save(newCourse);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCourse(int courseId) {
        try{
            courseJpaRepository.deleteById(courseId);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Student> getCourseStudent(int courseId) {
        try{
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getStudents();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor getCourseProfessor(int courseId) {
        try{
            Course course = courseJpaRepository.findById(courseId).get();
            return course.getProfessor();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
