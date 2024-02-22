package code.controller;


import code.dao.UserDao;

import code.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequiredArgsConstructor

public class UserController {


    private final UserDao userDao;

    @GetMapping("/addUser")
    public String displayUser(Model model){
        List<User> allUsers =  userDao.findAll();
        model.addAttribute("users",allUsers);
        return "user/user_details";
    }

    /*@GetMapping("/userRegister")
    public ModelAndView listUser(){
        return new ModelAndView("user/user_details","users",new User());
    }*/

    @GetMapping("/userRegister")
    public ModelAndView userRegister(){
        return new ModelAndView("user/user_reg","user",new User());
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user,Model model,HttpServletRequest request){

        if(user.getName() == null || user.getName().isEmpty() || user.getEmail() == null || user.getEmail().isEmpty() ||
        user.getPassword() == null || user.getPassword().isEmpty()){
            model.addAttribute("error","Fields cannot be null");
            return "user/user_reg";
        }

        if(userDao.validEmail(user.getEmail()) > 0){
            model.addAttribute("error","Email already used");
            return "user/user_reg";
        }
        String confirmPass = request.getParameter("confirmPass");
        if(!user.getPassword().equals(confirmPass)){
            model.addAttribute("error","Password does not match");
            return "user/user_reg";
        }
            userDao.createUser(user);
            List<User> users  = userDao.findAll();
            model.addAttribute("users",users);
            return "user/user_details";

    }
    @GetMapping("/updateUser")
    public ModelAndView userUpdate(@RequestParam("userId") int userId){

        User user = userDao.findById(userId);
        ModelAndView modelAndView = new ModelAndView("user/user_update");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userObj", new User());

        return modelAndView;
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId")int userId, RedirectAttributes redirectAttributes, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("valid_user");
        User searchUser = userDao.findById(userId);

        if(user != null && user.getEmail().equals(searchUser.getEmail())){
            userDao.deleteUser(userId);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/";
        }else{
            userDao.deleteUser(userId);
            redirectAttributes.addFlashAttribute("deletedUserId", userId);
            return "redirect:/userList";
        }

    }
    @GetMapping("/userList")
    public String AfterDelete(Model model){
        List<User> users;
            users = userDao.findAll();
        model.addAttribute("users", users);
        return "user/user_details";
    }
    @GetMapping("/searchUser")
    public String searchUser(@RequestParam(name = "id", required = false) String userId,
                             @RequestParam(name = "name", required = false) String userName,
                             Model model) {

        int id = 0;
        if (userId != null && !userId.isEmpty()) {
            try {
                id = Integer.parseInt(userId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + userId);
            }
        }

        List<User> searchResults = userDao.findByIdOrUserName(id, userName);
        model.addAttribute("users", searchResults);

        return "user/user_details";
    }
}
