package ed.uopp.uoppcore.security.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import ed.uopp.uoppcore.security.data.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static ed.uopp.uoppcore.security.constant.SecurityConstant.*;
import static java.util.Arrays.stream;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(User user) {
        String[] claims = getClaimsFromUser(user);
        return JWT.create()
                .withIssuer(UOPP_LLC)
                .withAudience(UOPP_ADMINISTRATION)
                .withIssuedAt(new Date())
                .withSubject(user.getEmail())
                .withClaim(USER_ID, user.getId())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secret));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String email, String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        return StringUtils.isNoneEmpty(email) && !isTokenExpired(jwtVerifier, token);
    }

    public String getSubject(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        return jwtVerifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expiration = jwtVerifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();
        return jwtVerifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier jwtVerifier;
        try {
            Algorithm algorithm = HMAC512(secret);
            jwtVerifier = JWT.require(algorithm).withIssuer(UOPP_LLC).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return jwtVerifier;
    }

    private String[] getClaimsFromUser(User user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList()
                .toArray(String[]::new);
    }
}
