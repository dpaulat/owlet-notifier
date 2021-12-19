/*
 * Copyright 2019-2021 Dan Paulat
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

package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.*;
import net.dpaulat.apps.owlet.OwletProperties;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * API to interact with the Ayla Device Service
 *
 * @link https://developer.aylanetworks.com/apibrowser/swaggers/DeviceService
 */
@Service
public class AylaDeviceApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AylaDeviceApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/apiv1";
    private static final String retrieveDevicesUri = "/devices";
    private static final String retrieveDevicePropertiesUri = "/dsns/%s/properties";
    private static final String createDatapointUri = "/dsns/%s/properties/%s/datapoints";

    public AylaDeviceApi(@NotNull ApplicationContext context) {
        super(context, baseUrl);
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

    public AylaAddDataPointOutput createDatapoint(AylaAuthorizationByEmail auth, AylaDevice device,
                                                  OwletProperties property, String value) {

        AylaAddDataPointInput addDataPointInput = new AylaAddDataPointInput(value);

        String uri = String.format(createDatapointUri, device.getDsn(), property.name());
        AylaAddDataPointOutput addDataPointOutput = post(uri, httpHeaders ->
                        httpHeaders.add(HttpHeaders.AUTHORIZATION, "authToken " + auth.getAccessToken()),
                BodyInserters.fromValue(addDataPointInput), AylaAddDataPointOutput.class);

        log.debug(addDataPointOutput.toString());

        return addDataPointOutput;
    }
}
