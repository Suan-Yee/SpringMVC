package code.controller;


import code.dao.CourseDao;
import code.dao.EnrollmentDao;
import code.model.RegisterForm;
import code.dao.StudentDao;
import code.model.Course;
import code.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller

public class StudentController {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final EnrollmentDao enrollmentDao;

    @GetMapping("/studentRegister")
    public ModelAndView studentRegister(){

        List<Course> courses = courseDao.selectByStatus();

        ModelAndView modelAndView = new ModelAndView("student/studentRegister");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("register", new RegisterForm());

        return modelAndView;

    }
    @PostMapping("/addStudent")
    public String student_detail(@ModelAttribute("register")RegisterForm rgForm, Model model){


        List<Integer> course_id  = rgForm.getCourses();
        Student student = rgForm.getStudent();
        Student add_student = studentDao.createStudent(student);
        System.out.println(add_student.getName() + add_student.getDob());

        for(Integer course : course_id){
            enrollmentDao.saveEnrollment(add_student.getId(),course);
        }
        List<Student> allStudents  = studentDao.findAllStudent();

        model.addAttribute("students",allStudents);

        return "student/student_details";
    }
    @GetMapping("/addStudent")
    public String showData(Model model){

        List<Student> allStudents  = studentDao.findAllStudent();
        model.addAttribute("students",allStudents);

        return "student/student_details";
    }
    @GetMapping("/updateStudent")
    public ModelAndView studentUpdate(@RequestParam("studentId") String studentId) {


        int id = 0;
        if (studentId != null && !studentId.isEmpty()) {

            try {
                id = Integer.parseInt(studentId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + studentId);
            }
        }
            Student d_student = studentDao.findById(id);
            List<Integer> listOfCourse  = enrollmentDao.findCourseByStudentId(d_student.getId());
            System.out.println(listOfCourse);
            List<String> course_name = new ArrayList<>();
            for(Integer i : listOfCourse){
                Course course  = courseDao.findById(i);
                course_name.add(course.getName());
            }
            System.out.println("Course name " + course_name);
            List<Course> courses = courseDao.selectByStatus();

            ModelAndView modelAndView = new ModelAndView("student/STU002-01");
            modelAndView.addObject("c_name",course_name);
            modelAndView.addObject("courses", courses);
            modelAndView.addObject("student", d_student);
            modelAndView.addObject("register", new RegisterForm());

            return modelAndView;
        }
    @PostMapping("/updateStudentAction")
    public String updateStudentAction(@ModelAttribute("register")RegisterForm reg,Model model,HttpServletRequest request){

        String gender = request.getParameter("gender");
        String education = request.getParameter("education");
        String[] select_course = request.getParameterValues("course");

        String str_id  = request.getParameter("hide");
        int id = 0;
        if (str_id != null && !str_id.isEmpty()) {

            try {
                id = Integer.parseInt(str_id);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + str_id);
            }
        }
        List<Integer> course_id = new ArrayList<>();

        List<Course> courses = courseDao.selectByStatus();
        for(Course course : courses){
            if(Arrays.asList(select_course).contains(course.getName())){
                course_id.add(course.getId());
            }
        }

        Student student = reg.getStudent();
        student.setId(id);
        student.setGender(gender);
        student.setEducation(education);
        Student add_student = studentDao.updateStudent(student);
        enrollmentDao.deleteCourseAndStudent(add_student.getId());
        for(Integer course : course_id){
            enrollmentDao.saveEnrollment(add_student.getId(),course);
        }
        List<Student> allStudents  = studentDao.findAllStudent();

        model.addAttribute("students",allStudents);

        return "student/student_details";

    }
   /* @GetMapping("/searchStudent")
    public String searchStudent(@RequestParam(name = "id", required = false) String studentId,
                                @RequestParam(name = "name", required = false) String studentName,
                                @RequestParam(name = "course",required = false)String studentCourse,
                                Model model) {

        int id = 0;
        if (studentId != null && !studentId.isEmpty()) {
            try {
                id = Integer.parseInt(studentId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + studentId);
            }
        }
        StudentSearch studentSearch = new StudentSearch(id,studentName,studentCourse);

        List<Student> students = studentDao.findAllStudent();
        model.addAttribute("students", students);
        return "student/student_details";

    }*/
    @GetMapping("/deleteStudent")
    public String deleteUser(@RequestParam("studentId")int studentId, RedirectAttributes redirectAttributes){
        enrollmentDao.deleteCourseAndStudent(studentId);
        studentDao.deleteStudent(studentId);
        redirectAttributes.addFlashAttribute("deletedUserId", studentId);
        return "redirect:/studentList";
    }
    @GetMapping("/studentList")
    public String AfterDelete(Model model){
        List<Student> students;
        students = studentDao.findAllStudent();
        model.addAttribute("students", students);
        return "student/student_details";
    }
   /* @GetMapping("/studentLists")
    public String studentLists(Model model){
        List<Student> students;
        students = studentDao.findAllStudent();
        model.addAttribute("students", students);
        return "student/student_details";
    }*/

    @GetMapping("/searchStudent")
    public String searchStudent(@RequestParam(name = "id", required = false) String studentId,
                                @RequestParam(name = "name", required = false) String studentName,
                                @RequestParam(name = "course", required = false) String studentCourse,
                                Model model) {

        if (studentId != null && !studentId.isEmpty()) {
            int id = 0;
            try {
                id = Integer.parseInt(studentId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + studentId);
            }
            Student student = studentDao.findById(id);
            model.addAttribute("students", Collections.singletonList(student));
        } else if (studentName != null && !studentName.isEmpty()) {
            List<Student> students = studentDao.findByName(studentName);
            model.addAttribute("students", students);
        } /*lse if (studentCourse != null && !studentCourse.isEmpty()) {
            List<Student> students = studentDao.findByCourse(studentCourse);
            model.addAttribute("students", students);
        }*/ else {
            List<Student> allStudents = studentDao.findAllStudent();
            model.addAttribute("students", allStudents);
        }

        return "student/student_details";
    }

}
