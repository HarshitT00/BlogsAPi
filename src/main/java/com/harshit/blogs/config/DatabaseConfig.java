package com.harshit.blogs.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Value("${DATABASE_URL:${JDBC_DATABASE_URL:jdbc:postgresql://localhost:5432/blogs}}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        try {
            URI dbUri = new URI(databaseUrl.replace("jdbc:", ""));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" +
                    (dbUri.getPort() != -1 ? dbUri.getPort() : "5432") +
                    dbUri.getPath() +
                    (dbUri.getQuery() != null ? "?" + dbUri.getQuery() : "");

            config.setJdbcUrl(dbUrl);
            config.setUsername(username);
            config.setPassword(password);

        } catch (URISyntaxException e) {
            // Fallback for local development
            config.setJdbcUrl(databaseUrl);
            config.setUsername("postgres");
            config.setPassword("postgres");
        }

        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }
}