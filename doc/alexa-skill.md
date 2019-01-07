# Application Setup

1. Follow the instructions [here](https://www.baeldung.com/spring-boot-https-self-signed-certificate) to generate a PKCS12 keystore.
2. You will also need to export your key in X.509 format:
    - keytool -exportcert -keystore <keystore.p12> -storetype PKCS12 -alias <alias> -rfc -file <filename.crt>

# Alexa Skill Setup

In order to integrate with Alexa, you'll want to create an Alexa Skill using the [Alexa Skills Kit Developer Console](https://developer.amazon.com/alexa/console/ask).  Use the Custom skill model for your new skill.

Follow the Skill builder checklist to continue.

1. Select an Invocation Name.  This example will use "owlet notifier".
2. Configure Intents, Samples, and Slots.
    - Create the following custom intent:
        + Name: add_reminders
        + Sample Utterance: "add reminders"
3. Save and Build the Model.
4. Add an Endpoint.  Since Alexa will interact with the Spring application running on our machine, we will select HTTPS.
    - Configure a default region URI.  You can either use a fixed IP address, or define a hostname to use, followed by an optional port, then "/alexa".  Example:  https://example.com:8443/alexa
    - Select "I will upload a self-signed certificate in X 509 format".
        + Note that we can't publish a skill with a self-signed certificate.  This is OK, since we don't intend to publish our skill.

