package api.petpassport.config.security.filter;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static api.petpassport.config.security.SecurityConstants.OAUTH2_HEADER_KEY;
import static api.petpassport.config.security.SecurityConstants.OAUTH2_TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Value(value = "${jwt.secret}")
    public String jwtSecret;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {
        try {
            String token = getToken(request);
            if (token != null) {
                PrincipalDto principalDto = JwtService.parseToken(jwtSecret, token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principalDto, null, null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        }
    }

    private String getToken(final HttpServletRequest request) {
        String headerAuth = request.getHeader(OAUTH2_HEADER_KEY);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(OAUTH2_TOKEN_PREFIX)) {
            return headerAuth.replace(OAUTH2_TOKEN_PREFIX, "");
        } else {
            return null;
        }
    }

}
