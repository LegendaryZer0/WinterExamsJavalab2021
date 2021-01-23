/*
package ru.itis.servlets.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itis.model.User;
import ru.itis.service.IMessageService;
import ru.itis.service.IUserService;
import ru.itis.service.MessageService;
import ru.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@WebServlet("/messenger")
public class MessendjerViewServlet extends HttpServlet {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
    @Autowired
    @Qualifier("messageServiceImpl")
    private IMessageService messageService;
    private User user;
    private WebApplicationContext springContext;

    @Override
    public void init(ServletConfig config) throws ServletException {


        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {



            user = req.getSession().getAttribute("user") == null ?
                    userService.findUser(UUID.fromString(Arrays.stream(req.getCookies()).filter(x -> x.getName().equals("Auth")).map(Cookie::getValue).findFirst().orElseThrow(NullPointerException::new)))
                    : (User) req.getSession().getAttribute("user");
            List<User> users = userService.findAllUsersChats(user.getId());
            if (users.isEmpty()) {
                users.add(user);
            }
            log.info(user.toString());
            req.getSession().setAttribute("ListChats", users);
            req.getSession().setAttribute("mainUser", user);
            log.info("Users chats {}", users);
            getServletContext().getRequestDispatcher("/WEB-INF/ftl/Messenger.ftlh").forward(req, resp);
        } catch (NullPointerException | ServletException | IOException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    public User withoutChat() {
        return new User();
    }
}
*/
