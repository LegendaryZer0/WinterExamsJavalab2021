package ru.itis.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itis.model.User;
import ru.itis.service.IUserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.UUID;

@Slf4j
@WebFilter("/*")

public class AuthFilter implements Filter {
    protected final String[] disabledUrls = {"/login", "/registration", "/register", "/security", "/js/login.js", "/js/registr.js",/*"/js/chat.js"*/};
    private FilterConfig config = null;
    private String sessionUUID;
    private WebApplicationContext springContext;

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
    private User user;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;

        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        boolean isProtect = false;
        for (String url : disabledUrls) {

            log.info(httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()));

            if (url.equals(httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()))) {
                isProtect = true;
                break;
            }
        }
        log.info("is protect{}", isProtect);
        if (!isProtect) {
            log.info("начинаю подробную проверку");
            user = (User) httpRequest.getSession().getAttribute("user");

            if (user == null) {
                log.info("В сессии юзера нет");
                if (httpRequest.getCookies() == null) {
                    log.info("И в куках нет");
        /*            Cookie cookie = new Cookie("isRemembered", "false");
                    cookie.setMaxAge(60 * 360 * 60 * 60);
                    httpResponse.addCookie(cookie);*/
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                    return;
                }


                for (Cookie i : httpRequest.getCookies()) {
                    log.info("Лог отдельной куки и её имя {} ИМЯ {}", i.toString(), i.getName());
                    if (i.getName().equals("Auth")) {
                        if (i.getValue() != null) {
                            user = userService.findUser(UUID.fromString(i.getValue()));
                            if (user.getTechnicalInfo() != null) {
                                httpRequest.getSession().setAttribute("user", user);
                                log.info("Пользователь хороший-всё есть ^_^");

                                chain.doFilter(request, response);
                                return;

                            } else {
                                log.info("В бд куков нет, у поользователя есть(устаревшийЯ)");
                                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                                return;
                            }
                        } else {
                            log.info("Сработал лог с внутреннего elce(КУКОВ У ПОЛЬЗОВАТЕЛЯ НЕТ");
                            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                            return;


                        }
                    }
                }
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;

            } else {

                log.info("Пропускаю дальше{}", user.toString());

                chain.doFilter(request, response);return;
            }


        } else {
            log.info("Этот путь не фильтруется сервлетом");

            chain.doFilter(request, response);return;
        }


    }

    @Override
    public void destroy() {

    }






}
