package net.dpaulat.apps.owletnotifier.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.MessageReceivedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.messaging.MessageReceivedRequest;
import com.amazon.ask.model.services.reminderManagement.GetRemindersResponse;
import com.amazon.ask.model.services.reminderManagement.Reminder;
import com.amazon.ask.model.services.reminderManagement.ReminderRequest;
import com.amazon.ask.model.services.reminderManagement.Status;
import net.dpaulat.apps.alexa.api.ISkillMessage;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import net.dpaulat.apps.owletnotifier.alexa.message.SynchronizeReminders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

@Service
public class SynchronizeRemindersHandler extends OwletNotifierRequestHandler implements MessageReceivedRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(SynchronizeRemindersHandler.class);

    private final ConfigProperties config;
    private final OwletApi owletApi;
    private final ReminderRepository reminderRepository;

    public SynchronizeRemindersHandler(@NotNull ConfigProperties config, @NotNull OwletApi owletApi,
                                       @NotNull ReminderRepository reminderRepository) {
        this.config = config;
        this.owletApi = owletApi;
        this.reminderRepository = reminderRepository;
    }

    @Override
    public boolean canHandle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        Map<String, Object> message = messageReceivedRequest.getMessage();
        return (message.containsKey(ISkillMessage.TYPE) &&
                message.get(ISkillMessage.TYPE).equals(SynchronizeReminders.class.getSimpleName()));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        String userId = input
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getUserId();

        log.info("Synchronizing reminders for user {}", userId);

        GetRemindersResponse getRemindersResponse = input
                .getServiceClientFactory()
                .getReminderManagementService()
                .getReminders();

        // Ensure each current reminder is stored in the database.  If it's unknown, remove it.
        for (ReminderEntity localReminder : reminderRepository.findByUserId(userId)) {
            boolean localReminderExists = false;

            for (Reminder remoteReminder : getRemindersResponse.getAlerts()) {
                if (localReminder.getAlertToken().equals(remoteReminder.getAlertToken())) {
                    localReminderExists = true;
                }
            }

            if (!localReminderExists) {
                log.info("Local reminder {} not found on servers, deleting", localReminder.getAlertToken());
                reminderRepository.delete(localReminder);
            }
        }

        for (Reminder remoteReminder : getRemindersResponse.getAlerts()) {
            if (!reminderRepository.existsByAlertToken(remoteReminder.getAlertToken())) {
                // Only delete the remote reminder if it's not yet completed
                if (remoteReminder.getStatus() != Status.COMPLETED) {
                    log.info("Remote reminder {} not found in database, deleting", remoteReminder.getAlertToken());
                    input.getServiceClientFactory()
                            .getReminderManagementService()
                            .deleteReminder(remoteReminder.getAlertToken());
                }
            } else {
                log.info("Found remote reminder {}", remoteReminder.getAlertToken());
                log.debug("{}", remoteReminder);

                // If we aren't monitoring, and the reminder is completed, refresh it
                if (!owletApi.isAnyMonitoringEnabled() && remoteReminder.getStatus() == Status.COMPLETED) {
                    log.info("Refreshing remote reminder: {}", remoteReminder.getAlertToken());

                    ReminderRequest reminderRequest = createReminderRequest(
                            config.getAlexa().getPlaceholderReminder(),
                            false,
                            false);
                    input.getServiceClientFactory()
                            .getReminderManagementService()
                            .updateReminder(remoteReminder.getAlertToken(), reminderRequest);
                }
            }
        }

        return input.getResponseBuilder().build();
    }
}
