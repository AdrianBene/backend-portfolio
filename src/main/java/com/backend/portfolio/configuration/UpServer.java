package com.backend.portfolio.configuration;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UpServer {
    @Autowired
    private DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void upServer() {
        System.out.println("Server on");
        System.out.println("http://localhost:8080/");
        dataBaseConnection();

    }

    private void dataBaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connected to database successfully");

        } catch (SQLException e) {
            System.out.println("Error to connected database");
        }
    }

}
