package com.dss.spring;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class Config {
	@Bean
    public Long getId() {
        return Long.valueOf(0);
    }
    @Bean
    @Qualifier("summary")
    public String getSummary() {
        return "Spring: prueba de Inyeccion de Dependencias";
    }
    @Bean
    @Qualifier("description")
    public String getDescription() {
        return "Spring: prueba de Inyeccion de Dependencias y todo lo demas";
    }
    @Bean
    public Boolean isDone() {
        return Boolean.FALSE;
    }
    @Bean
    public Date getDueDate() {
        return new Date();
    }

}
