package dmitr.stockcontrol.authService.service.face.auth;

import dmitr.stockcontrol.authService.controller.auth.request.AuthRequestDto;
import dmitr.stockcontrol.authService.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authService.controller.auth.response.AuthResponseDto;

public interface AuthService {

    AuthResponseDto auth(AuthRequestDto authRequest);
    AuthResponseDto refresh(RefreshRequestDto refreshRequest);
}
