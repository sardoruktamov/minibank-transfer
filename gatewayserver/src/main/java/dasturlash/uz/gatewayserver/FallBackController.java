package dasturlash.uz.gatewayserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {
    @RequestMapping("/profileFallbackApi")
    public Mono<String> profileFallbackAPI() {
        return Mono.just("An error occurred. Please try after some time or contact support team !!!");
    }

    @RequestMapping("/cardFallbackApi")
    public Mono<String> cardFallbackApi() {
        return Mono.just("Fall back action while calling card service");
    }
}

