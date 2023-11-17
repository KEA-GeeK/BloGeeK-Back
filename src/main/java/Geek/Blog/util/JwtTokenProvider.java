package Geek.Blog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret.key}")
    private String jwtKey;

    private final AES256 aes256;

    @Autowired
    public JwtTokenProvider(AES256 aes256) {
        this.aes256 = aes256;
    }

    public String createTokens(Authentication authentication, Long memberIdx, String password) throws Exception {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        long oneDay = 1000L * 60 * 60 * 24;

        String accessToken = Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("memberIdx", memberIdx.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + oneDay * 7))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    /**
     * access token 유효성 검사
     *
     * @param token access token
     * @return boolean (true: valid, false: invalid)
     */
    public boolean vallidateToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            log.info("invalid JWT Token" + exception.getMessage());
        }

        return false;
    }

    /**
     * 사용자의 식별자를 추출하는 method
     *
     * @param bearerToken 사용자 token
     * @return 사용자 식별자
     */
    public String extractIdx(String bearerToken) throws Exception {
        try {
            log.info("요청 token 정보: " + bearerToken);
            String token = getTokenFromBearer(bearerToken);
            log.info("추출된 token 정보: " + token);

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtKey.getBytes())
                    .build()
                    .parseClaimsJws(token);

            String memberIdx = claimsJws.getBody().get("memberIdx", String.class);
            log.info("요청 사용자 정보: " + memberIdx);

            return memberIdx;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private static String getTokenFromBearer(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return bearerToken;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * access token에서 사용자의 정보 추출
     *
     * @param token access token
     * @return UsernamePasswordAuthenticationToken
     */
    public Authentication getAuthentication(String token) {
        Jws<Claims> claimsJws;

        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtKey.getBytes()).build()
                    .parseClaimsJws(token);

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("An error occurred: " + exception.getMessage());
            throw exception;
        }

        // 인증 정보 받아오기
        Collection<? extends GrantedAuthority> authorities =
                // Jwt token 에서 auth 필드에대한 스트림을 열어서 ,로 split 한다음에 SimpleGrantedAuthority 로 매핑해서 리스트로 반환
                Arrays.stream(claimsJws.getBody().get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new).toList();

        UserDetails principal = new User(claimsJws.getBody().getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * access token 유효성 검사
     *
     * @param token access token
     * @return boolean (true: valid, false: invalid)
     */
    public boolean validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            log.info("invalid JWT Token" + exception.getMessage());
        }

        return false;
    }

    /**
     * refresh token 유효성 검사
     *
     * @param refreshToken refresh token
     * @return
     */
    public String validateRefreshToken(String refreshToken) throws Exception {
        Jws<Claims> claims;

        try {
            log.info(String.valueOf(refreshToken));
            claims = Jwts.parserBuilder()
                    .setSigningKey(jwtKey.getBytes())
                    .build()
                    .parseClaimsJws(refreshToken);

            Date expired = claims.getBody().getExpiration();
            Date now = new Date();

            if (!expired.after(now)) throw new RuntimeException();

            return aes256.decrypt(claims.getBody().get("password").toString());

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

}
