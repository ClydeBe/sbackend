package com.thewheel.sawatu.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SpringFoxConfig {

    private final String DOCS_VERSION = "0.5";
    private final Map<String, Response> responses = new HashMap<>() {{
        put("OK", new ResponseBuilder().code("200").description("Ok").build());
        put("CREATED", new ResponseBuilder().code("201").description("Created").build());
        put("BAD_REQUEST", new ResponseBuilder().code("400").description("Bad request").build());
        put("UNAUTHORIZED", new ResponseBuilder().code("401").description("Unauthorized").build());
        put("FORBIDDEN", new ResponseBuilder().code("403").description("Forbidden").build());
        put("NOT_FOUND", new ResponseBuilder().code("404").description("Not found").build());
    }};

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("SAWATU ReST API for Developers")
                                .description("Documentation of Sawatu ReST API")
                                .version(DOCS_VERSION)
                                .license("Proprietary")
                                .build()
                        )
                .useDefaultResponseMessages(false)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
        setGlobalResponses(docket);
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private BasicAuth auth() {
        return new BasicAuth("basicAuth");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private void setGlobalResponses(Docket docket) {
        docket.globalResponses(GET, Arrays.asList(
                responses.get("OK"),
                responses.get("NOT_FOUND"),
                responses.get("BAD_REQUEST"),
                responses.get("FORBIDDEN"),
                responses.get("UNAUTHORIZED")));
        docket.globalResponses(POST, Arrays.asList(
                responses.get("CREATED"),
                responses.get("BAD_REQUEST"),
                responses.get("FORBIDDEN"),
                responses.get("UNAUTHORIZED")));
        docket.globalResponses(PUT, Arrays.asList(
                responses.get("OK"),
                responses.get("BAD_REQUEST"),
                responses.get("FORBIDDEN"),
                responses.get("UNAUTHORIZED")));
        docket.globalResponses(DELETE, Arrays.asList(
                responses.get("NOT_FOUND"),
                responses.get("BAD_REQUEST"),
                responses.get("FORBIDDEN"),
                responses.get("UNAUTHORIZED")));
    }

}
