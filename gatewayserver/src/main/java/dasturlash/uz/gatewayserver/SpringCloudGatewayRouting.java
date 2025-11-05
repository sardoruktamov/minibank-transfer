package dasturlash.uz.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class SpringCloudGatewayRouting {
    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/v1/card/**")
                        .filters(f -> f.rewritePath("/api/v1/card/(?<segment>.*)", "/api/v1/card/${segment}")
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.BAD_GATEWAY)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofSeconds(1), 2, true)
                                )
//                                .circuitBreaker(config -> config.setName("cardCircuitBreaker")
//                                        .setFallbackUri("forward:/cardFallbackApi")
//                                )

                        )
                        .uri("lb://CARD")
                ).route(p -> p.path("/api/v1/profile/**")
                        .filters(f -> f.rewritePath("/api/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("X-Request-Source", "Gateway")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("profileCircuitBreaker"))
                        )
                        .metadata("response-timeout", 3000)
                        .metadata("connect-timeout", 1000)
                        .uri("lb://PROFILE")
                ).build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
    }

}

