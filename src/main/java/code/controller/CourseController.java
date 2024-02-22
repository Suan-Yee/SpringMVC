package code.controller;

import code.dao.CourseDao;
import code.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseDao courseDao;

    @GetMapping("/courseRegister")
    public ModelAndView register(){
        System.out.println("Method reached 1");
        return new ModelAndView("course/course_reg","course",new Course());
    }
    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course")Course course){
        Course course1 = courseDao.createCourse(course);
        System.out.println(course1.getName());
        return "redirect:courseRegister";
    }
    @GetMapping("/courseDetails")
    public String courseDetails(Model model){
        List<Course> courses = courseDao.findAllCourse();
        model.addAttribute("courses",courses);

        return "course/course_details";
    }
    @GetMapping("/searchCourse")
    public String searchCourse(@RequestParam(name = "id", required = false) String courseId,
                               @RequestParam(name = "name", required = false) String courseName,
                               Model model){

        int id = 0;
        if (courseId != null && !courseId.isEmpty()) {
            try {
                id = Integer.parseInt(courseId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + courseId);
            }
        }

        List<Course> courses = courseDao.findByIdOrName(id,courseName);
        model.addAttribute("courses",courses);

        return "course/course_details";
    }
    @GetMapping("/courseStatus")
    public String courseStatus(@RequestParam(name = "courseId",required = false)String courseId, Model model, RedirectAttributes redirectAttributes){
        int id = 0;
        if (courseId != null && !courseId.isEmpty()) {
            try {
                id = Integer.parseInt(courseId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + courseId);
            }
        }
        Course course = courseDao.findById(id);
        Course updated_course = courseDao.changeStatus(id,course);
        model.addAttribute("status",updated_course.getStatus());
        redirectAttributes.addFlashAttribute("courseId", id);
        return "redirect:/courseDetails";
    }

}
