package ru.itis.repository.jdbc;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.itis.model.TechnicalInfo;
import ru.itis.model.User;
import ru.itis.model.dto.ChatroomDto;
import ru.itis.repository.UserRepository;

import ru.itis.repository.utill.SimpleJdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


@Slf4j

public class UserRepositoryImpl implements UserRepository {

    //language=PostgreSQL
    private final String SQL_FIND_UUID = "SELECT * from \"technical_Info\" where uuid =?";
    //language=PostgreSQL
    private final String SQL_UPDATE_TECH_INFO = "UPDATE \"technical_Info\" SET uuid=? where id =? ";
    //language=PostgreSQL
    private final String SQL_ADD_TECH_INFO = "INSERT INTO  \"technical_Info\"(uuid,id) VALUES(?,?) ";
    //language=PostgreSQL
    private final String SQL_ADD_USER = "INSERT INTO  \"User\" VALUES (?,?)"; //TODO nothing to do
    //language=PostgreSQL
    private final String SQL_GET_USER = "SELECT * from \"User\" join \"technical_Info\" on \"User\".id = \"technical_Info\".id where email =? ";//and password =?
    //language=PostgreSQL
    private final String SQL_UPDATE_USER = "UPDATE  \"User\" SET nickname=?,email=?,password=? where id = ?";
    //language=PostgreSQL
    private final String SQL_GET_USER_BY_UUID = "SELECT *,\"User\".id as id from \"User\" join \"technical_Info\" on \"User\".id = \"technical_Info\".id where uuid =?";
    //language=PostgreSQL
    private final String SQL_GET_USER_BY_LOGIN = "select * from \"User\" where email =?";
    //language=PostgreSQL
    private final String SQL_GET_USER_BY_ID = "SELECT \"User\".id as id,\"User\".email, \"User\".password, \"User\".nickname, phone, \"technical_Info\".uuid, \"technical_Info\".id from \"User\" join \"technical_Info\" on \"User\".id  = \"technical_Info\".id where \"User\".id=?";
    //language=PostgreSQL
    private final String SQL_GET_USER_BY_EMAIL = "SELECT \"User\".id as id,\"User\".email, \"User\".password, \"User\".nickname, phone, \"technical_Info\".uuid,  \"technical_Info\".id from \"User\" join \"technical_Info\" on \"User\".id  = \"technical_Info\".id where \"User\".email=?";
    //language=PostgreSQL
    private final String SQL_DElETE_USER_BY_ID = "DELETE FROM \"User\" WHERE ID =?";
    //language=PostgreSQL
    private final String SQL_GET_CHATROOM_UUID = "select  * from chats where id_from=? and id_to=?;";
    //language=PostgreSQL
    private final String SQL_GET_CHAT_ID_FROM = "select  id_from as id from \"chats\"";
    //language=PostgreSQL
    private final String SQL_ADD_LINKS = "INSERT INTO \"chats\"(id_from,id_to)VALUES (?,?);";
    //language=PostgreSQL
    private final String SQL_FIND_USERS_CHAT_BY_ID = "select \"User\".email, \"User\".password, \"User\".id as id, \"User\".nickname, \"User\".phone, tI.uuid from \"User\" inner join \"technical_Info\" tI on \"User\".id = tI.id where \"User\".id in(\n" +
            "(SELECT id_from from \"Messages\" inner join \"User\" U on U.id = \"Messages\".id_to where  id=?\n" +
            "))or \"User\".id in\n" +
            "(SELECT id_to from \"Messages\" inner join \"User\" U on U.id = \"Messages\".id_from where  id=?\n" +
            ");";


    private JdbcTemplate jdbcTemplate;

   private List<User> users;
    private User user;
    private final RowMapper<ChatroomDto> chatroomDtoRowMapper = (ResultSet row,int numRow) ->
            ChatroomDto.builder().id_from(row.getLong("id_from"))
            .id_to(row.getLong("id_to"))
            .uuid(row.getObject("uuid", UUID.class))
            .build();
    private final RowMapper<TechnicalInfo> technicalInfoRowMapper = (ResultSet row,int numRow) ->
            TechnicalInfo.builder().id(row.getLong("id")).uuid((UUID) row.getObject("uuid")).build();

    private final RowMapper<User> userUtillRowMapper = (ResultSet row,int numRow) -> User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .build();

    private final RowMapper<User> userUtillChatRowMapper = (ResultSet row,int numRow) -> User.builder()
            .id(row.getLong("id")).build();

    private final RowMapper<User> userRowMapper = (ResultSet row,int numRow) -> User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .nickname(row.getString("nickname"))
            .password(row.getString("password"))
            .technicalInfo(TechnicalInfo.builder().uuid((UUID) row.getObject("uuid")).build())
            .build();

    public UserRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Deprecated
    @Override
    public User findUserByUUID(User user) {
        List<User> ans = jdbcTemplate.query(SQL_GET_USER_BY_UUID, userRowMapper, user.getTechnicalInfo().getUuid());
        if (ans.size() > 0) {
            return ans.get(0);
        } else
            return User.builder().build();

    }

    public List<User> findAllUserChatById(long id) {
        return jdbcTemplate.query(SQL_FIND_USERS_CHAT_BY_ID, userRowMapper, id, id);
    }


    @Override
    public User getUserByLogin(String login) { //Получаем пользователя по логину, пароль(кэш) сравниваем потом
            return jdbcTemplate.query(SQL_GET_USER,userRowMapper,login).get(0);
    }


    @Override
    public void create(User entity) {

            jdbcTemplate.query(SQL_ADD_USER,userUtillRowMapper,entity.getEmail(),entity.getPassword());
            user =jdbcTemplate.query(SQL_GET_USER_BY_LOGIN,userUtillRowMapper,entity.getEmail()).get(0);

            jdbcTemplate.query(SQL_ADD_TECH_INFO,userRowMapper,entity.getTechnicalInfo().getUuid(),user.getId());

            users=jdbcTemplate.query(SQL_GET_CHAT_ID_FROM,userUtillChatRowMapper);
            users.add(user);
        new HashSet<>(users).forEach(x -> {
                    jdbcTemplate.query(SQL_ADD_LINKS,userRowMapper, user.getId(),x.getId());
            });
    }

    @Override  //UPDATE
    public void update(User entity) {

        try  {
            log.debug(entity.toString());
            User user = this.findUserByUUID(entity);
            log.info("ID USERA" + user.getId());
            log.info("ID ENTYTY" + entity.getId());
            jdbcTemplate.update(SQL_UPDATE_USER, entity.getNickname(), entity.getEmail(), entity.getPassword(), user.getId());
            jdbcTemplate.update(SQL_UPDATE_TECH_INFO, entity.getTechnicalInfo().getUuid(), user.getId());

        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        }


    }

    @Override
    public void delete(User entity) {
        jdbcTemplate.query(SQL_DElETE_USER_BY_ID, userRowMapper, entity.getId());

    }

    @Deprecated
    public void updateWithUUID(User entity) {

        try  {
            log.debug(entity.toString());
            User user = this.findUserByUUID(entity);
            log.info("ID USERA" + user.getId());
            log.info("ID ENTYTY" + entity.getId());
            jdbcTemplate.update(SQL_UPDATE_USER, entity.getNickname(), entity.getEmail(), entity.getPassword(), entity.getId());
            jdbcTemplate.update(SQL_UPDATE_TECH_INFO, entity.getTechnicalInfo().getUuid(), entity.getId());

        } catch (DataAccessException e) {
            throw new IllegalStateException(e);
        }


    }

    public User getUserByEmail(User user) {
        return jdbcTemplate.query(SQL_GET_USER_BY_EMAIL, userRowMapper, user.getEmail()).get(0);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.empty();
    }

    public User getById(long id) {
        return jdbcTemplate.query(SQL_GET_USER_BY_ID, userRowMapper, id).get(0);
    }


    public UUID getChatroomUUIDForTwoUsers(long id_from, long id_to) {
        return jdbcTemplate.query(SQL_GET_CHATROOM_UUID, chatroomDtoRowMapper, id_from, id_to).get(0).getUuid();
    }

}









/* I think there is a BUG in this code....(see below )*/
















/*
*
*             .                .
            :"-.          .-";
            |:`.`.__..__.'.';|
            || :-"      "-; ||
            :;              :;
            /  .==.    .==.  \
           :      _.--._      ;
           ; .--.' `--' `.--. :
          :   __;`      ':__   ;
          ;  '  '-._:;_.-'  '  :
          '.       `--'       .'
           ."-._          _.-".
         .'     ""------""     `.
        /`-                    -'\
       /`-                      -'\
      :`-   .'              `.   -';
      ;    /                  \    :
     :    :                    ;    ;
     ;    ;                    :    :
     ':_:.'                    '.;_;'
        :_                      _;
        ; "-._                -" :`-.     _.._
        :_          ()          _;   "--::__. `.
         \"-                  -"/`._           :
        .-"-.                 -"-.  ""--..____.'
       /         .__  __.         \
      : / ,       / "" \       . \ ; BUG
       "-:___..--"      "--..___;-"
*
*
*
*
*
*
*     */
