package net.dpaulat.apps.owletnotifier.alexa;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Permissions;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.services.ServiceException;
import com.amazon.ask.model.services.reminderManagement.*;
import com.amazon.ask.request.Predicates;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnableNotificationsIntentHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(EnableNotificationsIntentHandler.class);

    private final @NotNull ConfigProperties config;
    private final @NotNull OwletApi owletApi;
    private final @NotNull ReminderRepository reminderRepository;

    public EnableNotificationsIntentHandler(@NotNull ConfigProperties config, @NotNull OwletApi owletApi,
                                            @NotNull ReminderRepository reminderRepository) {
        this.config = config;
        this.owletApi = owletApi;
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

        List<ReminderEntity> reminderList = reminderRepository.findByDeviceId(deviceId);
        boolean reminderExists = false;
        if (!reminderList.isEmpty()) {
            ReminderEntity storedReminder = reminderList.get(0);

            GetRemindersResponse getRemindersResponse = handlerInput
                    .getServiceClientFactory()
                    .getReminderManagementService()
                    .getReminders();

            // Search current reminder list for stored reminder
            for (Reminder reminder : getRemindersResponse.getAlerts()) {
                if (reminder.getAlertToken().equals(storedReminder.getAlertToken())) {
                    reminderExists = true;
                }
            }

            // Stored reminder no longer exists, need to create a new one
            if (!reminderExists) {
                reminderRepository.delete(storedReminder);
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

        SpokenText spokenText = SpokenText.builder()
                .withText("This is a placeholder reminder.")
                .build();

        AlertInfoSpokenInfo alertInfoSpokenInfo = AlertInfoSpokenInfo.builder()
                .addContentItem(spokenText)
                .build();

        AlertInfo alertInfo = AlertInfo.builder()
                .withSpokenInfo(alertInfoSpokenInfo)
                .build();

        // Place the reminder absurdly in the future
        LocalDateTime triggerTime = LocalDateTime.of(2199, 01, 01, 00, 00, 00);

        Trigger trigger = Trigger.builder()
                .withType(TriggerType.SCHEDULED_ABSOLUTE)
                .withScheduledTime(triggerTime)
                .build();

        PushNotification pushNotification = PushNotification.builder()
                .withStatus(PushNotificationStatus.DISABLED)
                .build();

        ReminderRequest reminderRequest = ReminderRequest.builder()
                .withAlertInfo(alertInfo)
                .withRequestTime(OffsetDateTime.now())
                .withTrigger(trigger)
                .withPushNotification(pushNotification)
                .build();

        try {
            reminderResponse = handlerInput.getServiceClientFactory().getReminderManagementService().createReminder(reminderRequest);
        } catch (ServiceException ex) {
            log.info(ex.toString());
        }

        return reminderResponse;
    }

    private GetRemindersResponse getReminders(HandlerInput handlerInput) {
        return handlerInput.getServiceClientFactory().getReminderManagementService().getReminders();
    }
}
