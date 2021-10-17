package br.com.jkassner.apiloteria.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

//@Configuration
//@Profile("local")
public class ConfigDataSource {

    @Value("spring.datasource.username")
    private String username;
    @Value("spring.datasource.password")
    private String password;
    @Value("spring.datasource.url")
    private String url;
    @Value("spring.datasource.driver-class-name")
    private String driver;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);

        return dataSourceBuilder.build();
    }
}
