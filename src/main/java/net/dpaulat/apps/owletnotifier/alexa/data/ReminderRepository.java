package net.dpaulat.apps.owletnotifier.alexa.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends CrudRepository<ReminderEntity, Long> {

    boolean existsByAlertToken(String alertToken);

    List<ReminderEntity> findByUserId(String userId);

    Optional<ReminderEntity> findByDeviceId(String deviceId);

    @Query("SELECT DISTINCT r.userId FROM ReminderEntity r")
    List<String> findDistinctUserId();
}
