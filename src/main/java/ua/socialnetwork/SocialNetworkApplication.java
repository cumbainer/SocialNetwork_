package ua.socialnetwork;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;


import javax.sql.DataSource;

@SpringBootApplication
public class SocialNetworkApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkApplication.class, args);
    }
}
