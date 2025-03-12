package com.banana.config;

import com.banana.persistence.StudentRepositoryProd;
import com.banana.persistence.StudentsRepository;
import com.banana.persistence.StudentsRepositoryInf;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
public class ReposConfig {
    @Bean
    public StudentsRepositoryInf getStudentRepoBean(){
        System.out.println("Creando repo test");
        return new StudentsRepository();

    }

    @Bean
    @Profile("prod")
    public StudentsRepositoryInf getStudentRepoProdBean(){
        System.out.println("Creando repo prod");
        return new StudentRepositoryProd();

    }
}
