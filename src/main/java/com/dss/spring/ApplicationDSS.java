package com.dss.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;


@SpringBootApplication
public class ApplicationDSS extends SpringBootServletInitializer {
	private static final Logger log = LoggerFactory.getLogger(ApplicationDSS.class);
	public static void main(String[] args) {
		SpringApplication.run(ApplicationDSS.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationDSS.class);
	}
	
	@Bean
    CommandLineRunner jpaSample(TodoRepository todoRepo) {
      return (args) -> {
	//Almacenar los 2 "instancias" de Todo en la base de datos H2   
    todoRepo.save(new Todo("Test"));
    Todo todo = new Todo("Test detallado");
    Date date = new Date();
    todo.setDueDate(date);
    todo.setDescription("Descripcion detallada");
    todoRepo.save(todo);
 // fetch all customers
    log.info("Todos encontrados con findAll():");
    log.info("-------------------------------");
    todoRepo.findAll().forEach(todos -> {
      log.info(todos.toString());
    });
    log.info("");
    RestTemplate restTemplate = new RestTemplate();
    //Ahora los vamos a obtener del servidor REST	   
    Todo firstTodo = restTemplate.getForObject("http://localhost:8080/rest/tasks/1", Todo.class);
    Todo secondTodo = restTemplate.getForObject("http://localhost:8080/rest/tasks/2", Todo.class);
    System.out.println(firstTodo);
    System.out.println(secondTodo);   
    
    //Envio y validacion
  //  ResponseEntity<Todo> postForEntity = 
  //  restTemplate.postForEntity("http://localhost:8080/rest/tasks/", newTodo, Todo.class);
  //  System.out.println(postForEntity);
    
  //Ejemplo de post
    Todo newTodo = new Todo("Nuevo Todo");
    newTodo.setDescription("Todo añadido por la API rest");
    newTodo.setDone(true);

    ResponseEntity<Todo> postForEnt = restTemplate.postForEntity("http://localhost:8080/rest/tasks", newTodo, Todo.class);
    System.out.println("Entidad posteada en repo"+postForEnt);
    
    ResponseEntity<Todo> postForEnt2 = restTemplate.postForEntity("http://localhost:8080/rest/tasks", newTodo, Todo.class);
    System.out.println("Entidad posteada en repo"+postForEnt2);
    };
	}

}
