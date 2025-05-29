package dmitr.stockcontrol.authService.service.face.auth;

import dmitr.stockcontrol.authService.dto.auth.AuthUserDetailsDto;

public interface AuthUserTokenGeneratorService {

    String generateAccessToken(AuthUserDetailsDto authUser);

    String generateRefreshToken(AuthUserDetailsDto authUser);
}
