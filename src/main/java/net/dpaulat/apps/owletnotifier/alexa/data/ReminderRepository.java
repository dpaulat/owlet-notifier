/*
 * Copyright 2019 Dan Paulat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
