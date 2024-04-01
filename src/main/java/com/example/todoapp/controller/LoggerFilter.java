package com.example.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter @{@Link Filter} to standardowe narzędzie wykorzystywane w Javie i nie tylko
 * Spring ma swój odpowiednik Interceptor
 */

//@Order(Ordered.HIGHEST_PRECEDENCE) - nie potrzebujemy tutaj!
@Component
public class LoggerFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            var httpRequest = (HttpServletRequest) request;
            logger.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        }
        //teraz musimy spowodować, aby żądanie poszło dalej w łańcuch żądań
        chain.doFilter(request, response);
    }
}
