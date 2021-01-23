package ru.itis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.itis.filter.AuthFilter;
import ru.itis.repository.MessageRepo;
import ru.itis.repository.UserRepository;
import ru.itis.repository.jdbc.MessageRepoImpl;
import ru.itis.repository.jdbc.UserRepositoryImpl;
import ru.itis.service.*;

//LabWork_war
@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "ru.itis")
public class AppConfiguration {

    @Autowired
    private Environment environment;
    @Bean
    public HikariDataSource hikariDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("db.hikari.max-pool-size")));
        hikariConfig.setUsername(environment.getProperty("db.username"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver.classname"));

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }
    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        resolver.setContentType("text/html;charset=UTF-8");

        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/ftl/");
        return configurer;
    }

    @Bean
    public MessageRepo messageRepoImpl(){
        return new MessageRepoImpl(hikariDataSource());
    }

    @Bean
    public IMessageService messageServiceImpl(){
        return new MessageService(messageRepoImpl(),userServiceImpl());
    }
    @Bean
    public UserRepository userRepoImpl(){
        return new UserRepositoryImpl(hikariDataSource());
    }
    @Bean
    public IUserService userServiceImpl(){
        return new UserService(userRepoImpl());
    }
    @Bean
    public UserService userServiceImp(){
        return new UserService(userRepoImpl());
    }
    @Bean
    public IChatService chatServiceImpl(){
        return new ChatService(userRepoImpl());
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }
  /*  @Bean
    public CsrfFilter csrfFilter(){
        return new CsrfFilter();
    }*/



}
