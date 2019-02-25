/*
 * Copyright 2019 Dan Paulat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
                                                    @NotNull DisableNotificationsIntentHandler disableNotificationsIntentHandler,
                                                    @NotNull EnableNotificationsIntentHandler enableNotificationsIntentHandler,
                                                    @NotNull HelpIntentHandler helpIntentHandler,
                                                    @NotNull FallbackIntentHandler fallbackIntentHandler,
                                                    @NotNull LaunchRequestHandler launchRequestHandler,
                                                    @NotNull NotificationMessageHandler notificationMessageHandler,
                                                    @NotNull ReadVitalsIntentHandler readVitalsIntentHandler,
                                                    @NotNull SessionEndedRequestHandler sessionEndedRequestHandler,
                                                    @NotNull SynchronizeRemindersHandler synchronizeRemindersHandler,
                                                    @NotNull StartMonitoringIntentHandler startMonitoringIntentHandler,
                                                    @NotNull StopMonitoringIntentHandler stopMonitoringIntentHandler) {
        ServletRegistrationBean<OwletNotifierSkillServlet> bean = new ServletRegistrationBean<>(
                new OwletNotifierSkillServlet(config,
                        cancelAndStopIntentHandler,
                        disableNotificationsIntentHandler,
                        enableNotificationsIntentHandler,
                        helpIntentHandler,
                        fallbackIntentHandler,
                        launchRequestHandler,
                        notificationMessageHandler,
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
