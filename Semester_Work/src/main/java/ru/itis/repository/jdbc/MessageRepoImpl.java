package ru.itis.repository.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.MessageRepo;

import ru.itis.repository.utill.SimpleJdbcTemplate;
import ru.itis.service.IUserService;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MessageRepoImpl implements MessageRepo {



    private final String SQL_ADD_MESSAGE = "INSERT INTO \"Messages\"(id_from, id_to, \"Message\", \"Time\") values(?,?,?,?) ";

    private final String SQL_FIND_MESSAGES = "SELECT * from \"Messages\" inner join \"User\" U on U.id = \"Messages\".id_from where id_from =? and  id_to=?";


    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Message> messageRowMapper = (ResultSet row,int num) -> {
        return Message.builder()
                .message(row.getString("Message"))
                .from(User.builder().id(row.getLong("id_from"))
                        .nickname(row.getString("nickname")).build())
                .to(User.builder().id(row.getLong("id_to"))
                        .build())
                .date(row.getTimestamp("Time"))
                .build();
    };

    public MessageRepoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public void create(Message entity) {
        log.info("entity : {}", entity.toString());

        log.info("from  {}", entity.getFrom());
        log.info("to   {}", entity.getTo());
        Timestamp timestamp = new Timestamp(entity.getDate().getTime());
        jdbcTemplate.update(SQL_ADD_MESSAGE, entity.getFrom().getId(), entity.getTo().getId(), entity.getMessage(), timestamp);


        //   }
    }

    @Override
    public void update(Message entity) {
        throw  new UnsupportedOperationException("Not supported yet");

    }

    @Override
    public void delete(Message entity) {
        throw  new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Optional<Message> findById(long id) {
        return Optional.empty();
    }

    public List<Message> findAllMessages(long id_from, long id_to) {
        return jdbcTemplate.query(SQL_FIND_MESSAGES, messageRowMapper, id_from, id_to);
    }

}
