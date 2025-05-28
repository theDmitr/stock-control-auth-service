package dmitr.stockcontrol.authService.service.impl.auth;

import dmitr.stockcontrol.authService.controller.auth.request.LoginRequestDto;
import dmitr.stockcontrol.authService.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authService.controller.auth.response.AuthResponseDto;
import dmitr.stockcontrol.authService.dao.entity.user.User;
import dmitr.stockcontrol.authService.dao.repository.user.UserRepository;
import dmitr.stockcontrol.authService.dto.auth.AuthUserDetailsDto;
import dmitr.stockcontrol.authService.exception.extended.InvalidAuthCredentialsException;
import dmitr.stockcontrol.authService.service.face.auth.AuthService;
import dmitr.stockcontrol.authService.service.face.auth.AuthUserTokenExtractorService;
import dmitr.stockcontrol.authService.service.face.auth.AuthUserTokenGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthUserTokenGeneratorService authUserTokenGeneratorService;
    private final AuthUserTokenExtractorService authUserTokenExtractorService;

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequest) {
        User loginUser = getUserFromLoginRequestDto(loginRequest);
        AuthUserDetailsDto authUserDetailsDto = getAuthUserDtoFromUser(loginUser);
        return getAuthResponseByAuthUserDto(authUserDetailsDto);
    }

    @Override
    public AuthResponseDto refresh(RefreshRequestDto refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        UUID authUserId = authUserTokenExtractorService.getUserIdFromRefreshToken(refreshToken);

        User userFromRefreshToken = userRepository.findById(authUserId)
                .orElseThrow(InvalidAuthCredentialsException::new);

        AuthUserDetailsDto authUserDetailsDto = getAuthUserDtoFromUser(userFromRefreshToken);
        return getAuthResponseByAuthUserDto(authUserDetailsDto);
    }

    private User getUserFromLoginRequestDto(LoginRequestDto loginRequest) {
        String authUsername = loginRequest.getUsername();

        User userToAuth = userRepository.findByUsername(authUsername)
                .orElseThrow(InvalidAuthCredentialsException::new);

        String authPassword = loginRequest.getPassword();
        String validPasswordHash = userToAuth.getPassword();

        if (!passwordEncoder.matches(authPassword, validPasswordHash)) {
            throw new InvalidAuthCredentialsException();
        }

        return userToAuth;
    }

    private AuthUserDetailsDto getAuthUserDtoFromUser(User user) {
        UUID authUserId = user.getId();

        List<GrantedAuthority> authUserRights = user.getRights()
                .stream()
                .map(r -> (GrantedAuthority) new SimpleGrantedAuthority(r.getLabel()))
                .toList();

        return AuthUserDetailsDto.builder()
                .id(authUserId)
                .rights(authUserRights)
                .build();
    }

    private AuthResponseDto getAuthResponseByAuthUserDto(AuthUserDetailsDto authUser) {
        String accessToken = authUserTokenGeneratorService.generateAccessToken(authUser);
        String refreshToken = authUserTokenGeneratorService.generateRefreshToken(authUser);
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
