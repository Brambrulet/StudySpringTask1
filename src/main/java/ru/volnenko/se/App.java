package ru.volnenko.se;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.context.event.DefaultEventListenerFactory;
import org.springframework.context.event.EventListenerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.volnenko.se.api.component.IInputProvider;
import ru.volnenko.se.api.command.ICommand;
import ru.volnenko.se.error.CommandAbsentException;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@EnableTransactionManagement
@PropertySource("classpath:db-conf.properties")
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
                        return !ICommand.class.isAssignableFrom(type) || condition == null
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

    @Bean
    public DataSource dataSource(@Value("${datasource.driver}") final String dataSourceDriver,
                                 @Value("${datasource.url}") final String dataSourceUrl,
                                 @Value("${datasource.user}") final String dataSourceUser,
                                 @Value("${datasource.password}") final String dataSourcePassword) {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceDriver);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUser);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            final LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
                                                                       @Value("${hibernate.show_sql}") final boolean showSql,
                                                                       @Value("${hibernate.hbm2ddl.auto}") final String tableStrategy,
                                                                       @Value("${hibernate.dialect}") final String dialect) {
        final LocalContainerEntityManagerFactoryBean factoryBean;
        factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("ru.volnenko.se.entity");
        final Properties properties = new Properties();
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.hbm2ddl.auto", tableStrategy);
        properties.put("hibernate.dialect", dialect);
        factoryBean.setJpaProperties(properties);
        return factoryBean;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        if (context.getBeansOfType(ICommand.class).isEmpty()) {
            throw new CommandAbsentException();
        }
        context.getBean(IInputProvider.class).start();
    }
}
