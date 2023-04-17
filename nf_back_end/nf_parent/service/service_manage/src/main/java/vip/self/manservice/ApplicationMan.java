package vip.self.manservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("vip.self")
public class ApplicationMan {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMan.class);
    }
}
