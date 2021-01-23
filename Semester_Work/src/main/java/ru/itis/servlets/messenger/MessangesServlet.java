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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebServlet("/getInfo")
@Controller
public class MessangesServlet extends HttpServlet {

    List messagesHistory;
    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
    @Autowired
    @Qualifier("messageServiceImpl")
    private IMessageService messageService;
    private User user;
    private Long id_from;
    private Long id_to;
    private Gson gson;
    private WebApplicationContext springContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        messagesHistory = new ArrayList();

        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            resp.setContentType("application/gson");
            Gson gson = new GsonBuilder()
                    .create();

            Pair o = gson.fromJson(req.getReader(), Pair.class);
            log.info(o.toString());
            id_from = o.id_from;
            id_to = o.id_to;
            PrintWriter printWriter = resp.getWriter();
            log.info("ID_FROM {},ID TO{}", id_from, id_to);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(gson.toJson(messageService.getAllMessagesBetweenTwoUsers(id_from, id_to)));
            resp.getWriter().flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
    static private class Pair {
        private long id_from;
        private long id_to;
    }
}
*/
