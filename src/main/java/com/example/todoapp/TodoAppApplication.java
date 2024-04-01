package com.example.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;

/**
 * Program ćwiczebny do nauki
 * Podejście klasyczne z wykorzystaniem @RestControler, sami mapujemy żądania GET, POST, etc.
 * Różne właściwości w pliku application-local.yml: use-new-id-generator-mappings: false, out-of-order: false, ignore-missing-migrations: false
 * @autor Radosław Sawicki
 * @verison 0.0.1 2016-10-02
 */
@EnableAsync
@SpringBootApplication
public class TodoAppApplication{

	public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
	}

    /**
     * Importujemy Validator - javax.validation.Validator - do importowania adnotacji
     */
    @Bean
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }
         
}