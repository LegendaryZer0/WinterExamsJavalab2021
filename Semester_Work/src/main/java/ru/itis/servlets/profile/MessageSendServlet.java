package ru.itis.servlets.profile;

/*
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itis.model.Message;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.service.IMessageService;
import ru.itis.service.IUserService;
import ru.itis.service.MessageService;
import ru.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@Slf4j
@Configurable
@Controller
@WebServlet("/sendMessage")
public class MessageSendServlet extends HttpServlet {
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
    private WebApplicationContext springContext;

    @Override
    public void init(ServletConfig config) throws ServletException {

    */
/*    ServletContext servletContext = config.getServletContext();
        messageService = (MessageService) servletContext.getAttribute("messageService");
        userService = (UserService) servletContext.getAttribute("usersService");*//*

        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = (User) req.getSession().getAttribute("user");
        log.info(user.toString());

        userTo = userService.getUserByEmail(User.builder()
                .technicalInfo(TechnicalInfo
                        .builder()
                        .build())
                .email(req.getParameter("to-email"))
                .nickname(req.getParameter("to-nickname"))
                .phone(req.getParameter("to-phone"))
                .build());
        if(userTo==null){
            resp.sendRedirect(req.getContextPath() + "/yourProfile");
            return;
        }
        message = Message.builder().from(user).to(userTo).date(new Timestamp(System.currentTimeMillis())).message(req.getParameter("message")).build();
        log.info(message.toString());
        messageService.sendMessage(message);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/Profile.ftlh").forward(req, resp);


    }
}
*/
