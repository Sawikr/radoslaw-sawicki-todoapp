package com.example.todoapp.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Set;

/**
 * Klasa do wstrzykiwania Interceptora!
 * Bez niej Spring nie widzi klasy LoggerInterceptor
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    //wstrzykiwanie automatycznie wielu Interceptorów - poprzez konstruktor!
    private Set<HandlerInterceptor> interceptors;

    public MvcConfiguration(final Set<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);// nie trzema .stream().forE...
    }
}
