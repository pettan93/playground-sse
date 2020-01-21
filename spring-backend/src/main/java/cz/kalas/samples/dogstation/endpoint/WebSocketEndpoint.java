package cz.kalas.samples.dogstation.endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebSocketEndpoint {

    @MessageMapping("/create")
    @SendTo("/events/get")
    public String greeting(String message) throws Exception {
        log.info("greeting ws endponit call!");
        Thread.sleep(1000); // simulated delay
        return "Hello, " + HtmlUtils.htmlEscape(message) + "!";
    }


    @SubscribeMapping("/events/get")
    public String findAll() {
        log.info("findAll ws endponit call!");
        return "ahoj;";
    }

}
