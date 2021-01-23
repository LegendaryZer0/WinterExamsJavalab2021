package ru.itis.servlets.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.model.dto.UserLoginDto;
import ru.itis.model.dto.UserSignInDto;
import ru.itis.service.IUserService;
import ru.itis.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/*@Slf4j
@Configurable
@Controller
@WebServlet(value = "/login"
)

public class ValidationServlet extends HttpServlet {
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userServiceImpl;
    private WebApplicationContext springContext;


    private boolean isSaved;
    private User user;
    UserLoginDto userLoginDto;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
       *//* final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this)*//*
    }
  *//*  @Autowired
    public ValidationServlet(UserService userServiceImpl){
        this.userServiceImpl= userServiceImpl;
    }*//*

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("test",req.getSession());

        getServletContext().getRequestDispatcher("/WEB-INF/ftl/Login.ftlh").forward(req, resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        Gson gson = new GsonBuilder()
                .create();

        try {
            userLoginDto = gson.fromJson(req.getReader(), UserLoginDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user = userLoginDto.getUser();
        isSaved=userLoginDto.getIsChecked();
        log.info("USERLOGINDTO{}",userLoginDto.toString());

        User finUser = userServiceImpl.getUser(user.getEmail(), user.getPassword());

        log.info(req.getParameter("chex"));
        log.info("Пользователь нажал Remember me{}",isSaved);

        req.getSession().setAttribute("UserSignInDto", UserSignInDto.builder().user(finUser).saveMe(isSaved).build());

        log.info("from validationServlet {}", finUser.toString());

        if (finUser.getEmail() != null) {
            String uuid = UUID.randomUUID().toString();
            resp.addCookie(new Cookie("Auth", uuid));
            finUser.setTechnicalInfo(TechnicalInfo.builder().uuid(UUID.fromString(uuid)).build());
            userServiceImpl.updateUserUUID(finUser);
            req.getSession().setAttribute("user", finUser);
            getServletContext().setAttribute("user", finUser);
            if(userLoginDto.getIsChecked()){
                resp.addCookie(new Cookie("isRemembered","true"));
            }
            resp.setStatus(302);
            try {
                resp.sendRedirect("http://localhost:8080/LabWork_war/selfProfile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            PrintWriter out = null;
            try {
                out = resp.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = "<h1  style=\"color: red\">Неправильное имя пользователя или пароль<h1>";
            gson = new Gson();

            System.out.println(gson.toJson(s));

            log.info(gson.toJson(s));
            out.println(gson.toJson(s));
            out.flush();
            *//*getServletContext().getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(req, resp);*//*
            *//*req.getSession().setAttribute("fail",true);*//*

            *//*getServletContext().getRequestDispatcher("/WEB-INF/classes/static/Fail.html").forward(req,resp)*//*
        }

    }


}*/
