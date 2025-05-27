package dmitr.stockcontrol.authservice.service.impl.auth;

import dmitr.stockcontrol.authservice.dto.auth.AuthUserDto;
import dmitr.stockcontrol.authservice.controller.auth.request.AuthRequestDto;
import dmitr.stockcontrol.authservice.controller.auth.request.RefreshRequestDto;
import dmitr.stockcontrol.authservice.controller.auth.response.AuthResponseDto;
import dmitr.stockcontrol.authservice.dao.entity.user.User;
import dmitr.stockcontrol.authservice.dao.repository.user.UserRepository;
import dmitr.stockcontrol.authservice.exception.extended.auth.InvalidAuthCredentialsException;
import dmitr.stockcontrol.authservice.service.face.auth.AuthService;
import dmitr.stockcontrol.authservice.service.face.auth.AuthUserTokenExtractorService;
import dmitr.stockcontrol.authservice.service.face.auth.AuthUserTokenGeneratorService;
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
    public AuthResponseDto auth(AuthRequestDto authRequest) {
        User authUser = getUserFromAuthRequestDto(authRequest);
        AuthUserDto authUserDto = getAuthUserDtoFromUser(authUser);
        return getAuthResponseByAuthUserDto(authUserDto);
    }

    @Override
    public AuthResponseDto refresh(RefreshRequestDto refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        UUID authUserId = authUserTokenExtractorService.getUserIdFromRefreshToken(refreshToken);

        User userFromRefreshToken = userRepository.findById(authUserId)
                .orElseThrow(InvalidAuthCredentialsException::new);

        AuthUserDto authUserDto = getAuthUserDtoFromUser(userFromRefreshToken);
        return getAuthResponseByAuthUserDto(authUserDto);
    }

    private User getUserFromAuthRequestDto(AuthRequestDto authRequest) {
        String authUsername = authRequest.getUsername();

        User userToAuth = userRepository.findByUsername(authUsername)
                .orElseThrow(InvalidAuthCredentialsException::new);

        String authPassword = authRequest.getPassword();
        String validPasswordHash = userToAuth.getPassword();

        if (!passwordEncoder.matches(authPassword, validPasswordHash)) {
            throw new InvalidAuthCredentialsException();
        }

        return userToAuth;
    }

    private AuthUserDto getAuthUserDtoFromUser(User user) {
        UUID authUserId = user.getId();

        List<GrantedAuthority> authUserRights = user.getRights()
                .stream()
                .map(r -> (GrantedAuthority) new SimpleGrantedAuthority(r.getLabel()))
                .toList();

        return AuthUserDto.builder()
                .id(authUserId)
                .rights(authUserRights)
                .build();
    }

    private AuthResponseDto getAuthResponseByAuthUserDto(AuthUserDto authUser) {
        String accessToken = authUserTokenGeneratorService.generateAccessToken(authUser);
        String refreshToken = authUserTokenGeneratorService.generateRefreshToken(authUser);
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
