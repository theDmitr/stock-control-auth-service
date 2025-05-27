package dmitr.stockcontrol.authService.service.face.auth;

import dmitr.stockcontrol.authService.dto.auth.AuthUserDto;

import java.util.UUID;

public interface AuthUserTokenExtractorService {

    AuthUserDto getAuthUserFromAccessToken(String accessToken);
    UUID getUserIdFromRefreshToken(String refreshToken);
}
