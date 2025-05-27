package dmitr.stockcontrol.authservice.service.face.auth;

import dmitr.stockcontrol.authservice.dto.auth.AuthUserDto;

import java.util.UUID;

public interface AuthUserTokenExtractorService {

    AuthUserDto getAuthUserFromAccessToken(String accessToken);
    UUID getUserIdFromRefreshToken(String refreshToken);
}
