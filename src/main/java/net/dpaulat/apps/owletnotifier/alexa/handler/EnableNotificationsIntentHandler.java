package net.dpaulat.apps.owletnotifier.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Permissions;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.services.ServiceException;
import com.amazon.ask.model.services.reminderManagement.GetRemindersResponse;
import com.amazon.ask.model.services.reminderManagement.Reminder;
import com.amazon.ask.model.services.reminderManagement.ReminderRequest;
import com.amazon.ask.model.services.reminderManagement.ReminderResponse;
import com.amazon.ask.request.Predicates;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnableNotificationsIntentHandler extends OwletNotifierRequestHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(EnableNotificationsIntentHandler.class);

    private final @NotNull ConfigProperties config;
    private final @NotNull ReminderRepository reminderRepository;

    public EnableNotificationsIntentHandler(@NotNull ConfigProperties config,
                                            @NotNull ReminderRepository reminderRepository) {
        this.config = config;
        this.reminderRepository = reminderRepository;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("EnableNotifications"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Permissions permissions = handlerInput
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getPermissions();

        if (permissions == null || permissions.getConsentToken() == null) {
            String speechText = "In order to enable notifications, this skill needs permission to create reminders.  Please grant permission using the card sent to your Alexa app.";
            List<String> list = new ArrayList<>();
            list.add("alexa::alerts:reminders:skill:readwrite");
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechText)
                    .withAskForPermissionsConsentCard(list)
                    .build();
        }

        String userId = handlerInput
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getUserId();
        String deviceId = handlerInput
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getDevice()
                .getDeviceId();

        log.info("Enabling notifications for {}", deviceId);

        Optional<ReminderEntity> storedReminder = reminderRepository.findByDeviceId(deviceId);
        boolean reminderExists = false;
        if (storedReminder.isPresent()) {
            GetRemindersResponse getRemindersResponse = handlerInput
                    .getServiceClientFactory()
                    .getReminderManagementService()
                    .getReminders();

            // Search current reminder list for stored reminder
            for (Reminder reminder : getRemindersResponse.getAlerts()) {
                if (reminder.getAlertToken().equals(storedReminder.get().getAlertToken())) {
                    reminderExists = true;
                }
            }

            // Stored reminder no longer exists, need to create a new one
            if (!reminderExists) {
                reminderRepository.delete(storedReminder.get());
            }
        }

        String speechText;

        if (!reminderExists) {
            ReminderResponse reminderResponse = createReminder(handlerInput);

            if (reminderResponse != null) {
                reminderRepository.save(new ReminderEntity(userId, deviceId, reminderResponse.getAlertToken()));
                speechText = "OK, notifications are enabled on this device using reminders.";
            } else {
                speechText = "Something went wrong trying to enable notifications on this device.";
            }
        } else {
            speechText = "Notifications are already enabled on this device.";
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                .withReprompt(speechText)
                .build();
    }

    private ReminderResponse createReminder(HandlerInput handlerInput) {
        ReminderResponse reminderResponse = null;

        ReminderRequest reminderRequest = createReminderRequest(
                config.getAlexa().getPlaceholderReminder(),
                false,
                false);

        try {
            reminderResponse = handlerInput
                    .getServiceClientFactory()
                    .getReminderManagementService()
                    .createReminder(reminderRequest);
        } catch (ServiceException ex) {
            log.info(ex.toString());
        }

        return reminderResponse;
    }
}
