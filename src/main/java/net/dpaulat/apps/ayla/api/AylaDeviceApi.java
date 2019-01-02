package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.*;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * API to interact with the Ayla Device Service
 *
 * @link https://developer.aylanetworks.com/apibrowser/swaggers/DeviceService
 */
public class AylaDeviceApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AylaDeviceApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/apiv1";
    private static final String retrieveDevicesUri = "/devices";
    private static final String retrieveDevicePropertiesUri = "/dsns/%s/properties";

    public AylaDeviceApi() {
        super(baseUrl);
    }

    public List<AylaDevice> retrieveDevices(AylaAuthorizationByEmail auth) {

        List<AylaDevice> deviceList = new ArrayList<>();

        Iterable<AylaDeviceWrapper> devices = getIterable(retrieveDevicesUri, httpHeaders ->
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

    public List<AylaDevProperty> retrieveDeviceProperties(AylaAuthorizationByEmail auth, AylaDevice device) {

        List<AylaDevProperty> propertyList = new ArrayList<>();

        String uri = String.format(retrieveDevicePropertiesUri, device.getDsn());
        Iterable<AylaDevPropertyWrapper> properties = getIterable(uri, httpHeaders ->
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, "authToken " + auth.getAccessToken()),
                AylaDevPropertyWrapper.class);

        for (AylaDevPropertyWrapper propertyWrapper : properties) {
            if (propertyWrapper.getProperty() != null) {
                propertyList.add(propertyWrapper.getProperty());
            }
            log.debug(propertyWrapper.toString());
        }

        return propertyList;
    }
}
