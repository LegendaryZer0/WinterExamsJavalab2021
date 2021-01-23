package ru.itis.WebSocket.output.format;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ru.itis.model.Message;

import javax.websocket.EndpointConfig;

@Slf4j
public class Decoder implements javax.websocket.Decoder.Text<Message> {
    private static final Gson gson = new Gson();

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public Message decode(String s) {
        return gson.fromJson(s, Message.class);
    }

    @Override
    public boolean willDecode(String s) {
        log.info("Пробую раскодировать сообщение");
        try {
            gson.fromJson(s, Message.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
