package pl.maciejtuznik.WordMasterApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Zezwala na dostęp do wszystkich endpointów
                .allowedOrigins("http://localhost:63342")  // Zezwól na połączenia z Twojego klienta (port frontendowy)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // Dozwolone metody HTTP
                .allowedHeaders("*")  // Dozwolone nagłówki
                .allowCredentials(true);  // Pozwól na ciasteczka i uwierzytelnianie (opcjonalne)
    }
}



