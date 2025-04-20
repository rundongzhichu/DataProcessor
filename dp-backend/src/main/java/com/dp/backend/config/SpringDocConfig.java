package com.dp.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


/**
 *
 * Springdoc Config
 *
 * @author shoucai
 */
@Configuration
@ConditionalOnProperty(name = Constants.SPRINGDOC_ENABLED, matchIfMissing = true)
public class SpringDocConfig {

    @Value(value = "${spring.application.name}")
    private String title;

    @Value(value = "${spring.application.version}")
    private String version;

    @Value(value = "${spring.application.description}")
    private String description;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(title).version(version).description(description));
//                .schemaRequirement(HttpHeaders.AUTHORIZATION,
//                        new SecurityScheme()
//                                .name(HttpHeaders.AUTHORIZATION)
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer"))
//                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
    }

    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream()).forEach(operation -> {
            operation.getResponses().addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    new ApiResponse().description("Bad Request").content(new Content().addMediaType(MediaType.TEXT_PLAIN_VALUE, new io.swagger.v3.oas.models.media.MediaType().example(HttpStatus.BAD_REQUEST.getReasonPhrase()))));
            operation.getResponses().addApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    new ApiResponse().description("Unauthorized").content(new Content().addMediaType(MediaType.TEXT_PLAIN_VALUE, new io.swagger.v3.oas.models.media.MediaType().example(HttpStatus.UNAUTHORIZED.getReasonPhrase()))));
            operation.getResponses().addApiResponse(String.valueOf(HttpStatus.FORBIDDEN.value()),
                    new ApiResponse().description("Request Forbidden").content(new Content().addMediaType(MediaType.TEXT_PLAIN_VALUE, new io.swagger.v3.oas.models.media.MediaType().example(HttpStatus.FORBIDDEN.getReasonPhrase()))));
            operation.getResponses().addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    new ApiResponse().description("Internal Server Error").content(new Content().addMediaType(MediaType.TEXT_PLAIN_VALUE, new io.swagger.v3.oas.models.media.MediaType().example(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()))));
        });
    }

    @Bean
    @Primary
    public SwaggerUiConfigProperties swaggerUiConfig() {
        SwaggerUiConfigProperties config = new SwaggerUiConfigProperties();
        config.setPersistAuthorization(true);
        config.setFilter("true");
        config.setDefaultModelsExpandDepth(-1);
        config.setDefaultModelExpandDepth(10);
        config.setDisplayRequestDuration(true);
        config.setDocExpansion("none");
        config.setShowCommonExtensions(true);
        return config;
    }

}
