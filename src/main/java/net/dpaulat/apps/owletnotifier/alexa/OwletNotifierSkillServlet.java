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

package net.dpaulat.apps.owletnotifier.alexa;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.servlet.SkillServlet;
import net.dpaulat.apps.owletnotifier.ConfigProperties;

import javax.validation.constraints.NotNull;

public class OwletNotifierSkillServlet extends SkillServlet {

    public OwletNotifierSkillServlet(@NotNull ConfigProperties config,
                                     RequestHandler... handlers) {
        super(getSkill(config, handlers));
    }

    private static Skill getSkill(ConfigProperties config, RequestHandler... handlers) {
        return Skills.standard()
                .addRequestHandlers(handlers)
                .withSkillId(config.getAlexa().getSkillId())
                .build();
    }
}
