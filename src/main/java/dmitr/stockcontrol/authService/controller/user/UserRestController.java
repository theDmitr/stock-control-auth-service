package dmitr.stockcontrol.authService.controller.user;

import dmitr.stockcontrol.authService.controller.user.response.AuthUserDto;
import dmitr.stockcontrol.authService.service.face.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth")
    public AuthUserDto getAuthUser() {
        return userService.getAuthUser();
    }
}
