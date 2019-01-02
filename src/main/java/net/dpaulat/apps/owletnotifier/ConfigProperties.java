package net.dpaulat.apps.owletnotifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        public static class Monitor {
            @NotBlank
            private String name;
            private Long minimumValue;
            private Long maximumValue;
            private Long repeatTime;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Long getMinimumValue() {
                return minimumValue;
            }

            public void setMinimumValue(Long minimumValue) {
                this.minimumValue = minimumValue;
            }

            public Long getMaximumValue() {
                return maximumValue;
            }

            public void setMaximumValue(Long maximumValue) {
                this.maximumValue = maximumValue;
            }

            public Long getRepeatTime() {
                return repeatTime;
            }

            public void setRepeatTime(Long repeatTime) {
                this.repeatTime = repeatTime;
            }

            @Override
            public String toString() {
                return "Monitor{" +
                        "name='" + name + '\'' +
                        ", minimumValue=" + minimumValue +
                        ", maximumValue=" + maximumValue +
                        ", repeatTime=" + repeatTime +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Monitor monitor = (Monitor) o;
                return Objects.equals(name, monitor.name) &&
                        Objects.equals(minimumValue, monitor.minimumValue) &&
                        Objects.equals(maximumValue, monitor.maximumValue) &&
                        Objects.equals(repeatTime, monitor.repeatTime);
            }

            @Override
            public int hashCode() {
                return Objects.hash(name, minimumValue, maximumValue, repeatTime);
            }
        }
    }
}
