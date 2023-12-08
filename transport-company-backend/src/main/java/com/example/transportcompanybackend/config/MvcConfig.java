package com.example.transportcompanybackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String LAYOUT = "index";

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        resolver.setPrefix("/");
        resolver.setCache(false);
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // handles home url
        registry.addViewController("/").setViewName(LAYOUT);
        registry.addViewController("/**/{[path:[^\\.]*}").setViewName(LAYOUT);
        // required to handle WebSocket mappings first
        registry.setOrder(2);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseSuffixPatternMatch(true);
    }


}
