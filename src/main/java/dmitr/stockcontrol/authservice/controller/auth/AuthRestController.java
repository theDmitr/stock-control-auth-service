package dmitr.stockcontrol.authservice.controller.auth;

import dmitr.stockcontrol.authservice.controller.auth.request.AuthRequestDto;
import dmitr.stockcontrol.authservice.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authservice.controller.auth.response.AuthResponseDto;
import dmitr.stockcontrol.authservice.service.face.auth.AuthService;
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
    public AuthResponseDto auth(@RequestBody AuthRequestDto authRequest) {
        return authService.auth(authRequest);
    }

    @PostMapping("/refresh")
    public AuthResponseDto refresh(@RequestBody RefreshRequestDto refreshRequest) {
        return authService.refresh(refreshRequest);
    }
}
