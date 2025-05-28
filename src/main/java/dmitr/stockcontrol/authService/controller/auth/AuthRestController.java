package dmitr.stockcontrol.authService.controller.auth;

import dmitr.stockcontrol.authService.controller.auth.request.LoginRequestDto;
import dmitr.stockcontrol.authService.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authService.controller.auth.response.AuthResponseDto;
import dmitr.stockcontrol.authService.service.face.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto auth(@RequestBody LoginRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public AuthResponseDto refresh(@RequestBody RefreshRequestDto refreshRequest) {
        return authService.refresh(refreshRequest);
    }
}
