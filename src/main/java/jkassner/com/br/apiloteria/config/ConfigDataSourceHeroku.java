package jkassner.com.br.apiloteria.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("heroku")
public class ConfigDataSourceHeroku {

    @Bean
    public DataSource getDataSource() {

        String url = System.getenv("DATABASE_URL");
        String user = System.getenv("USER_DB");
        String pass = System.getenv("PASS_DB");

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(pass);
        return dataSourceBuilder.build();
    }
}
