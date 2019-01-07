package net.dpaulat.apps.owletnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"net.dpaulat.apps"})
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run();
    }
}
