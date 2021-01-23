package ru.itis.controllers.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.model.Message;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.service.IMessageService;
import ru.itis.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
@Slf4j
@Controller
public class MessageSendController {
    private User userTo;
    private User userFrom;
    private User user;
    private Message message;
    @Autowired
    @Qualifier("messageServiceImpl")
    private IMessageService messageService;
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    @PostMapping("/sendMessage")
    public String sendMessage(HttpServletRequest request,@RequestParam(value = "message", required = false) String userMessage){
        user = (User) request.getSession().getAttribute("user");
        log.info(user.toString());

        userTo = userService.getUserByEmail(User.builder()
                .technicalInfo(TechnicalInfo
                        .builder()
                        .build())
                .email(request.getParameter("to-email"))
                .nickname(request.getParameter("to-nickname"))
                .phone(request.getParameter("to-phone"))
                .build());
        if(userTo==null){

            return "redirect:/yourProfile";
        }
        message = Message.builder().from(user).to(userTo).date(new Timestamp(System.currentTimeMillis())).message(userMessage).build();
        log.info(message.toString());
        messageService.sendMessage(message);
       return "Profile";
    }
}
