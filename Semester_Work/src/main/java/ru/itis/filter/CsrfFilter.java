package ru.itis.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.itis.model.dto.UserLoginDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@WebFilter("/*")
public class CsrfFilter implements Filter {
    private FilterConfig config;
    private WebApplicationContext springContext;
    private HashSet<String> csrfTokens;

    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        csrfTokens = new HashSet<String>();
        objectMapper = new ObjectMapper();

    springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

      /*  if (httpRequest.getMethod().equals("POST")) {
            String requestCsrf = httpRequest.getParameter("_csrf_token");
            log.info(requestCsrf);
            if (((Set<String>) httpRequest.getSession(false).getAttribute("_csrf_tokens")).contains(requestCsrf)) {
                chain.doFilter(request, response);
                return;
            } else {
                log.info("csrf token {}, несовпал или отсутсвует ",requestCsrf);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;
            }                //не работает
        }*/


        if(httpRequest.getMethod().equals("GET")){
            String csrf = UUID.randomUUID().toString();
            request.setAttribute("_csrf_token",csrf);
            if( httpRequest.getSession().getAttribute("_csrf_tokens")==null){
                log.info("csrf tokens hashset is null");
                csrfTokens = new HashSet<>();//если там ничего не было, то я создам новый сет csrf токенов
   csrfTokens.add(csrf);
                httpRequest.getSession().setAttribute("_csrf_tokens",csrfTokens);


            }else {
                log.info("csrf tokens hashset is not null");
                csrfTokens = (HashSet<String>) httpRequest.getSession().getAttribute("_csrf_tokens");
   csrfTokens.add(csrf);
                httpRequest.getSession().setAttribute("_csrf_tokens",csrfTokens); // а если же что то было, то возьму действующий

            }
            csrfTokens.add(csrf); // и положу в него токен
            httpRequest.getSession().setAttribute("_csrf_tokens",csrfTokens);
            log.info("puted csrf roken {}",csrf);
            log.info("_csrf_tokens {}",csrfTokens);

            csrfTokens = new HashSet<>();//в конце обновлю




        }

        log.info("Log from csrfFilter");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
