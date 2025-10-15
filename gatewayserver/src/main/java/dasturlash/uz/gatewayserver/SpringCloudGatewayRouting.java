package dasturlash.uz.gatewayserver;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudGatewayRouting {
    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/api/v1/card/**")
                        .uri("lb://CARD")
                ).route(p -> p.path("/api/v1/profile/**")
                        .uri("lb://PROFILE")
                ).build();
    }

}

