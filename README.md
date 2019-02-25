# Application Setup

1. Add your Owlet credentials to owlet-notifier.config.
2. Configure monitoring conditions in owlet-notifier.config, using the samples as guidance.
3. Configure SSL to work with Alexa using ONE of the following methods.  You will need a domain or subdomain for Alexa to communicate with.  Note Alexa will only work over port 443.
	1. Configure an Apache proxy.  The application.properties file is setup for this method already.
	2. Configure Tomcat to use SSL.  The application.properties file will need modified for this method.
		1. Follow the instructions [here](https://www.baeldung.com/spring-boot-https-self-signed-certificate) to generate a PKCS12 keystore.
		2. You will also need to export your key in X.509 format:
			- keytool -exportcert -keystore <keystore.p12> -storetype PKCS12 -alias <alias> -rfc -file <filename.crt>

# Alexa Skill Setup

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

