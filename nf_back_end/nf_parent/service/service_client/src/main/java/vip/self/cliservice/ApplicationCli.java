package vip.self.cliservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("vip.self")
public class ApplicationCli {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ApplicationCli.class);
    }
}
