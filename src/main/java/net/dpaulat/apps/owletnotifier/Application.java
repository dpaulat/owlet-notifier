package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.owletnotifier.alexa.OwletNotifierSkillServlet;
import net.dpaulat.apps.owletnotifier.alexa.handler.*;
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
                                                    @NotNull EnableNotificationsIntentHandler enableNotificationsIntentHandler,
                                                    @NotNull HelpIntentHandler helpIntentHandler,
                                                    @NotNull FallbackIntentHandler fallbackIntentHandler,
                                                    @NotNull LaunchRequestHandler launchRequestHandler,
                                                    @NotNull ReadVitalsIntentHandler readVitalsIntentHandler,
                                                    @NotNull SessionEndedRequestHandler sessionEndedRequestHandler,
                                                    @NotNull SynchronizeRemindersHandler synchronizeRemindersHandler,
                                                    @NotNull StartMonitoringIntentHandler startMonitoringIntentHandler,
                                                    @NotNull StopMonitoringIntentHandler stopMonitoringIntentHandler) {
        ServletRegistrationBean<OwletNotifierSkillServlet> bean = new ServletRegistrationBean<>(
                new OwletNotifierSkillServlet(config,
                        cancelAndStopIntentHandler,
                        enableNotificationsIntentHandler,
                        helpIntentHandler,
                        fallbackIntentHandler,
                        launchRequestHandler,
                        readVitalsIntentHandler,
                        sessionEndedRequestHandler,
                        synchronizeRemindersHandler,
                        startMonitoringIntentHandler,
                        stopMonitoringIntentHandler),
                "/alexa");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
