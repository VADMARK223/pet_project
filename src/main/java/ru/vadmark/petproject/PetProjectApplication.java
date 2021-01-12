package ru.vadmark.petproject;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

import static java.util.Collections.singletonList;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class PetProjectApplication {
    public PetProjectApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        DefaultObjectWrapperBuilder owb = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23);
        owb.setForceLegacyNonListCollections(false);
        DefaultObjectWrapper objectWrapper = owb.build();

        TaglibFactory tagLibFactory = freeMarkerConfigurer.getTaglibFactory();
        if (tagLibFactory.getObjectWrapper() == null) {
            tagLibFactory.setObjectWrapper(objectWrapper);
        }
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("/META-INF/security.tld"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PetProjectApplication.class, args);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                apis(RequestHandlerSelectors.basePackage("ru.vadmark.petproject.controller")).
                paths(PathSelectors.any()).
                build().
                apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Pet project API",
                "Project for demo.",
                "0.0.1",
                "Term of service",
                new Contact("Markitanov Vadim", "https://vk.com/random223", "vadmark223@mail.ru"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }
}
