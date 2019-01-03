package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("owlet-notifier.config")
@ConfigurationProperties()
@Validated
public class ConfigProperties {

    private final Owlet owlet = new Owlet();

    protected ConfigProperties() {
    }

    public Owlet getOwlet() {
        return owlet;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "owlet=" + owlet +
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
}
