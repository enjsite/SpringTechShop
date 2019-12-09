package ru.geekbrains.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Bean
    public DefaultHandshakeHandler customHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request,
                                              WebSocketHandler wsHandler,
                                              Map<String, Object> attributes) {
                ChatPrincipal principal = new ChatPrincipal(UUID.randomUUID().toString());
                WebSocketConfig.logger.info("New WebSocket user " + principal.getName());
                return principal;
            }
        };
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // STOMP messages with destination header begins with /chat_in are routed to certain @MessageMapping
        config.setApplicationDestinationPrefixes("/chat_in");
        // Enabling of subscription and broadcasting of messages
        config.enableSimpleBroker("/chat_out");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // HTTP URL for WebSocket handshake
        registry.addEndpoint("/gs-guide-websocket")
                .setHandshakeHandler(customHandshakeHandler())
                .withSockJS()
                .setClientLibraryUrl("/webjars/sockjs-client/1.0.2/sockjs.min.js");
    }
}
