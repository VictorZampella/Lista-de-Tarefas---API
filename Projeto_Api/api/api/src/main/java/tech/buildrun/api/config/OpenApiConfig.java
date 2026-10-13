package tech.buildrun.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI documentacaoApi() {
        return new OpenAPI().info(new Info()
                .title("API de Gerenciamento de Tarefas")
                .description("API para usuários, perfis, tarefas, categorias, etiquetas e comentários.")
                .version("1.0.0"));
    }

    @Bean
    public SwaggerIndexTransformer swaggerIndexTransformer(
            SwaggerUiConfigProperties swaggerUiConfig,
            SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            SwaggerWelcomeCommon swaggerWelcomeCommon,
            ObjectMapperProvider objectMapperProvider) {
        return new SwaggerIndexPageTransformer(swaggerUiConfig, swaggerUiOAuthProperties,
                swaggerWelcomeCommon, objectMapperProvider) {
            @Override
            public Resource transform(HttpServletRequest request, Resource resource,
                    ResourceTransformerChain transformerChain) throws IOException {
                Resource transformed = super.transform(request, resource, transformerChain);
                String html;
                try (var input = transformed.getInputStream()) {
                    html = new String(input.readAllBytes(), StandardCharsets.UTF_8);
                }
                String style = "<style>body{background-color:#EAF2F8!important;}"
                        + ".swagger-ui,.swagger-ui .wrapper,.swagger-ui .scheme-container{"
                        + "background-color:transparent!important;}"
                        + ".swagger-ui .topbar{background-color:#173B57!important;}</style>";
                html = html.replace("</head>", style + "</head>");
                return new TransformedResource(transformed, html.getBytes(StandardCharsets.UTF_8));
            }
        };
    }
}
