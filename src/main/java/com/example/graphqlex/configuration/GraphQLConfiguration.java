package com.example.graphqlex.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class GraphQLConfiguration {
    @Bean
    public WebGraphQlInterceptor requestInterceptor() {
        return (requestInput, chain) -> {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            requestInput.configureExecutionInput((executionInput, builder) ->
                    builder.graphQLContext(ctxBuilder -> ctxBuilder.of(HttpServletRequest.class, servletRequest)).build()
            );
            return chain.next(requestInput);
        };
    }
}


