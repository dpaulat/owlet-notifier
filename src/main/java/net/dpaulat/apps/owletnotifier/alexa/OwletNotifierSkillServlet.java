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
