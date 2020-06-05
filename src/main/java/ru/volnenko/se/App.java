package ru.volnenko.se;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.volnenko.se.api.service.IBootstrap;

public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("ru.volnenko.se");
        context.getBean(IBootstrap.class).start();
    }

}
