package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAsync
@PropertySource("owlet-notifier.config")
@ConfigurationProperties()
@Validated
public class ConfigProperties {

    private final Owlet owlet = new Owlet();
    private final Alexa alexa = new Alexa();

    protected ConfigProperties() {
    }

    public Owlet getOwlet() {
        return owlet;
    }

    public Alexa getAlexa() {
        return alexa;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
               "owlet=" + owlet +
               "alexa=" + alexa +
               '}';
    }

    public static class Owlet {
        private final List<Monitor> monitors = new ArrayList<>();
        @NotBlank
        private String email;
        @NotBlank
        private String password;

        public List<Monitor> getMonitors() {
            return monitors;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Owlet{" +
                   "monitors=" + monitors +
                   ", email='" + email + '\'' +
                   ", password='***'" +
                   '}';
        }
    }

    public static class Alexa {
        private String clientId;
        private String clientSecret;
        private String skillId;
        private String cardTitle;
        private String placeholderReminder;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getSkillId() {
            return skillId;
        }

        public void setSkillId(String skillId) {
            this.skillId = skillId;
        }

        public String getCardTitle() {
            return cardTitle;
        }

        public void setCardTitle(String cardTitle) {
            this.cardTitle = cardTitle;
        }

        public String getPlaceholderReminder() {
            return placeholderReminder;
        }

        public void setPlaceholderReminder(String placeholderReminder) {
            this.placeholderReminder = placeholderReminder;
        }

        @Override
        public String toString() {
            return "Alexa{" +
                   "clientId='" + clientId + '\'' +
                   ", clientSecret='***'" +
                   ", skillId='" + skillId + '\'' +
                   ", cardTitle='" + cardTitle + '\'' +
                   ", placeholderReminder='" + placeholderReminder + '\'' +
                   '}';
        }
    }
}
