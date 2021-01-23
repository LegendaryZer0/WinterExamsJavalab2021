package ru.itis.service;

import ru.itis.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    public List<User> findAllUsersChats(long id);
    public User updateUser(User user);
    public void updateUserUUID(User user);
    public User findUser(UUID uuid);
    public User getUserById(long id);
    public User getUser(String login, String password);
    public User getUserByEmail(User user);
    public boolean addUser(User user);




}
