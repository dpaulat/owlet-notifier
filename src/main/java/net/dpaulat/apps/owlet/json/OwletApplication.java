package net.dpaulat.apps.owlet.json;

import net.dpaulat.apps.ayla.json.AylaApplication;

public class OwletApplication extends AylaApplication {

    public OwletApplication() {
        super();
    }

    public static OwletApplication create() {
        OwletApplication application = new OwletApplication();
        application.setAppId("OWL-id");
        application.setAppSecret("OWL-4163742");
        return application;
    }
}
