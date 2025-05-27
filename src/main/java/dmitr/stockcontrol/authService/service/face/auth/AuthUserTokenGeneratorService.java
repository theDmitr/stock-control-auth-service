package dmitr.stockcontrol.authService.service.face.auth;

import dmitr.stockcontrol.authService.dto.auth.AuthUserDto;

public interface AuthUserTokenGeneratorService {

    String generateAccessToken(AuthUserDto authUser);
    String generateRefreshToken(AuthUserDto authUser);
}
