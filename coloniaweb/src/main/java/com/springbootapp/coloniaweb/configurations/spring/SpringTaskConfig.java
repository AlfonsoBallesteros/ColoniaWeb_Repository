package com.springbootapp.coloniaweb.configurations.spring;



import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/*
 * usamos spring job scheduling framework para crear tareas que se ejecutan en nuestra bd cada cierto tiempo
 * esta clase de configuración nos sirve para habilitar el scheduling 
 */
@Configuration
@EnableScheduling
@ComponentScan({ "springbootapp.coloniaweb.task" })
public class SpringTaskConfig {

}