package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaDeviceApi;
import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevProperty;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.json.OwletApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private AylaDeviceApi aylaDeviceApi;
    private AylaUsersApi aylaUsersApi;

    private OwletApplication owletApplication;

    private AylaAuthorizationByEmail authorization;
    private List<AylaDevice> deviceList;
    private Map<String, Map<String, String>> deviceMap;

    public OwletApi() {
        this.aylaDeviceApi = new AylaDeviceApi();
        this.aylaUsersApi = new AylaUsersApi();
        this.authorization = null;
        this.deviceList = null;
        this.deviceMap = new HashMap<>();
        this.owletApplication = new OwletApplication();
    }

    public void signIn(String email, String password) {
        authorization = aylaUsersApi.signIn(email, password, owletApplication);
    }

    public void refreshToken() {
        if (authorization != null) {
            authorization = aylaUsersApi.refreshToken(authorization);
        }
    }

    public List<AylaDevice> retrieveDevices() {
        if (authorization != null) {
            deviceList = aylaDeviceApi.retrieveDevices(authorization);
        }

        return deviceList;
    }

    public void updateProperties(AylaDevice device, IOwletPropertyUpdated onUpdate) {
        List<AylaDevProperty> propertyList = null;

        if (authorization != null) {
            propertyList = aylaDeviceApi.retrieveDeviceProperties(authorization, device);
        }

        if (propertyList != null) {
            Map<String, String> propertyMap;
            if (deviceMap.containsKey(device.getDsn())) {
                propertyMap = deviceMap.get(device.getDsn());
            } else {
                propertyMap = new HashMap<>();
                deviceMap.put(device.getDsn(), propertyMap);
            }

            for (AylaDevProperty property : propertyList) {
                if (onUpdate != null) {
                    onUpdate.callback(property.getName(), propertyMap.get(property.getName()), property.getValue());
                }
                propertyMap.put(property.getName(), property.getValue());
            }
        }
    }

    public boolean isSignedIn() {
        return authorization != null;
    }
}
