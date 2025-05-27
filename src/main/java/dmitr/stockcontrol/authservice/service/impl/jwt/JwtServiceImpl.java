package dmitr.stockcontrol.authservice.service.impl.jwt;

import dmitr.stockcontrol.authservice.dto.jwt.JwtGenerationDetailsDto;
import dmitr.stockcontrol.authservice.exception.extended.auth.JwtInvalidException;
import dmitr.stockcontrol.authservice.service.face.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKeyString;
    private Key jwtSecretKey;

    @PostConstruct
    private void init() {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String buildToken(JwtGenerationDetailsDto jwtGenerationDetails) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + jwtGenerationDetails.getLifetime();
        Date currentDate = new Date(currentTimeMillis);
        Date expirationDate = new Date(expirationTimeMillis);

        String subject = jwtGenerationDetails.getSubject();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate);

        for (Map.Entry<String, Object> claim : jwtGenerationDetails.getClaims().entrySet()) {
            jwtBuilder.claim(claim.getKey(), claim.getValue());
        }

        return jwtBuilder.signWith(jwtSecretKey)
                .compact();
    }

    @Override
    public Claims extractClaimsFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date currentDate = new Date();
            if (claims.getExpiration().before(currentDate)) {
                throw new JwtInvalidException();
            }

            return claims;
        } catch (Exception e) {
            throw new JwtInvalidException();
        }
    }
}
