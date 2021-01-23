package ru.itis.repository;

import ru.itis.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<Long,User> {
    public User getUserByLogin(String login);
    public User getById(long id);
    public User findUserByUUID(User user);
    public User getUserByEmail(User user);

    public void updateWithUUID(User entity);
    public List<User> findAllUserChatById(long id);
    public UUID getChatroomUUIDForTwoUsers(long id_from,long id_to);
}
