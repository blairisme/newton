package rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("HHHHHHXXX files = " + new File("").getAbsolutePath());
        String path = new File("").getAbsolutePath();
        registry
                .addResourceHandler("/files/**")
                .addResourceLocations("file:///" + path +"/");
    }
}
