package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.ayla.json.AylaDeviceWrapper;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

public class AylaDeviceApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AylaDeviceApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/apiv1/devices";
    private static final String retrieveUri = "";

    public AylaDeviceApi() {
        super(baseUrl);
    }

    public List<AylaDevice> retrieveDevices(AylaAuthorizationByEmail auth) {

        List<AylaDevice> deviceList = new ArrayList<>();

        Iterable<AylaDeviceWrapper> devices = getIterable(retrieveUri, httpHeaders ->
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, "authToken " + auth.getAccessToken()),
                AylaDeviceWrapper.class);

        for (AylaDeviceWrapper deviceWrapper : devices) {
            if (deviceWrapper.getDevice() != null) {
                deviceList.add(deviceWrapper.getDevice());
            }
            log.debug(deviceWrapper.toString());
        }

        return deviceList;
    }
}
