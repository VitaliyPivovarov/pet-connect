package api.petpassport.config.stomp;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.service.JwtService;
import api.petpassport.service.ws.ObserveUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static api.petpassport.config.security.SecurityConstants.OAUTH2_TOKEN_PREFIX;

@Configuration
@Slf4j
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value(value = "${jwt.secret}")
    public String jwtSecret;

    private final ObserveUserService observeUserService;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public void afterReceiveCompletion(@Nullable Message<?> message,
//                                               @NonNull MessageChannel channel,
//                                               Exception ex) {
//                ChannelInterceptor.super.afterReceiveCompletion(message, channel, ex);
//            }

            @Override
            public Message<?> preSend(@NonNull Message<?> message,
                                      @NonNull MessageChannel channel) {
                final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (accessor != null) {
                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        List<String> nativeHeaders = accessor.getNativeHeader("Authorization");
                        if (nativeHeaders != null && !nativeHeaders.isEmpty()) {
                            PrincipalDto principalDto = JwtService.parseToken(jwtSecret, getToken(nativeHeaders.get(0)));
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principalDto, null, null);
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            accessor.setUser(principalDto);
                            observeUserService.connect(accessor.getSessionId(), accessor.getUser());
                        }
                    } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                        observeUserService.disconnect(accessor.getSessionId());
                    } else if (StompCommand.SEND.equals(accessor.getCommand())) {
                    } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    }
                }
                return message;
            }
        });
    }

    //todo remove
    private String getToken(final String token) {
        if (StringUtils.hasText(token) && token.startsWith(OAUTH2_TOKEN_PREFIX)) {
            return token.replace(OAUTH2_TOKEN_PREFIX, "");
        } else {
            return null;
        }
    }
}