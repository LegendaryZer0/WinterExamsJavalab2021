package ru.itis.service;

import org.springframework.context.annotation.Bean;
import ru.itis.repository.UserRepository;

import java.util.Arrays;
import java.util.UUID;

public class ChatService implements IChatService {
    private UserRepository userRepository;

    public ChatService() {

    }

    public ChatService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public UUID getChatUUID(Long[] pair) {
        Arrays.sort(pair);
        try {
            return userRepository.getChatroomUUIDForTwoUsers(pair[1], pair[0]);
        }catch (Exception e){
            return this.getChatUUID(pair);
        }


    }
}
