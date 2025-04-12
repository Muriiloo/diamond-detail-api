package com.diamonddetail.api.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
            return new OpenAPI().info(new Info()
                    .title("Diamond Detail API")
                    .version("1.0")
                    .description("API Rest para gerenciamento de usuários, serviços e agendamentos"));
    }
}
