package com.yappa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI yappaOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Servidor de Desarrollo");

        Server prodServer = new Server();
        prodServer.setUrl("http://yappa-app:8080");
        prodServer.setDescription("Servidor de Producción (Docker)");

        Contact contact = new Contact();
        contact.setName("Yappa");
        contact.setEmail("support@yappa.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Yappa - API de Gestión de Clientes")
                .version("1.0.0")
                .contact(contact)
                .description("API RESTful para el sistema ABM (Alta, Baja, Modificación) de clientes. " +
                        "")
                .termsOfService("https://yappa.com/terms")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
