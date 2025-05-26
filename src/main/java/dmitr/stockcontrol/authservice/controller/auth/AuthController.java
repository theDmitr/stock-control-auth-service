package dmitr.stockcontrol.authservice.controller.auth;

import dmitr.stockcontrol.authservice.controller.auth.request.AuthRequest;
import dmitr.stockcontrol.authservice.controller.auth.response.AuthResponse;
import dmitr.stockcontrol.authservice.service.face.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public AuthResponse auth(@RequestBody AuthRequest authRequest) {
        return authService.auth(authRequest);
    }
}
