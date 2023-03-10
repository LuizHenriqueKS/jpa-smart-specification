package io.github.zul.springsmartspecification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SpringSmartSpecificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSmartSpecificationApplication.class, args);
    }

}
