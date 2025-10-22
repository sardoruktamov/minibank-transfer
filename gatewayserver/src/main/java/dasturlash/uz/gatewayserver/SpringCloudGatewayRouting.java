package dasturlash.uz.gatewayserver;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Configuration
public class SpringCloudGatewayRouting {
    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/v1/card/**")
                        .filters(f -> f.rewritePath("/api/v1/card/(?<segment>.*)", "/api/v1/card/${segment}")
                                .filter((exchange, chain) -> {
                                    exchange.getRequest().getHeaders().add("X-Request-Source", "Gateway");
                                    long start = System.currentTimeMillis();
                                    return chain.filter(exchange)
                                            .then(Mono.fromRunnable(() -> {
                                                long end = System.currentTimeMillis();
                                                exchange.getResponse().getHeaders()
                                                        .add("X-Response-Time", (end-start) +" ms");
                                            }));
                                })
                        )
                        .uri("lb://CARD")
                ).route(p -> p.path("/api/v1/profile/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Request-Source", "Gateway")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        )
                        .uri("lb://PROFILE")
                ).build();
    }

}

