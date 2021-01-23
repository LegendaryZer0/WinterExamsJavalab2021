package ru.itis.servlets.messenger;
/*

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
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.service.ChatService;
import ru.itis.service.IChatService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
@Slf4j
@WebServlet("/giveUrl")
public class UrlUuidServlet extends HttpServlet {
    private Gson gson;
    private User userTo;
    private User userFrom;
    private User user;
    private Message message;
    private Long[] mass;
    @Autowired
    @Qualifier("chatServiceImpl")
    private IChatService chatService;
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

        gson = new GsonBuilder()
                .create();
        mass = gson.fromJson(req.getReader(), Long[].class);


        log.info(mass.toString());
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        log.info(chatService.getChatUUID(mass).toString());
        resp.getWriter().write(gson.toJson(chatService.getChatUUID(mass).toString()));
        resp.getWriter().flush();

    }
}
*/
