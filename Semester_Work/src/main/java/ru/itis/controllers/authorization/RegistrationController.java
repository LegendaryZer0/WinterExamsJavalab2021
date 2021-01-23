package ru.itis.controllers.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.service.IUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Controller
public class RegistrationController {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService service;
    String mass[] ={"Введенный пароль не совпадает с оригиналом", "Неправильный логин или пароль"};

    @PostMapping("/register")
    @ResponseBody
    public String  registration(HttpServletResponse response, Model model,@RequestBody RegistrationDto dto,HttpServletRequest request){
        if(!dto.login.equals(dto.confirm)) {response.setStatus(200) ;return "<h1  style=\"color: red\">" + mass[0]+"<h1>";}



        log.info("РЕГИСТРАЦИЯ: полученные пароль и логин{}, {}",dto.password,dto.login);
        if (dto.login != null && dto.password != null) {
            UUID uuid = UUID.randomUUID();
            User user = User.builder()
                    .email(dto.login)
                    .password(dto.password)
                    .technicalInfo(TechnicalInfo
                            .builder()
                            .uuid(uuid)
                            .build())
                    .build();

            if (!service.addUser(user)) {
                return "<h1  style=\"color: red\">" + mass[1]+"<h1>";
            }else {
                request.getSession().setAttribute("user", service.getUserByEmail(user));
                Cookie cookie =new Cookie("Auth", uuid.toString());
                cookie.setMaxAge(60*360*60*60);
                response.addCookie(cookie);
                response.setStatus(302);
                return "Успешно";
            }
        }else {
            return "<h1  style=\"color: red\">" + mass[1]+"<h1>";
        }

    }
    @GetMapping("/register")
    public ModelAndView getRegistration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Registration");
        return modelAndView;
    }
    private static class RegistrationDto{
        private String login;
        private String password;
        private String confirm;
    }

}
