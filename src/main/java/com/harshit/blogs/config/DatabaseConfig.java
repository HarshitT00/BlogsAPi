package com.harshit.blogs.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${JDBC_DATABASE_URL:jdbc:postgresql://localhost:5432/blogs}")
    private String databaseUrl;

    @Value("${JDBC_DATABASE_USERNAME:postgres}")
    private String databaseUsername;

    @Value("${JDBC_DATABASE_PASSWORD:postgres}")
    private String databasePassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl = databaseUrl;
        if (databaseUrl.startsWith("postgresql://")) {
            jdbcUrl = "jdbc:" + databaseUrl;
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(databaseUsername);
        config.setPassword(databasePassword);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(30000);
        return new HikariDataSource(config);
    }
}