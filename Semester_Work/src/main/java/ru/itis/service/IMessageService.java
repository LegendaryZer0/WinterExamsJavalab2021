package ru.itis.service;

import ru.itis.model.Message;

import java.util.List;

public interface IMessageService {
    public void sendMessage(Message message);
    public List<Message> getAllMessagesFromUserToUser(long id_From, long id_to);
    public List<Message> getAllMessagesBetweenTwoUsers(long id_From, long id_to);
}
