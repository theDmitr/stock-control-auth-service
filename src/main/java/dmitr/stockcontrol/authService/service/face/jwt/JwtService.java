package dmitr.stockcontrol.authService.service.face.jwt;


import dmitr.stockcontrol.authService.dto.jwt.JwtGenerationDetailsDto;
import io.jsonwebtoken.Claims;

public interface JwtService {

    String buildToken(JwtGenerationDetailsDto jwtGenerationDetails);
    Claims extractClaimsFromToken(String token);
}
