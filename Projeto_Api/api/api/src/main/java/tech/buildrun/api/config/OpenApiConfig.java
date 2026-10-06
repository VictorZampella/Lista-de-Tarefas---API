package tech.buildrun.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI documentacaoApi() {
        return new OpenAPI().info(new Info()
                .title("API de Gerenciamento de Tarefas")
                .description("API para usuários, perfis, tarefas, categorias, etiquetas e comentários.")
                .version("1.0.0"));
    }
}
