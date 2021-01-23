package ru.itis.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.socket.server.standard.SpringConfigurator;
import ru.itis.WebSocket.output.format.Decoder;
import ru.itis.WebSocket.output.format.Encoder;
import lombok.extern.slf4j.Slf4j;
import ru.itis.model.Message;
import ru.itis.service.IMessageService;
import ru.itis.service.IUserService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@ServerEndpoint(value = "/chat/{hash}",configurator = SpringConfigurator.class,
        decoders = Decoder.class,
        encoders = Encoder.class
)

public class ChatServerEndPoint {
    private ApplicationContext applicationContext /*= new ClassPathXmlApplicationContext("WEB-INF/dispatcherServlet-servlet.xml")*/;
    private static final Set<ChatServerEndPoint> sessionsSet
            = new CopyOnWriteArraySet<>();
    @Autowired
    @Qualifier("userServiceImpl")
    private  IUserService userService/*=applicationContext.getBean("userServiceImpl",IUserService.class)*/;

    @Autowired
    @Qualifier("messageServiceImpl")
    private IMessageService messageService/*= applicationContext.getBean("messageServiceImpl",IMessageService.class);*/;
    private String sessionId;
    private Session session;

    /* private static HashMap<String, String> users = new HashMap<>();*/

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("hash") String hash/*,@PathParam("")String id_from,@PathParam("") String id_to*/) throws IOException {
        this.session = session;
        sessionId = hash;
        sessionsSet.add(this);
        log.info("Сессия открыта сессий всего {}", sessionsSet.size());
        log.info("Идентификатор сессии{}", hash);

    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        log.info("Количество сессий на момент отпр сообщ {}", sessionsSet.size());
        log.info("Сообщение в onMessage перед отправкой  {}", message.toString());

        messageService.sendMessage(message);
        sessionsSet.stream().forEach(x -> {
            try {
                log.info(message.toString());
                if (!session.equals(x.session) && this.sessionId.equals(x.sessionId)) {
                    x.session.getBasicRemote().sendObject(message);
                }
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });

    }

    @OnClose
    public void onClose(Session session) {

        sessionsSet.remove(this);
        log.info("Сессий после закрытия {}", sessionsSet.size());

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throw new IllegalStateException(throwable);
    }


}
