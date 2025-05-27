package dmitr.stockcontrol.authservice.service.face.auth;

import dmitr.stockcontrol.authservice.dto.auth.AuthUserDto;

public interface AuthUserTokenGeneratorService {

    String generateAccessToken(AuthUserDto authUser);
    String generateRefreshToken(AuthUserDto authUser);
}
