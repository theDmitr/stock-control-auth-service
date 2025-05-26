package dmitr.stockcontrol.authservice.service.face.jwt;


import dmitr.stockcontrol.authservice.dto.jwt.JwtGenerationDetailsDto;

public interface JwtService {

    String buildToken(JwtGenerationDetailsDto jwtGenerationDetails);
}
