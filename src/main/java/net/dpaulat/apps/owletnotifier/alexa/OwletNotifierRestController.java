package net.dpaulat.apps.owletnotifier.alexa;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwletNotifierRestController {

    @RequestMapping("/alexa")
    public void alexa() {
    }
}
