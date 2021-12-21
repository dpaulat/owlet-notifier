# Owlet Notifier

The Owlet Notifier application allows you to set custom alerts, and integrate with an Alexa-powered smart home.

## Notice

This code is in no way affiliated with, authorized, maintained, sponsored or endorsed by Owlet Baby Care or any of its affiliates or subsidiaries. This is an independent and unofficial API. While I do my best to provide this service, and use it with my own child, I cannot provide any warranty. **Use at your own risk.**

## Application Setup

1. These instructions assume your Owlet has already been setup, and you can access vitals using the official app.
2. Install a JDK of your choice, 1.8+
3. Build: `./gradlew bootJar`
4. Create an owlet-notifier.config and application.properties file based on the examples, somewhere on the classpath (e.g., build/classes/java/main/ - NOTE: Gradle will remove this directory on clean, so back it up!)
5. Customize owlet-notifier.config:
   - Add your Owlet credentials to owlet-notifier.config.
   - Configure monitoring conditions in owlet-notifier.config, using the samples as guidance.
   - Review other options in owlet-notifier.config for customization.  If you need to edit your configuration for multiple devices, each DSN will be printed on startup.
6. Configure SSL to work with Alexa using ONE of the following methods.  You will need a domain or subdomain for Alexa to communicate with.  Note Alexa will only work over port 443.
    1. Configure an Apache proxy.  The application.properties file is setup for this method already.
    2. Configure Tomcat to use SSL.  The application.properties file will need modified for this method.
        1. Follow the instructions [here](https://www.baeldung.com/spring-boot-https-self-signed-certificate) to generate a PKCS12 keystore.
        2. You will also need to export your key in X.509 format:
            - keytool -exportcert -keystore <keystore.p12> -storetype PKCS12 -alias <alias> -rfc -file <filename.crt>
7. Run: `./gradlew bootRun`

## Alexa Skill Setup

In order to integrate with Alexa, you'll want to create an Alexa Skill using the [Alexa Skills Kit Developer Console](https://developer.amazon.com/alexa/console/ask).  Use the Custom skill model for your new skill.  Add the Skill ID to owlet-notifier.config.

Follow the Skill builder checklist to continue.

1. Select an Invocation Name.  This example will use "owlet notifier".
2. Configure Intents, Samples, and Slots.
    - Create the following custom intents:
        + Name: ReadVitals
			- Sample Utterance: "read vitals"
		+ Name: StartMonitoring
            - Sample Utterance: "enable monitoring"
            - Sample Utterance: "start monitoring"
		+ Name: StopMonitoring
			- Sample Utterance: "disable monitoring"
			- Sample Utterance: "stop monitoring"
		+ Name: EnableNotifications
			- Sample Utterance: "enable notifications"
		+ Name: DisableNotifications
			- Sample Utterance: "disable notifications"
3. Configure permissions, and enable Reminders.
	- Use the Alexa Skill Messaging Client Id and Client Secret in owlet-notifier.config.
4. Save and Build the Model.
5. Add an Endpoint.  Since Alexa will interact with the Spring application running on our machine, we will select HTTPS.
    - Configure a default region URI.  You can either use a fixed IP address, or define a hostname to use, then "/alexa".  Example:  https://example.com/alexa
    - Select "I will upload a self-signed certificate in X 509 format", unless you have generated one from an SSL provider.
        + Note that we can't publish a skill with a self-signed certificate.  This is OK, since we don't intend to publish our skill.  In fact, it is currently designed to work with a single Owlet account.

## Notes

- Notifications must be enabled on each Alexa device you wish to receive notifications on.
- The application is designed to stop monitoring after the sock has been charging for a minute or longer.  You will need to re-enable monitoring when the sock has been placed.

## Credits

Thanks to Owlet Baby Care for a great device!

Thanks to the following people for their work on integrating with the Owlet device:

- @puco
- @bobcat0070
- @arosequist
- @mitch-b
- @mbevand
