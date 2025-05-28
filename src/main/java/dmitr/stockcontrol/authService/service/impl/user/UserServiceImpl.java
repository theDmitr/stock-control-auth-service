package dmitr.stockcontrol.authService.service.impl.user;

import dmitr.stockcontrol.authService.controller.user.response.AuthUserDto;
import dmitr.stockcontrol.authService.dto.auth.AuthUserDetailsDto;
import dmitr.stockcontrol.authService.service.face.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public AuthUserDto getAuthUser() {
        AuthUserDetailsDto authUserDetails = (AuthUserDetailsDto) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        UUID userId = authUserDetails.getId();
        List<String> rightLabels = authUserDetails.getRights()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return AuthUserDto.builder()
                .id(userId)
                .rights(rightLabels)
                .build();
    }
}
