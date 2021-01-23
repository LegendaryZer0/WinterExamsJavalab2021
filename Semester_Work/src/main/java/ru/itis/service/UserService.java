package ru.itis.service;

import ru.itis.encryption.Iecnrypt;
import ru.itis.encryption.IencryptImpl;
import lombok.extern.slf4j.Slf4j;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.repository.jdbc.UserRepositoryImpl;


import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j

public class UserService implements IUserService {
    private UserRepository userRepository;
    private final Pattern emailPattern;
    private final Pattern passwordPattern;
    private Matcher matcher;
    private final Iecnrypt cipher = new IencryptImpl();

    private UserService() {
        emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");//RFC 5322
        passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public UserService(UserRepository userRepository) {
        this();
        this.userRepository = userRepository;
    }

    public boolean addUser(User user) {
        try {
            matcher = emailPattern.matcher(user.getEmail());
            if (matcher.matches()) {
                matcher = passwordPattern.matcher(user.getPassword());
                if (matcher.matches()) {
                    user.setPassword(cipher.encrypt(user.getPassword()));


                    userRepository.create(user);
                } else {
                    log.info("Пароль не соотвествует условию");
                    return false;}
            } else {
                log.info("Логин не соотвествует условию");
                return false;}
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByEmail(User user) {
        try {
            return userRepository.getUserByEmail(user);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }

    }

    public User getUser(String login, String password) {
        try {
            User user = userRepository.getUserByLogin(login);


            if (cipher.check(password, user.getPassword())) {
                return user;
            } else return new User();
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }

    }

    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    public User findUser(UUID uuid) {

        return userRepository.findUserByUUID(User.builder().technicalInfo(TechnicalInfo.builder().uuid(uuid).build()).build());

    }

    public List<User> findAllUsersChats(long id) {

        List<User> userList = userRepository.findAllUserChatById(id);
        return userRepository.findAllUserChatById(id);
    }

    public User updateUser(User user) {
        matcher = emailPattern.matcher(user.getEmail());
        if (matcher.matches()) {
            if(user.getPassword().equals(userRepository.getUserByEmail(user).getPassword())){
                log.info("Пользователь не менял пароля");
                userRepository.update(user);
                return userRepository.findUserByUUID(user);
            }
            matcher = passwordPattern.matcher(user.getPassword());
            if (matcher.matches()) {
                user.setPassword(cipher.encrypt(user.getPassword()));
                userRepository.update(user);
                return userRepository.findUserByUUID(user);


            } else {
                log.info("Пароль не соотвествует условию");
                return null;

                }
        } else {
            log.info("Логин не соотвествует условию");
            return null;

            }

    }

    public void updateUserUUID(User user) {
        userRepository.updateWithUUID(user);
    }
}
