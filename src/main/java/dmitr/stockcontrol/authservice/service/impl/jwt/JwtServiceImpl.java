package dmitr.stockcontrol.authservice.service.impl.jwt;

import dmitr.stockcontrol.authservice.dto.jwt.JwtGenerationDetailsDto;
import dmitr.stockcontrol.authservice.service.face.jwt.JwtService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

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

        return jwtBuilder.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }
}
