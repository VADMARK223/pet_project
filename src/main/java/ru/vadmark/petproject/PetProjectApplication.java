package ru.vadmark.petproject;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import static java.util.Collections.singletonList;

@Slf4j
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
}
