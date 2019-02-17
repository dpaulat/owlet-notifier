package net.dpaulat.apps.owletnotifier.alexa.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReminderRepository extends CrudRepository<ReminderEntity, Long> {
    List<ReminderEntity> findByDeviceId(String deviceId);

    List<ReminderEntity> findByAlertToken(String alertToken);
}
