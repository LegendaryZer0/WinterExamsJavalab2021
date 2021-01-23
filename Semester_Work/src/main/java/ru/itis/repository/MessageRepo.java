package ru.itis.repository;

import ru.itis.model.Message;

import java.util.List;

public interface MessageRepo extends CrudRepository<Long,Message> {
    public List<Message> findAllMessages(long id_from, long id_to);
}
