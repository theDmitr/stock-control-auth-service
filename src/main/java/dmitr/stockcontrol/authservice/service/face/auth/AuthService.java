package dmitr.stockcontrol.authservice.service.face.auth;

import dmitr.stockcontrol.authservice.controller.auth.request.AuthRequestDto;
import dmitr.stockcontrol.authservice.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authservice.controller.auth.response.AuthResponseDto;

public interface AuthService {

    AuthResponseDto auth(AuthRequestDto authRequest);
    AuthResponseDto refresh(RefreshRequestDto refreshRequest);
}
