package ru.itis.servlets.registration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
/*

@Slf4j
@WebServlet("/register")
@Controller
public class RegistrationServlet extends HttpServlet {
    String login;
    String password;
    String password_confirm;
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService service;
    private Gson gson;
    private WebApplicationContext springContext;


    @Override
    public void init(ServletConfig config) throws ServletException {
        gson = new GsonBuilder().create();
      */
/*  ServletContext servletContext = config.getServletContext();
        service = (UserService) servletContext.getAttribute("usersService");
        super.init(config);*//*

        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        login = req.getParameter("login");
        password = req.getParameter("password");

        password_confirm=req.getParameter("confirm");
        log.info("Отправленный павторный пароль{}",password_confirm);
        if(!password.equals(password_confirm)){
            duplMapper(resp,"Введенный повторный пароль не совпадает с оригиналом");
            return;
        }
        log.info("РЕГИСТРАЦИЯ: полученные пароль и логин{}, {}",password,login);
        if (login != null && password != null) {
            UUID uuid = UUID.randomUUID();
            User user = User.builder()
                    .email(login)
                    .password(password)
                    .technicalInfo(TechnicalInfo
                            .builder()
                            .uuid(uuid)
                            .build())
                    .build();

            if (!service.addUser(user)) {
                duplMapper(resp,"Неправильный логин или пароль");
            } else {
                System.out.println("complete");
                req.getSession().setAttribute("user", service.getUserByEmail(user));
                Cookie cookie =new Cookie("Auth", uuid.toString());
                cookie.setMaxAge(60*360*60*60);
                resp.addCookie(cookie);
                resp.addCookie(new Cookie("isRemembered","false"));
                resp.setStatus(302);
                resp.sendRedirect(req.getContextPath() + "/selfProfile");

            }
        } else {
            duplMapper(resp,"Неправильный логин или пароль");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/Registration.ftlh").forward(req, resp);
        }

    }

    private void duplMapper(HttpServletResponse resp,String a) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("utf8");
        resp.setContentType("application/json");
        String s = "<h1  style=\"color: red\">" + a+"<h1>";
        Gson gson = new Gson();
        System.out.println(gson.toJson(s));
        log.info(gson.toJson(s));
        out.println(gson.toJson(s));
        out.flush();
    }


}
*/
