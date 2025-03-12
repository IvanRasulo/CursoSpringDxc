package com.banana;

import com.banana.condig.SpringConfig;
import com.banana.models.Student;
import com.banana.persistence.StudentsRepository;
import com.banana.persistence.StudentsRepositoryInf;
import com.banana.services.IStudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.banana.config.SpringConfig;
import com.banana.models.Student;
import com.banana.persistence.StudentsRepository;
import com.banana.persistence.StudentsRepositoryInf;
import com.banana.services.IStudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("School Manager.....");
        // Con xml config, casi no se usa
        // ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        // Con clases de configuración
        AnnotationConfigApplicationContext ctx =new AnnotationConfigApplicationContext();
        ctx.register(SpringConfig.class);
        ctx.refresh();

//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SpringConfig.class);
        ctx.refresh();

        StudentsRepositoryInf repo = ctx.getBean(StudentsRepositoryInf.class);
        System.out.println(repo);
        Student aStd = repo.getById(1L);
        System.out.println(aStd);

        IStudentService srv = ctx.getBean(IStudentService.class);
        System.out.println(srv);
        Student srvStd = srv.getStudentById(1L);
        System.out.println(srvStd);

        StudentsRepositoryInf repo = ctx.getBean(StudentsRepositoryInf.class);
        Student student = repo.getById(1L);
        System.out.println(repo);
        System.out.println(student);

        IStudentService serv = ctx.getBean(IStudentService.class);
        System.out.println(serv);
        Student srvStd = serv.getStudentById(1L);
        System.out.println(srvStd);
    }
}
