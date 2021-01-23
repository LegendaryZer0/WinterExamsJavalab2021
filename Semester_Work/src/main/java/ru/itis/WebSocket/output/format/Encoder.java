package ru.itis.WebSocket.output.format;

import com.google.gson.Gson;
import ru.itis.model.Message;

import javax.websocket.EndpointConfig;

public class Encoder implements javax.websocket.Encoder.Text<Message> {

    private static final Gson gson = new Gson();

    @Override
    public String encode(Message object) {
        return gson.toJson(object);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
