package ru.wain.springmvcapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;

/***
 * Аналог applicationContextMVC.xml
 * https://www.baeldung.com/thymeleaf-in-spring-mvc
 */
@Configuration
@ComponentScan("ru.wain.springmvcapp")
@EnableWebMvc // замена <mvc:annotation-driven/> в xml
public class SpringConfig implements WebMvcConfigurer { // Интерфес используется для настройки спринг под себя

    private final ApplicationContext applicationContext; //контекст будет добавлен автоматически (Dependency Injection)

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /*
    <bean id="templateResolver" class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".html"/>
    </bean>
     */

    //Для работы JDBC-template
    //В нём описываются все параметры подключения к БД,
    //Что позволяет избегать дублирования большого количества кода в DAO
    //И сократить размер запросов к БД из DAO

    @Bean
    public DataSource dataSource() {
        String URL = "jdbc:postgresql://localhost:5432/first_db";
        String USERNAME = "postgres";
        String PASSWORD = "postgres";
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl(URL);
        driverManagerDataSource.setUsername(USERNAME);
        driverManagerDataSource.setPassword(PASSWORD);

        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/"); //путь к представлениям
        templateResolver.setSuffix(".html"); //расширение представлений
        return templateResolver;
    }

    /*
    <bean id="templateEngine" class="org.thymeleaf.spring5.SpringTemplateEngine">
        <property name="templateResolver" ref="templateResolver"/>
        <property name="enableSpringELCompiler" value="true"/>
    </bean>
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /*
    <bean class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="templateEngine" ref="templateEngine"/>
        <property name="order" value="1"/>
        <property name="viewNames" value="*"/>
    </bean>
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) { // Метод для конфигурации шаблонизатора thymeleaf
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}
