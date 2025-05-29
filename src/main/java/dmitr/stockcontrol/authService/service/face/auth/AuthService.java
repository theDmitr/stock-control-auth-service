package dmitr.stockcontrol.authService.service.face.auth;

import dmitr.stockcontrol.authService.controller.auth.request.LoginRequestDto;
import dmitr.stockcontrol.authService.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authService.controller.auth.response.AuthResponseDto;
import dmitr.stockcontrol.authService.controller.auth.response.AuthUserDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto loginRequest);

    AuthResponseDto refresh(RefreshRequestDto refreshRequest);

    AuthUserDto getAuthUser();
}
