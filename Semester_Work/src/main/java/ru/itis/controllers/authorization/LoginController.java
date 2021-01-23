package ru.itis.controllers.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.model.dto.UserLoginDto;
import ru.itis.service.IUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    private Cookie cookie;
    String s = "<h1  style=\"color: red\">Неправильный логин или пароль<h1>";

    private boolean isSaved;
    private User user;
    UserLoginDto userLoginDto;


    @GetMapping("/login")
    public String getLogin(){
        log.info("Отдаю страницу логина");
        return "Login";
    }
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {


        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        user = userLoginDto.getUser();
        isSaved=userLoginDto.getIsChecked();
        log.info("USERLOGINDTO{}",userLoginDto.toString());

        User finUser = userServiceImpl.getUser(user.getEmail(), user.getPassword());

        log.info("Пользователь нажал Remember me{}",isSaved);

        if (finUser.getEmail() != null) {
            String uuid = UUID.randomUUID().toString();
            cookie=new Cookie("Auth", uuid);

            if(userLoginDto.getIsChecked()){
                cookie.setMaxAge(360*360*360);
            }else   {cookie.setMaxAge(360*60*60);}
            response.addCookie(cookie);
            finUser.setTechnicalInfo(TechnicalInfo.builder().uuid(UUID.fromString(uuid)).build());
            userServiceImpl.updateUserUUID(finUser);
            request.getSession().setAttribute("user", finUser);


            response.setStatus(302);

            }
        return s;
    }


}
