package dmitr.stockcontrol.authService.service.face.auth;

import dmitr.stockcontrol.authService.dto.auth.AuthUserDetailsDto;

import java.util.UUID;

public interface AuthUserTokenExtractorService {

    AuthUserDetailsDto getAuthUserFromAccessToken(String accessToken);
    UUID getUserIdFromRefreshToken(String refreshToken);
}
