package com.vczyh.shardingsphere;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class ShardingSphereDemoApplication implements ApplicationRunner {

//    public static final Logger log = LoggerFactory.getLogger(ShardingSphereDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereDemoApplication.class, args);
    }


    @Resource
    DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String sql = "SELECT @@server_id";
        Connection conn;

        try {
            while (true) {
                conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        log.info(rs.getString(1));
                    }
                }
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
