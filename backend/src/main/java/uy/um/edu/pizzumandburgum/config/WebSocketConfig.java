package uy.um.edu.pizzumandburgum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefijo para los mensajes salientes (del servidor al cliente)
        config.enableSimpleBroker("/topic");
        // Prefijo para los mensajes entrantes (del cliente al servidor)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint al que se conecta el cliente (por ejemplo, desde React o JS)
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // o restringir al dominio del front
                .withSockJS();
    }
}
