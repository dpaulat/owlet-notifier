package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.owletnotifier.alexa.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.validation.constraints.NotNull;

@SpringBootApplication(scanBasePackages = {"net.dpaulat.apps"})
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run();
    }

    @Bean
    public ServletRegistrationBean alexaServletBean(@NotNull ConfigProperties config,
                                                    @NotNull CancelAndStopIntentHandler cancelAndStopIntentHandler,
                                                    @NotNull HelpIntentHandler helpIntentHandler,
                                                    @NotNull FallbackIntentHandler fallbackIntentHandler,
                                                    @NotNull LaunchRequestHandler launchRequestHandler,
                                                    @NotNull SessionEndedRequestHandler sessionEndedRequestHandler) {
        ServletRegistrationBean<OwletNotifierSkillServlet> bean = new ServletRegistrationBean<>(
                new OwletNotifierSkillServlet(config,
                        cancelAndStopIntentHandler,
                        helpIntentHandler,
                        fallbackIntentHandler,
                        launchRequestHandler,
                        sessionEndedRequestHandler),
                "/alexa");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
