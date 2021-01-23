/*
package ru.itis.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ru.itis.repository.MessageRepo;
import ru.itis.repository.UserRepository;
import ru.itis.repository.jdbc.MessageRepoImpl;
import ru.itis.repository.jdbc.UserRepositoryImpl;
import ru.itis.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@WebListener
@Slf4j
public class CustomServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/db.properties"));
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        servletContext.setAttribute("dataSource", dataSource);

        UserRepository usersRepository = new UserRepositoryImpl(dataSource);

        IUserService userService = new UserService(usersRepository);
        MessageRepo messageRepo = new MessageRepoImpl(dataSource, userService);
        IMessageService messageService = new MessageService(messageRepo);
        IChatService chatService = new ChatService(usersRepository);
        servletContext.setAttribute("usersService", userService);
        servletContext.setAttribute("chatService", chatService);
        servletContext.setAttribute("messageService", messageService);



    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
*/

