package net.dpaulat.apps.owletnotifier.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.MessageReceivedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.messaging.MessageReceivedRequest;
import com.amazon.ask.model.services.reminderManagement.GetRemindersResponse;
import com.amazon.ask.model.services.reminderManagement.Reminder;
import net.dpaulat.apps.alexa.api.ISkillMessage;
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
public class SynchronizeRemindersHandler implements MessageReceivedRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(SynchronizeRemindersHandler.class);

    private final ReminderRepository reminderRepository;

    public SynchronizeRemindersHandler(@NotNull ReminderRepository reminderRepository) {
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
                log.info("Remote reminder {} not found in database, deleting", remoteReminder.getAlertToken());
                input.getServiceClientFactory()
                        .getReminderManagementService()
                        .deleteReminder(remoteReminder.getAlertToken());
            }
        }

        return input.getResponseBuilder().build();
    }
}
