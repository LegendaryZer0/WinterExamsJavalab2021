package ru.itis.controllers.messenger;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.service.IChatService;
import ru.itis.service.IMessageService;
import ru.itis.service.IUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class MessengerController {
    List messagesHistory;
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
    @Autowired
    @Qualifier("messageServiceImpl")
    private IMessageService messageService;
    private User user;

    @Autowired
    @Qualifier("chatServiceImpl")
    private IChatService chatService;


    @PostMapping("/getInfo")
    @ResponseBody
    public List<Message> getMesagesBetweenTwoUsers(@RequestBody Pair pair){
        log.info("Пара ID {}",pair);
        log.info("Отработал");
        List<Message> messages = messageService.getAllMessagesBetweenTwoUsers(pair.getId_from(), pair.getId_to());
        log.info("Сообщения{}",messages);
        return messages;
    }

    @PostMapping("/giveUrl")
    @ResponseBody
    public UUID getUUIDForUsers(@RequestBody Long[] data){
        return chatService.getChatUUID(data);

    }


    @GetMapping("/messenger")
    public String getAllUsersChats(HttpServletResponse response, HttpServletRequest request){
        user = request.getSession().getAttribute("user") == null ?
                userService.findUser(UUID.fromString(Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("Auth")).map(Cookie::getValue).findFirst().orElseThrow(NullPointerException::new)))
                : (User) request.getSession().getAttribute("user");
        List<User> users = userService.findAllUsersChats(user.getId());
        if (users.isEmpty()) {
            users.add(user);
        }
        log.info(user.toString());
        request.getSession().setAttribute("ListChats", users);
        request.getSession().setAttribute("mainUser", user);
        log.info("Users chats {}", users);
        return "Messenger";
    }











    @Data
    private  static  class Pair {
        private long id_from;
        private long id_to;
    }

}
