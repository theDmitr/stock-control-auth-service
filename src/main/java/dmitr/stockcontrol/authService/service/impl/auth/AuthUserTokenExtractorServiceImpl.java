package dmitr.stockcontrol.authService.service.impl.auth;

import dmitr.stockcontrol.authService.dto.auth.AuthUserDetailsDto;
import dmitr.stockcontrol.authService.service.face.auth.AuthUserTokenExtractorService;
import dmitr.stockcontrol.authService.service.face.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserTokenExtractorServiceImpl implements AuthUserTokenExtractorService {

    private final JwtService jwtService;

    @Override
    public AuthUserDetailsDto getAuthUserFromAccessToken(String accessToken) {
        Claims claims = jwtService.extractClaimsFromToken(accessToken);

        UUID userId = UUID.fromString(claims.getSubject());
        String username = claims.get("username", String.class);
        List<String> rightLabels = (List<String>) claims.get("rights");

        List<GrantedAuthority> rights = rightLabels.stream()
                .map(rightLabel -> (GrantedAuthority) new SimpleGrantedAuthority(rightLabel))
                .toList();

        return AuthUserDetailsDto.builder()
                .id(userId)
                .username(username)
                .rights(rights)
                .build();
    }

    @Override
    public UUID getUserIdFromRefreshToken(String refreshToken) {
        Claims claims = jwtService.extractClaimsFromToken(refreshToken);
        return UUID.fromString(claims.getSubject());
    }
}
