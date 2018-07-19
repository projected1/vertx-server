package com.vala.demo;

import java.sql.*;

public class Repository {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/vala_demo_db";

    private static final String SQL_CREATE_TABLE = "create table if not exists counter (click_count int)";
    private static final String SQL_SELECT_COUNT = "select click_count from counter";
    private static final String SQL_INSERT_COUNT = "insert into counter values (?)";
    private static final String SQL_UPDATE_COUNT = "update counter set click_count = click_count + 1";

    Repository() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        initDatabase();
    }

    public int getCount() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_COUNT);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementCount() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement statement = conn.createStatement()) {
            statement.executeUpdate(SQL_UPDATE_COUNT);
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            boolean isEmptyTable;
            try (Statement statement = conn.createStatement()) {
                statement.execute(SQL_CREATE_TABLE);
                ResultSet results = statement.executeQuery(SQL_SELECT_COUNT);
                isEmptyTable = !results.next();
            }

            if (isEmptyTable) {
                try (PreparedStatement statement = conn.prepareStatement(SQL_INSERT_COUNT)) {
                    statement.setInt(1, 0);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
