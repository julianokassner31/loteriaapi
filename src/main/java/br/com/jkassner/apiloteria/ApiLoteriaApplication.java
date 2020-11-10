package br.com.jkassner.apiloteria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages={"br.com.jkassner.apiloteria.repository"})
public class ApiLoteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLoteriaApplication.class, args);
    }
}
