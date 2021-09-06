package beathub.api.configuration;

import beathub.api.annotation.ShowAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

import java.sql.Timestamp;
import java.util.Collections;

@Configuration
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(ShowAPI.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Timestamp.class, Long.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "BeatHub REST API",
                "Backend REST API for BeatHub Android and Desktop Flutter apps.",
                "1.0",
                "Terms of service",
                new Contact("Marin Bareza", "https://github.com/MarinBareza", "mbareza@racunarstvo.hr"),
                "",
                "",
                Collections.emptyList());
    }
}