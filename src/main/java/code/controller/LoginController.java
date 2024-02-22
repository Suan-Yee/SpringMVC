package code.controller;

import code.dao.UserDao;
import code.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserDao userDao;

    @GetMapping("/")
    public ModelAndView loginUser(){
        return new ModelAndView("user/login","bean",new User());
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("bean") @Validated User user, ModelMap model, HttpSession session){

        if(user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()){
            model.addAttribute("error","Please Fill the require fields");
            return "user/login";
        }

        User loginUser = userDao.loginUser(user);

        if (loginUser == null) {
            model.addAttribute("error", "Wrong Credentials");
            return "user/login";
        }else{
            session.setAttribute("valid_user",loginUser);
        }
        return "redirect:welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


    @GetMapping("/changePassword")
    public ModelAndView showEmailModel(){

        return new ModelAndView("user/changePassword","user",new User());
    }
    @PostMapping ("/checkEmail")
    public String checkEmail(@ModelAttribute("user")User user, Model model){
        User users = userDao.loginUser(user);

        if(users == null){
            model.addAttribute("error","Wrong Credentials");
            return "user/changePassword";
        }else{
            return "redirect:/changeEmail";
        }
    }
    @GetMapping("/changeEmail")
    public ModelAndView changeEmail(){
        return new ModelAndView("user/change","user",new User());
    }
    @PostMapping("/changeEmailAndPassword")
    public String changeEmailAndPassword(@ModelAttribute("user")User user,HttpServletRequest request){
        User chg_user  = userDao.changeEmail(user,request);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";

    }

    @GetMapping("/welcome")
    public String welcome() {
        return "user/welcome";
    }

}
 /*if (result.hasErrors()) {
            model.addAttribute("error", "Please fill in the required fields.");
            return "/user/login";
        }*/