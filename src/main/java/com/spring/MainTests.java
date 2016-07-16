package com.spring;

import com.spring.models.Product;
import com.spring.repositories.ProductRespository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by Dewes on 16/07/2016.
 */
public class MainTests {

    public static Logger logger = Logger.getLogger(MainTests.class);

    public static void main(String[] args) {

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("deregistering jdbc driver: "+ driver.getClass().getName());
            } catch (SQLException e) {
                logger.error("Error deregistering driver "+ driver.getClass().getName() +"\nMessage: "+ e.getMessage());
            }

        }

    }
}
