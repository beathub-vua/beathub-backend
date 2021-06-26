package beathub.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtVerifier extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JwtConfig jwtConfig;

    public JwtVerifier(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (Strings.isBlank(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        try {

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecretKeyForSigning())
                    .build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = ((List<Map<String, String>>) body.get("authorities"))
                    .stream()
                    .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            // the client is authenticated
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }

        // TODO Invalidate old bearer tokens!

        filterChain.doFilter(request, response);
    }
}
