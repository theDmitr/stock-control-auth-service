package dmitr.stockcontrol.authservice.service.face.jwt;


import dmitr.stockcontrol.authservice.dto.jwt.JwtGenerationDetailsDto;
import io.jsonwebtoken.Claims;

public interface JwtService {

    String buildToken(JwtGenerationDetailsDto jwtGenerationDetails);
    Claims extractClaimsFromToken(String token);
}
