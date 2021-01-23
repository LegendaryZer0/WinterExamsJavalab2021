package ru.itis;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map.Entry;

public class EntityManager {
    private static  int k=2;
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        EntityManager entityManager = new EntityManager(new SimpleDataSource("jdbc:postgresql://localhost:5432/FabulousDatabase","postgres","123456789"));
     /*   Client client =Client.builder().age(7).name("fsFS").id(99L).build();*/
        /*Client client1 = entityManager.findById("myclient",Client.class,Long.class,99L);*/
       /* entityManager.createTable("entityTable",Entity.class);*/
       /* entityManager.save("entityTable",Entity.builder().id("someID").age(6).number(2134124124L).build());*/
        System.out.println(entityManager.findById("entitytable",Entity.class,Integer.class,7));
        /*System.out.println(entityManager.findById("entitytable where id =? or true --",Entity.class,Integer.class,7));*/

    }
    private ResultSet resultSet;
    private DataSource dataSource;
    private PreparedStatement preparedStatement;
    private Connection connection;
    private StringBuilder stringBuilder;
    private String SQL;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
        stringBuilder = new StringBuilder();
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        String SQL = "CREATE TABLE ? ( ";

        try(Connection connection = dataSource.getConnection();) {
            stringBuilder.append(SQL);
            for( int i = 0;i< entityClass.getDeclaredFields().length;i++){
               stringBuilder.append("? ?,");

            }
            SQL =stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length()).append(")").toString();
            stringBuilder.delete(0,stringBuilder.length());


            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1,tableName);
            Arrays.stream(entityClass.getDeclaredFields()).forEach((x)->{

                try {


                    switch (x.getType().getSimpleName().toLowerCase()) {
                        case "string" -> {
                            preparedStatement.setObject(k++, x.getName());
                            preparedStatement.setString(k++,"VARCHAR");
                        }
                        case "long" -> {
                            preparedStatement.setString(k++, x.getName());
                            preparedStatement.setString(k++,"BIGINT");
                        }
                        case "double" ->{
                            preparedStatement.setString(k++,x.getName());
                            preparedStatement.setString(k++,"float8");

                        }
                        case  "float"->{
                            preparedStatement.setString(k++,x.getName());
                            preparedStatement.setString(k++,"float2");
                        }
                        default -> {
                            preparedStatement.setString(k++,x.getName());
                            preparedStatement.setString(k++,x.getType().getSimpleName());
                        }
                    }

                }catch (SQLException throwables){
                    throw new IllegalStateException(throwables);
                }

            });
            Arrays.stream(preparedStatement.toString().split("'")).forEach(x->stringBuilder.append(x));
            preparedStatement= connection.prepareStatement(stringBuilder.toString());
            stringBuilder.delete(0,stringBuilder.length());
            preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throw  new IllegalStateException(throwables);
        }


        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))
    }

    public void save(String tableName, Object entity)  {
        StringBuilder localStringBuilder = new StringBuilder();
        String SQL ="INSERT INTO ";
        System.out.println(entity.toString());
        Class<?> classOfEntity = entity.getClass();
        try(Connection connection = dataSource.getConnection()) {
            stringBuilder.append(SQL).append(tableName).append("(");



            Arrays.stream(classOfEntity.getDeclaredFields()).forEach(x->{
                try {
                    x.setAccessible(true);
                    stringBuilder.append(x.getName()).append(",");
                    localStringBuilder.append("'").append(x.get(entity)).append("'").append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length()).append(") VALUES (");
            localStringBuilder.delete(localStringBuilder.length()-1,localStringBuilder.length()).append(");");
            stringBuilder.append(localStringBuilder.toString());
            SQL =stringBuilder.toString();
            System.out.println(SQL);
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();
            stringBuilder.delete(0,stringBuilder.length());
            localStringBuilder.delete(0,localStringBuilder.length());

        } catch (SQLException throwables) {
            throw  new IllegalStateException(throwables);
        }



        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {

        SQL ="SELECT * FROM ";

        stringBuilder.append(SQL).append("\"").append(tableName).append("\"").append(" WHERE id =?");
        try (Connection connection =dataSource.getConnection()){
            preparedStatement =connection.prepareStatement(stringBuilder.toString());
            preparedStatement.setObject(1,idValue);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            T result =resultType.newInstance();
            Arrays.stream(resultType.getDeclaredFields()).forEach(x->{
                try {
                    x.setAccessible(true);
                    x.set(result,resultSet.getObject(x.getName()));

                } catch (IllegalAccessException | SQLException  e) {
                    e.printStackTrace();
                }

            });
            stringBuilder.delete(0,stringBuilder.length());
            System.out.println(result);
            return  result;
        } catch (SQLException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

}
