package net.dpaulat.apps.owletnotifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
            @NotNull
            private MonitorType type;
            private Long value;
            private Long repeatTime;
            @NotBlank
            private String activeMessage = "%1$s's %2$s is %3$s";
            @NotBlank
            private String deactivateMessage = "%1$s's %2$s is %3$s";

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public MonitorType getType() {
                return type;
            }

            public void setType(MonitorType type) {
                this.type = type;
            }

            public Long getValue() {
                return value;
            }

            public void setValue(Long value) {
                this.value = value;
            }

            public Long getRepeatTime() {
                return repeatTime;
            }

            public void setRepeatTime(Long repeatTime) {
                this.repeatTime = repeatTime;
            }

            public String getActiveMessage() {
                return activeMessage;
            }

            public void setActiveMessage(String activeMessage) {
                this.activeMessage = activeMessage;
            }

            public String getDeactivateMessage() {
                return deactivateMessage;
            }

            public void setDeactivateMessage(String deactivateMessage) {
                this.deactivateMessage = deactivateMessage;
            }

            @Override
            public int hashCode() {
                return Objects.hash(name, type, value, repeatTime, activeMessage, deactivateMessage);
            }

            @Override
            public String toString() {
                return "Monitor{" +
                        "name='" + name + '\'' +
                        ", type=" + type +
                        ", value=" + value +
                        ", repeatTime=" + repeatTime +
                        ", activeMessage='" + activeMessage + '\'' +
                        ", deactivateMessage='" + deactivateMessage + '\'' +
                        '}';
            }

            public enum MonitorType {
                MinimumValue,
                MaximumValue,
                Equals
            }
        }
    }
}
