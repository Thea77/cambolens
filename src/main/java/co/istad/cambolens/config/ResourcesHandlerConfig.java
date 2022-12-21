package co.istad.cambolens.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourcesHandlerConfig implements WebMvcConfigurer{
    
    @Value("${file.client-part}")
    private String clientPath;
    
    @Value("${file.server-path}")
    private String serverPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientPath).addResourceLocations("file:"+ serverPath);
    }
}
