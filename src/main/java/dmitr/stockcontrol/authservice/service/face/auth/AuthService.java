package dmitr.stockcontrol.authservice.service.face.auth;

import dmitr.stockcontrol.authservice.controller.auth.request.AuthRequest;
import dmitr.stockcontrol.authservice.controller.auth.response.AuthResponse;

public interface AuthService {

    AuthResponse auth(AuthRequest authRequest);
}
