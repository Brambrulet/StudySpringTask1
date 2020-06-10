package ru.volnenko.se;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.context.event.DefaultEventListenerFactory;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.volnenko.se.api.service.IBootstrap;
import ru.volnenko.se.api.component.AbstractCommand;
import ru.volnenko.se.error.CommandAbsentException;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@ComponentScan("ru.volnenko.se")
@EnableAsync
public class App {

    /**
     * Костыль.
     * Вставка текстовой константы команды в SpEl для потомков AbstractCommand
     */
    @Bean
    public EventListenerFactory createEventListenerFactory() {
        DefaultEventListenerFactory factory = new DefaultEventListenerFactory() {
            @Override
            public ApplicationListener<?> createApplicationListener(String command, Class<?> type, Method method) {
                return new ApplicationListenerMethodAdapter(command, type, method) {
                    @Override
                    protected String getCondition() {
                        String condition = super.getCondition();
                        return !AbstractCommand.class.isAssignableFrom(type) || condition == null
                                ? condition : condition.replace("#command", "'" + command + "'");
                    }
                };
            }
        };

        factory.setOrder(0);
        return factory;
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("SummerSpringHomework-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        if (context.getBeansOfType(AbstractCommand.class).isEmpty()) {
            throw new CommandAbsentException();
        }
        context.getBean(IBootstrap.class).start();
    }

}
