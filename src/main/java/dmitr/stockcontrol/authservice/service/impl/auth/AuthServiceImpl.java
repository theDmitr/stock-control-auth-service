package dmitr.stockcontrol.authservice.service.impl.auth;

import dmitr.stockcontrol.authservice.controller.auth.request.AuthRequest;
import dmitr.stockcontrol.authservice.controller.auth.response.AuthResponse;
import dmitr.stockcontrol.authservice.dao.entity.right.Right;
import dmitr.stockcontrol.authservice.dao.entity.user.User;
import dmitr.stockcontrol.authservice.dao.repository.user.UserRepository;
import dmitr.stockcontrol.authservice.dto.jwt.JwtGenerationDetailsDto;
import dmitr.stockcontrol.authservice.exception.extended.auth.IncorrectAuthPasswordException;
import dmitr.stockcontrol.authservice.exception.extended.auth.NotFoundAuthUsernameException;
import dmitr.stockcontrol.authservice.service.face.auth.AuthService;
import dmitr.stockcontrol.authservice.service.face.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final long FIVE_MINUTES_IN_MILLIS = TimeUnit.MINUTES.toMillis(5);
    private static final long SIX_MONTH_IN_MILLIS = TimeUnit.DAYS.toMillis(180);

    @Override
    public AuthResponse auth(AuthRequest authRequest) {
        String authUsername = authRequest.getUsername();

        User foundedUserToAuth = userRepository.findByUsername(authUsername)
                .orElseThrow(NotFoundAuthUsernameException::new);

        String authPassword = authRequest.getPassword();
        String validPasswordHash = foundedUserToAuth.getPassword();

        if (!passwordEncoder.matches(authPassword, validPasswordHash)) {
            throw new IncorrectAuthPasswordException();
        }

        return AuthResponse.builder()
                .accessToken(generateAccessToken(foundedUserToAuth))
                .refreshToken(generateRefreshToken(foundedUserToAuth))
                .build();
    }

    private String generateAccessToken(User user) {
        List<String> rightLabels = user.getRights()
                .stream()
                .map(Right::getLabel)
                .toList();

        JwtGenerationDetailsDto jwtGenerationDetails = JwtGenerationDetailsDto.builder()
                .subject(user.getId().toString())
                .claim("rights", rightLabels)
                .lifetime(FIVE_MINUTES_IN_MILLIS)
                .build();

        return jwtService.buildToken(jwtGenerationDetails);
    }

    private String generateRefreshToken(User user) {
        JwtGenerationDetailsDto jwtGenerationDetails = JwtGenerationDetailsDto.builder()
                .subject(user.getId().toString())
                .lifetime(SIX_MONTH_IN_MILLIS)
                .build();
        return jwtService.buildToken(jwtGenerationDetails);
    }
}
