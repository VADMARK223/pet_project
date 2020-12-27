package ru.vadmark.petproject;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import static java.util.Collections.singletonList;

@Slf4j
@Controller
@SpringBootApplication
public class PetProjectApplication {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    public PetProjectApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        DefaultObjectWrapperBuilder owb = new DefaultObjectWrapperBuilder
                (freemarker.template.Configuration.VERSION_2_3_23);
        owb.setForceLegacyNonListCollections(false);
        DefaultObjectWrapper objectWrapper = owb.build();

        TaglibFactory tagLibFactory = freeMarkerConfigurer.getTaglibFactory();
        log.info("TagLibFactory: {}.", tagLibFactory);
        log.info("Object wrapper: {}.", tagLibFactory.getObjectWrapper());
        if (tagLibFactory.getObjectWrapper() == null) {
            tagLibFactory.setObjectWrapper(objectWrapper);
        }
        log.info("Object wrapper: {}.", tagLibFactory.getObjectWrapper());
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(singletonList("/META-INF/security.tld"));
    }

    public static void main(String[] args) {
        SpringApplication.run(PetProjectApplication.class, args);
    }

}
