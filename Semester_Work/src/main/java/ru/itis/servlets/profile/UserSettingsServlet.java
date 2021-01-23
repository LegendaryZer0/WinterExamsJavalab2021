package ru.itis.servlets.profile;
/*

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.service.IUserService;
import ru.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@WebServlet("/usettings")
@Controller
public class UserSettingsServlet extends HttpServlet {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = User.builder()
                .technicalInfo(TechnicalInfo
                        .builder()
                        .uuid(UUID.fromString(Arrays.stream(req.getCookies()).filter(x -> x.getName().equals("Auth")).map(x -> x.getValue()).findFirst().get()))
                        .build())

                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .nickname(req.getParameter("nickname"))
                .phone(req.getParameter("phone"))
                .build();
        log.info(user.toString());

        user =userService.updateUser(user);
        if(user!=null) {
            req.getSession().setAttribute("user", user);
            log.info(user.toString());
            resp.sendRedirect(req.getContextPath() + "/selfProfile");
        }else {
            resp.sendRedirect(req.getContextPath() + "/myProfile");
        }
    }
}
*/
