package com.banana.config;

import com.banana.persistence.StudentsRepositoryInf;
import com.banana.services.IStudentService;
import com.banana.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

        @Autowired
        StudentsRepositoryInf studentRepo;
    //Para en vez de gestionar Spring el ciclo , manejarlo nosotros
    @Bean
    public IStudentService getStudentServiceBean() {
        StudentsService srv = new StudentsService();
        srv.setRepository(studentRepo);
        return srv;
    }

}
