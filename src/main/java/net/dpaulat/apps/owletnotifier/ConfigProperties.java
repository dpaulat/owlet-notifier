package net.dpaulat.apps.owletnotifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("owlet-notifier.config")
@ConfigurationProperties()
@Validated
public class ConfigProperties {

    private final Map<String, String> devices;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    protected ConfigProperties() {
        devices = new HashMap<>();
    }

    public Map<String, String> getDevices() {
        return devices;
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
}
