package ru.vadmark.petproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 05.01.2021
 */
//@SpringBootApplication
@Deprecated
public class PetProjectAppTest {/* implements CommandLineRunner {

    @Autowired
    UserEntityRepository userEntityRepository;

    public static void main(String[] args) {
        SpringApplication.run(PetProjectAppTest.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUN!");
        ClassPathResource classPathResource = new ClassPathResource("files/avatar.jpg");
        byte[] arrayData = new byte[(int) classPathResource.contentLength()];
        classPathResource.getInputStream().read(arrayData);
        UserEntity userEntity = userEntityRepository.findByUsername("a");
        userEntity.setAvatar(arrayData);
        userEntityRepository.save(userEntity);

        for(FileModel imageModel : fileRepository.findAll()){
      Files.write(Paths.get(  "download/" + imageModel.getName() + "." + imageModel.getType()), imageModel.getPic());
    }
    }*/
}