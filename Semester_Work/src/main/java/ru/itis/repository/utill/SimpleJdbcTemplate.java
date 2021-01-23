package ru.itis.repository.utill;


import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SimpleJdbcTemplate {

    /*private Connection connection;*/
    private DataSource dataSource;
    private Connection connection;






/*    public SimpleJdbcTemplate(Connection connection) {
        this.connection = connection;
    }*/
    //private DataSourse datasource - из hikariconnectionpool

    public SimpleJdbcTemplate(DataSource dataSource) {

        this.dataSource = dataSource;


        }




    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = null;
            List<T> result = new ArrayList<>();


            int position = 1;
            for (Object arg : args) {
                preparedStatement.setObject(position, arg);
                position++;
            }
            if (sql.contains("UPDATE") || sql.contains("update") || sql.toLowerCase().contains("delete") || sql.toLowerCase().contains("insert")) {

                preparedStatement.executeUpdate();
            } else {
                resultSet = preparedStatement.executeQuery();
                if (resultSet == null) {
                    throw new SQLException("No resultsSet");
                }
                while (resultSet.next()) {
                    result.add(rowMapper.mapRow(resultSet));
                }


            }
            log.info(result.toString());
            return result;
        } catch (SQLException e) {

            throw new IllegalStateException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    log.info("resultes finaly closed? - {}", resultSet.isClosed());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (preparedStatement != null) {
                try {

                    preparedStatement.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            try {

                if (connection != null) {

                    connection.close();

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
