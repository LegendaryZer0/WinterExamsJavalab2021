package ru.itis.controllers.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.model.User;
import ru.itis.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

@Controller
@Slf4j
public class ProfileController {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
    private User user;


    @GetMapping("/selfProfile")
    public String getProfile(){
        return "Profile";
    }

    @PostMapping("/usettings")
    public String changeUserSettings(@RequestBody User user, HttpServletRequest request){
        log.info("User in changeSettings Controller{}",user);
        user.getTechnicalInfo()
                .setUuid(UUID.fromString(Arrays.stream(request.getCookies())
                        .filter(x -> x.getName().equals("Auth"))
                        .map(x -> x.getValue()).findFirst()
                        .get()));
        log.info("User after placed UUID{}",user);
        user =userService.updateUser(user);
        if(user!=null){
            request.getSession().setAttribute("user",user);
            return "Profile";
        }else return "redirect:/myProfile";

    }


}
