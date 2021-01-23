package ru.itis.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.MessageRepo;

import java.util.Comparator;
import java.util.List;

@Slf4j

public class MessageService implements IMessageService {
    private MessageRepo messageRepo;
    private IUserService userService;


    public MessageService(MessageRepo messageRepo, IUserService userService) {
        this.messageRepo = messageRepo;
        this.userService = userService;
    } //Проверить

    public void sendMessage(Message message) {
        User from = userService.getUserById(message.getFrom().getId());
        User to = userService.getUserById(message.getTo().getId());
        message.setFrom(from);
        message.setTo(to);
        messageRepo.create(message);
    }


    public List<Message> getAllMessagesFromUserToUser(long id_From, long id_to) {
        List<Message> messages = messageRepo.findAllMessages(id_From, id_to);
        messages.sort(Comparator.comparing(Message::getDate));
        return messages;
    }

    public List<Message> getAllMessagesBetweenTwoUsers(long id_From, long id_to) {

        List<Message> messagesFrom = messageRepo.findAllMessages(id_From, id_to);
        List<Message> messagesTo = messageRepo.findAllMessages(id_to, id_From);
        messagesFrom.addAll(messagesTo);
        messagesFrom.sort(Comparator.comparing(Message::getDate));
        log.info(messagesFrom.toString());

        return messagesFrom;
    }

}
